/*
 * SPDX-License-Identifier: Apache-2.0
 */

package io.github.antiphysika.mods.sandbox;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

// {{{ class SandboxMod::
//
@Mod(SandboxMod.MOD_ID)
public class SandboxMod
{
  // Mod ID
  public static final String MOD_ID = "antiphysika_sandbox";

  // Mod classes
  public static final String MOD_CLASS_MAIN = "SandboxMod";
  public static final String MOD_CLASS_CLIENT =
    String.format("%s.%s", MOD_CLASS_MAIN, "ClientModEvents");

  // Mod logger
  private static final Logger LOGGER = LogUtils.getLogger();

  // DR: Blocks
  public static final DeferredRegister.Blocks BLOCKS =
    DeferredRegister.createBlocks(MOD_ID);

  // DR: Items
  public static final DeferredRegister.Items ITEMS =
    DeferredRegister.createItems(MOD_ID);

  // DR: Creative mode tabs
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
    DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

  /*
   * Registry: block
   */

  public static final DeferredBlock<Block> EXAMPLE_BLOCK =
    BLOCKS.registerSimpleBlock(
      "solar_stone",
      BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

  /*
   * Registry: block item
   */

  public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM =
    ITEMS.registerSimpleBlockItem("solar_stone", EXAMPLE_BLOCK);

  /*
   * Registry: food item
   */

  public static final DeferredItem<Item> EXAMPLE_ITEM =
    ITEMS.registerSimpleItem(
      "lunar_cheese",
      new Item.Properties().food(new FoodProperties.Builder()
        .alwaysEdible()
        .nutrition(1)
        .saturationModifier(2f)
        .build()));

  /*
   * Creative mode tab
   */

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB =
    CREATIVE_MODE_TABS.register("antiphysika_sandbox_tab", () -> CreativeModeTab.builder()
      .title(Component.translatable("itemGroup.antiphysika_sandbox"))
      .withTabsBefore(CreativeModeTabs.COMBAT)
      .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
      .displayItems((parameters, output) -> {
        output.accept(EXAMPLE_ITEM.get());
      }).build());

  /*
   * SandboxMod constructor (all sides)
   */

  public SandboxMod (IEventBus modEventBus, ModContainer modContainer)
  {
    modEventBus.addListener(this::commonSetup);

    BLOCKS.register(modEventBus);
    ITEMS.register(modEventBus);

    CREATIVE_MODE_TABS.register(modEventBus);

    NeoForge.EVENT_BUS.register(this);

    modEventBus.addListener(this::addCreative);

    modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

  /*
   * SandboxMod.commonSetup()
   *
   * Perform common mod setup (all sides)
   */

  private void commonSetup (final FMLCommonSetupEvent event)
  {
    LOGGER.info("In {}.commonSetup()", MOD_CLASS_MAIN);
  }

  /*
   * SandboxMod.addCreative()
   *
   * Add building block items to mod's creative tab
   */

  private void addCreative (BuildCreativeModeTabContentsEvent event)
  {
    if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
      event.accept(EXAMPLE_BLOCK_ITEM);
  }

  @SubscribeEvent
  public void onServerStarting (ServerStartingEvent event)
  {
    LOGGER.info("In {}.onServerStarting()", MOD_CLASS_MAIN);
  }

  // {{{ class ::ClientModEvents
  //
  @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents
  {
    @SubscribeEvent
    public static void onClientSetup (FMLClientSetupEvent event)
    {
      LOGGER.info("In {}.onClientSetup()", MOD_CLASS_CLIENT);
    }
  }
  //
  // END of ::ClientModEvents }}}

}
//
// END of SandboxMod:: }}}

/*
 * vim: ts=2 sw=2 et fdm=marker :
 */
