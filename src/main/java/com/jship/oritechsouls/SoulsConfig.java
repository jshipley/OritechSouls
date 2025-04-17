package com.jship.oritechsouls;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = OritechSouls.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class SoulsConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue SOUL_TRANSFER_RATE = BUILDER.comment(
        "How many souls a pipe can move per transfer"
    ).defineInRange("soulExtractRate", 4, 1, 100);

    private static final ModConfigSpec.IntValue SOUL_TRANSFER_PERIOD = BUILDER.comment(
        "How long (in ticks) to wait between transfers"
    ).defineInRange("soulTransferPeriod", 8, 1, 200);

    private static final ModConfigSpec.IntValue SOUL_PIPE_CAPACITY = BUILDER.comment(
        "How many souls can be in a pipe"
    ).defineInRange("soulPipeCapacity", 16, 1, 256);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int soulTransferRate;
    public static int soulTransferPeriod;
    public static int soulPipeCapacity;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        soulTransferRate = SOUL_TRANSFER_RATE.get();
        soulTransferPeriod = SOUL_TRANSFER_PERIOD.get();
        soulPipeCapacity = SOUL_PIPE_CAPACITY.get();
    }
}
