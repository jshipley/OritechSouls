package com.jship.oritechsouls.init;

import com.jship.oritechsouls.OritechSouls;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemContent {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OritechSouls.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
