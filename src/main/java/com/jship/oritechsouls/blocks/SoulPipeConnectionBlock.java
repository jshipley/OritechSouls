package com.jship.oritechsouls.blocks;

import static com.jship.oritechsouls.blocks.SoulPipeBlock.SOUL_PIPE_DATA;

import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import com.buuz135.industrialforegoingsouls.capabilities.SoulCapabilities;
import com.jship.oritechsouls.blocks.entities.SoulPipeInterfaceEntity;
import com.jship.oritechsouls.init.BlockContent;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import rearth.oritech.block.blocks.pipes.ExtractablePipeConnectionBlock;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity;

public class SoulPipeConnectionBlock extends ExtractablePipeConnectionBlock {

    public SoulPipeConnectionBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TriFunction<Level, BlockPos, Direction, Boolean> apiValidationFunction() {
        return ((level, pos, direction) -> level.getCapability(SoulCapabilities.BLOCK, pos, direction) != null);
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SoulPipeInterfaceEntity(pos, state);
    }
    
    @Override
    public BlockState getConnectionBlock() {
        return BlockContent.SOUL_PIPE_CONNECTION.get().defaultBlockState();
    }
    
    @Override
    public BlockState getNormalBlock() {
        return BlockContent.SOUL_PIPE.get().defaultBlockState();
    }
    
    @Override
    public String getPipeTypeName() {
        return "soul";
    }
    
    @Override
    public boolean connectToOwnBlockType(Block block) {
        return block instanceof SoulPipeBlock || block instanceof SoulPipeConnectionBlock || block instanceof SoulPipeDuctBlock;
    }
        
    @Override
    public GenericPipeInterfaceEntity.PipeNetworkData getNetworkData(Level level) {
        return SOUL_PIPE_DATA.computeIfAbsent(level.dimension().location(), data -> new GenericPipeInterfaceEntity.PipeNetworkData());
    }

	public static class FramedSoulPipeConnectionBlock extends SoulPipeConnectionBlock {

		public FramedSoulPipeConnectionBlock(Properties properties) {
			super(properties);
		}

		@Override
		public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			return Shapes.block();
		}

		@Override
		public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			return state.getShape(level, pos);
		}

		@Override
		public BlockState getNormalBlock() {
			return BlockContent.FRAMED_SOUL_PIPE.get().defaultBlockState();
		}

		@Override
		public BlockState getConnectionBlock() {
			return BlockContent.FRAMED_SOUL_PIPE_CONNECTION.get().defaultBlockState();
		}
	}
}
