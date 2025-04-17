package com.jship.oritechsouls.datagen;

import com.jship.oritechsouls.OritechSouls;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = OritechSouls.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var existingFileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBlockModelProvider(packOutput, OritechSouls.MOD_ID, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemModelProvider(packOutput, OritechSouls.MOD_ID, existingFileHelper));
    }
        
}
