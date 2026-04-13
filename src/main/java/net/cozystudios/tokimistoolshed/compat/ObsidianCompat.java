package net.cozystudios.tokimistoolshed.compat;

import net.cozystudios.tokimistoolshed.TokimisToolshed;
import net.cozystudios.tokimistoolshed.item.ExcavatorItem;
import net.cozystudios.tokimistoolshed.item.HammerItem;
import net.cozystudios.tokimistoolshed.item.LumberAxeItem;
import net.cozystudios.tokimistoolshed.item.ScytheItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.cozystudios.tokimistoolshed.item.ModToolMaterials;

public class ObsidianCompat {

    public static Item OBSIDIAN_EXCAVATOR;
    public static Item OBSIDIAN_HAMMER;
    public static Item OBSIDIAN_SCYTHE;
    public static Item OBSIDIAN_LUMBER_AXE;

    private static Item.Properties settings(String name) {
        return new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name)));
    }

    public static void register() {
        OBSIDIAN_EXCAVATOR = Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "obsidian_excavator"),
                new ExcavatorItem(ModToolMaterials.OBSIDIAN, 2.75F, -3.0F, settings("obsidian_excavator").fireResistant())
        );

        OBSIDIAN_HAMMER = Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "obsidian_hammer"),
                new HammerItem(ModToolMaterials.OBSIDIAN, 2.75F, -3.2F, settings("obsidian_hammer").fireResistant())
        );

        OBSIDIAN_SCYTHE = Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "obsidian_scythe"),
                new ScytheItem(ModToolMaterials.OBSIDIAN, 3.75F, -2.6F, settings("obsidian_scythe").fireResistant())
        );

        OBSIDIAN_LUMBER_AXE = Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "obsidian_lumber_axe"),
                new LumberAxeItem(ModToolMaterials.OBSIDIAN, 5.0F, -3.0F, settings("obsidian_lumber_axe").fireResistant())
        );
    }
}
