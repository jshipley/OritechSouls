package com.jship.oritechsouls.datagen;

import com.jship.oritechsouls.OritechSouls;
import com.jship.oritechsouls.init.BlockContent;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, OritechSouls.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(BlockContent.SOUL_PIPE_DUCT.get(), cubeAll(BlockContent.SOUL_PIPE_DUCT.get()));
    }    
}
