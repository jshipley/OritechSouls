package com.jship.oritechsouls.blocks.entities;

import static com.jship.oritechsouls.blocks.SoulPipeBlock.SOUL_PIPE_DATA;

import com.buuz135.industrialforegoingsouls.capabilities.ISoulHandler;
import com.buuz135.industrialforegoingsouls.capabilities.SoulCapabilities;
import com.jship.oritechsouls.OritechSouls;
import com.jship.oritechsouls.SoulsConfig;
import com.jship.oritechsouls.init.BlockEntitiesContent;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import rearth.oritech.block.blocks.pipes.ExtractablePipeConnectionBlock;
import rearth.oritech.block.entity.pipes.ExtractablePipeInterfaceEntity;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity;

public class SoulPipeInterfaceEntity extends ExtractablePipeInterfaceEntity {

    private static final int TRANSFER_RATE = SoulsConfig.soulTransferRate;
    private static final int TRANSFER_PERIOD = SoulsConfig.soulTransferPeriod;

    private List<ISoulHandler> cachedTargets = List.of();

    public SoulPipeInterfaceEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesContent.SOUL_PIPE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, GenericPipeInterfaceEntity entity) {
        var block = (ExtractablePipeConnectionBlock) state.getBlock();
        if (level.isClientSide() || !block.isExtractable(state)) return;

        if (level.getGameTime() % TRANSFER_PERIOD != 0 && !isBoostAvailable()) return;

        var data = SOUL_PIPE_DATA.getOrDefault(level.dimension().location(), new PipeNetworkData());

        // try to get souls to transfer
        // one transaction for each side
        var sources = data.machineInterfaces.getOrDefault(pos, new HashSet<>());

        var availableSouls = 0;
        var takenFrom = (ISoulHandler)null;
        for (var sourcePos : sources) {
            var offset = pos.subtract(sourcePos);
            var direction = Direction.fromDelta(offset.getX(), offset.getY(), offset.getZ());
            if (!block.isSideExtractable(state, direction.getOpposite())) continue;
            var sourceContainer = level.getCapability(SoulCapabilities.BLOCK, sourcePos, direction);
            OritechSouls.LOGGER.debug("Soul capability found at {} (direction: {}): {}", sourcePos, direction, sourceContainer);
            if (sourceContainer == null) {
                OritechSouls.LOGGER.debug("No capability found");
                continue;
            }

            var simulatedExtract = sourceContainer.drain(TRANSFER_RATE, ISoulHandler.Action.SIMULATE);
            if (simulatedExtract > 0) {
                availableSouls = simulatedExtract;
                takenFrom = sourceContainer;
                break;
            }
        }

        if (availableSouls <= 0 || takenFrom == null) return;

        var targets = findNetworkTargets(pos, data);
        if (targets == null) return; // this should never happen

        var targetHash = targets.hashCode();

        if (targetHash != filteredTargetsNetHash || cachedTargets == null) {
            cachedTargets = targets.stream()
                .map(target -> level.getCapability(SoulCapabilities.BLOCK, target.getA(), target.getB()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            filteredTargetsNetHash = targetHash;
        }

        Collections.shuffle(cachedTargets);

        var soulsToMove = availableSouls;

        for (var soulHandler : cachedTargets) {
            var transferred = soulHandler.fill(availableSouls, ISoulHandler.Action.EXECUTE);
            soulsToMove -= transferred;

            if (soulsToMove <= 0) break;
        }

        var moved = availableSouls - soulsToMove;
        if (moved > 0) {
            takenFrom.drain(moved, ISoulHandler.Action.EXECUTE);
        }
    }

    @Override
    public void setChanged() {
        if (this.level != null) level.blockEntityChanged(worldPosition);
    }
}
