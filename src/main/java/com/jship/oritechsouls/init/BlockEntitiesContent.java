package com.jship.oritechsouls.init;

import java.util.function.Supplier;

import com.jship.oritechsouls.OritechSouls;
import com.jship.oritechsouls.blocks.entities.SoulPipeInterfaceEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntitiesContent {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OritechSouls.MOD_ID);

    public static Supplier<BlockEntityType<SoulPipeInterfaceEntity>> SOUL_PIPE_ENTITY = BLOCK_ENTITIES.register(
        "soul_pipe_entity", () -> BlockEntityType.Builder.of(SoulPipeInterfaceEntity::new, BlockContent.SOUL_PIPE_CONNECTION.get(), BlockContent.FRAMED_SOUL_PIPE_CONNECTION.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
        
    }   
}
