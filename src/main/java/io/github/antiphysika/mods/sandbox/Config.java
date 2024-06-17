/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Based on the the Neoforged project's MDK.  For more information, see
 * the file `doc/license/NEOFORGE_LICENSE.txt` in the top level of this
 * repository.
 */

package io.github.antiphysika.mods.sandbox;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = SandboxMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
  private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

  private static final ModConfigSpec.BooleanValue DEBUG_MODE = BUILDER
    .comment("Whether or not to log noisy af debug-aid messages")
    .define("debug_mode", true);

  static final ModConfigSpec SPEC = BUILDER.build();

  public static boolean debug_mode;

  private static boolean validateItemName (final Object obj)
  {
    return obj instanceof String itemName
      && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
  }

  @SubscribeEvent
  static void onLoad (final ModConfigEvent event)
  {
    debug_mode = DEBUG_MODE.get();
  }
}

/*
 * vim: ts=2 sw=2 et fdm=marker :
 */
