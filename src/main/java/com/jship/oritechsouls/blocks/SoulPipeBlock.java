package com.jship.oritechsouls.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.function.TriFunction;

import com.buuz135.industrialforegoingsouls.capabilities.SoulCapabilities;
import com.jship.oritechsouls.init.BlockContent;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import rearth.oritech.block.blocks.pipes.GenericPipeBlock;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity.PipeNetworkData;

public class SoulPipeBlock extends GenericPipeBlock {

    public static Map<ResourceLocation, GenericPipeInterfaceEntity.PipeNetworkData> SOUL_PIPE_DATA = new HashMap<>();

    public SoulPipeBlock(Properties settings) {
        super(settings);
    }

    @Override
    public TriFunction<Level, BlockPos, Direction, Boolean> apiValidationFunction() {
        return ((level, pos, direction) -> level.getCapability(SoulCapabilities.BLOCK, pos, direction) != null);
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
    public PipeNetworkData getNetworkData(Level level) {
        return SOUL_PIPE_DATA.computeIfAbsent(level.dimension().location(), data -> new GenericPipeInterfaceEntity.PipeNetworkData());
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag options) {
        var text = Component.translatable("tooltip.oritechsouls.soul_max_transfer").withStyle(ChatFormatting.GRAY)
            .append(Component.translatable("tooltip.oritech.soul_transfer_rate", 4)).withStyle(ChatFormatting.GOLD);
        tooltip.add(text);
        super.appendHoverText(stack, context, tooltip, options);
    }

    public static class FramedSoulPipeBlock extends SoulPipeBlock {
        
        public FramedSoulPipeBlock(Properties settings) {
            super(settings);
        }

        @Override
        public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return Shapes.block();
        }

        @Override
		public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
			return state.getShape(world, pos);
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
