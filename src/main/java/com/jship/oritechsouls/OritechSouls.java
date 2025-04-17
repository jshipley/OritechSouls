package com.jship.oritechsouls;

import org.slf4j.Logger;

import com.buuz135.industrialforegoingsouls.capabilities.SoulCapabilities;
import com.jship.oritechsouls.capabilities.CatalystSoulCapability;
import com.jship.oritechsouls.init.BlockContent;
import com.jship.oritechsouls.init.BlockEntitiesContent;
import com.jship.oritechsouls.init.ItemContent;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import rearth.oritech.block.entity.arcane.EnchantmentCatalystBlockEntity;



@Mod(OritechSouls.MOD_ID)
public class OritechSouls
{
    public static final String MOD_ID = "oritechsouls";
    public static final Logger LOGGER = LogUtils.getLogger();

    // public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    // public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    // public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    // public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
    // public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            // .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
    //         .title(Component.translatable("itemGroup.examplemod")) //The language key for the title of your CreativeModeTab
    //         .withTabsBefore(CreativeModeTabs.COMBAT)
    //         .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
    //         .displayItems((parameters, output) -> {
    //             output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
    //         }).build());

    public OritechSouls(IEventBus modEventBus, ModContainer modContainer)
    {
        BlockContent.register(modEventBus);
        BlockEntitiesContent.register(modEventBus);
        ItemContent.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        // BLOCKS.register(modEventBus);
        // ITEMS.register(modEventBus);
        // CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerCapabilities);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, SoulsConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

        // Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(BlockContent.SOUL_PIPE.asItem());
        }
        // if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        //     event.accept(EXAMPLE_BLOCK_ITEM);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlock(
            SoulCapabilities.BLOCK,
            (level, blockPos, blockState, blockEntity, direction) -> {
                if (blockEntity instanceof EnchantmentCatalystBlockEntity catalystEntity) {
                    return new CatalystSoulCapability(catalystEntity, direction);
                }
                return null;
            }, rearth.oritech.init.BlockContent.ENCHANTMENT_CATALYST_BLOCK);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
