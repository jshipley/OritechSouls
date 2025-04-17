package com.jship.oritechsouls.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.withExistingParent("soul_pipe", modLoc("block/soul_pipe"));
        this.withExistingParent("soul_pipe_duct", modLoc("block/soul_pipe_duct"));
    }
}
