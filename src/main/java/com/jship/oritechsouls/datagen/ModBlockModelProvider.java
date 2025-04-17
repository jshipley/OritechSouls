package com.jship.oritechsouls.datagen;

import com.jship.oritechsouls.OritechSouls;
import com.jship.oritechsouls.init.BlockContent;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {
    
    public ModBlockModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        cubeAll("soul_pipe_duct", modLoc("block/soul_pipe_duct"));
        // withExistingParent(BlockContent.SOUL_PIPE.getId().toString(), modLoc("block/soul_pipe"));
    }

    @Override
    public String getName() {
        return "[Model Provider - " + OritechSouls.MOD_ID + "]";
    }
}
