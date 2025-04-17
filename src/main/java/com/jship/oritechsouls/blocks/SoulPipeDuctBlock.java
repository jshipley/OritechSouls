package com.jship.oritechsouls.blocks;

import static com.jship.oritechsouls.blocks.SoulPipeBlock.SOUL_PIPE_DATA;

import com.jship.oritechsouls.init.BlockContent;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import rearth.oritech.block.blocks.pipes.GenericPipeDuctBlock;
import rearth.oritech.block.entity.pipes.GenericPipeInterfaceEntity;

public class SoulPipeDuctBlock extends GenericPipeDuctBlock {
    public SoulPipeDuctBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockState getConnectionBlock() {
		return getNormalBlock();
	}

	@Override
	public BlockState getNormalBlock() {
		return BlockContent.SOUL_PIPE_DUCT.get().defaultBlockState();
	}

	@Override
	public String getPipeTypeName() {
		return "soul";
	}

	@Override
	public boolean connectToOwnBlockType(Block block) {
		return block instanceof SoulPipeDuctBlock || block instanceof SoulPipeBlock ||block instanceof SoulPipeConnectionBlock;
	}

	@Override
	public GenericPipeInterfaceEntity.PipeNetworkData getNetworkData(Level level) {
		return SOUL_PIPE_DATA.computeIfAbsent(level.dimension().location(), data -> new GenericPipeInterfaceEntity.PipeNetworkData());
	}
}
