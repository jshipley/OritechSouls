package com.jship.oritechsouls.init;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import com.jship.oritechsouls.OritechSouls;
import com.jship.oritechsouls.blocks.SoulPipeBlock;
import com.jship.oritechsouls.blocks.SoulPipeConnectionBlock;
import com.jship.oritechsouls.blocks.SoulPipeDuctBlock;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockContent {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OritechSouls.MOD_ID);

    @AddBlockItem
    public static final DeferredBlock<Block> SOUL_PIPE = BLOCKS.registerBlock(
        "soul_pipe", SoulPipeBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(1.0f, 2.0f));
    public static final DeferredBlock<Block> SOUL_PIPE_CONNECTION = BLOCKS.registerBlock(
        "soul_pipe_connection", SoulPipeConnectionBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(1.0f, 2.0f));
    @AddBlockItem
    public static final DeferredBlock<Block> FRAMED_SOUL_PIPE = BLOCKS.registerBlock(
        "framed_soul_pipe", SoulPipeBlock.FramedSoulPipeBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(1.0f, 2.0f));
    public static final DeferredBlock<Block> FRAMED_SOUL_PIPE_CONNECTION = BLOCKS.registerBlock(
        "framed_soul_pipe_connection", SoulPipeConnectionBlock.FramedSoulPipeConnectionBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(1.0f, 2.0f));
    @AddBlockItem
    public static final DeferredBlock<Block> SOUL_PIPE_DUCT = BLOCKS.registerBlock(
        "soul_pipe_duct", SoulPipeDuctBlock::new,
        BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    
    private static<T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ItemContent.ITEMS.registerSimpleBlockItem(name, block, new Item.Properties());
    }
    
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

        // register block items
        Arrays.stream(BlockContent.class.getDeclaredFields()).filter(field -> field.isAnnotationPresent(AddBlockItem.class)).forEach(field -> {
            try {
                @SuppressWarnings("unchecked")
                var block = (DeferredBlock<Block>)field.get(null);
                registerBlockItem(block.getRegisteredName().split(":")[1], block);
            } catch (IllegalAccessException e) {
                OritechSouls.LOGGER.error("Unable to add block item: {}", e);
            }
        });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface AddBlockItem { }
}
