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

public class CopperCompat {

    public static Item COPPER_EXCAVATOR;
    public static Item COPPER_HAMMER;
    public static Item COPPER_SCYTHE;
    public static Item COPPER_LUMBER_AXE;

    private static Item.Properties settings(String name) {
        return new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name)));
    }

    public static void register() {
        COPPER_EXCAVATOR = Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "copper_excavator"),
                new ExcavatorItem(ModToolMaterials.COPPER, 4.0F, -3.0F, settings("copper_excavator"))
        );

        COPPER_HAMMER = Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "copper_hammer"),
                new HammerItem(ModToolMaterials.COPPER, 1.75F, -3.2F, settings("copper_hammer"))
        );

        COPPER_SCYTHE = Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "copper_scythe"),
                new ScytheItem(ModToolMaterials.COPPER, 6.0F, -2.6F, settings("copper_scythe"))
        );

        COPPER_LUMBER_AXE = Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "copper_lumber_axe"),
                new LumberAxeItem(ModToolMaterials.COPPER, 10.0F, -3.4F, settings("copper_lumber_axe"))
        );

    }
}
