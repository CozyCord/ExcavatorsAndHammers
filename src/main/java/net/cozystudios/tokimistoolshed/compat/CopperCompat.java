package net.cozystudios.tokimistoolshed.compat;

import net.cozystudios.tokimistoolshed.TokimisToolshed;
import net.cozystudios.tokimistoolshed.item.ExcavatorItem;
import net.cozystudios.tokimistoolshed.item.HammerItem;
import net.cozystudios.tokimistoolshed.item.LumberAxeItem;
import net.cozystudios.tokimistoolshed.item.ScytheItem;
import net.cozystudios.tokimistoolshed.item.ModToolMaterials;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
//? if <1.21 {
import net.minecraft.util.Identifier;
//?} else {
/*import net.minecraft.util.Identifier;
*///?}
//? if >=1.21.2 {
/*import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
*///?}

public class CopperCompat {

    public static Item COPPER_EXCAVATOR;
    public static Item COPPER_HAMMER;
    public static Item COPPER_SCYTHE;
    public static Item COPPER_LUMBER_AXE;

    private static Item.Settings settings(String name) {
        //? if >=1.21.2 {
        /*return new Item.Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TokimisToolshed.MOD_ID, name)));
        *///?} else {
        return new Item.Settings();
        //?}
    }

    public static void register() {
        COPPER_EXCAVATOR = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(TokimisToolshed.MOD_ID, "copper_excavator"),
                //?} else {
                /*Identifier.of(TokimisToolshed.MOD_ID, "copper_excavator"),
                *///?}
                new ExcavatorItem(ModToolMaterials.COPPER, 4.0F, -3.0F, settings("copper_excavator"))
        );

        COPPER_HAMMER = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(TokimisToolshed.MOD_ID, "copper_hammer"),
                //?} else {
                /*Identifier.of(TokimisToolshed.MOD_ID, "copper_hammer"),
                *///?}
                new HammerItem(ModToolMaterials.COPPER, 1.75F, -3.2F, settings("copper_hammer"))
        );

        COPPER_SCYTHE = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(TokimisToolshed.MOD_ID, "copper_scythe"),
                //?} else {
                /*Identifier.of(TokimisToolshed.MOD_ID, "copper_scythe"),
                *///?}
                new ScytheItem(ModToolMaterials.COPPER, 6.0F, -2.6F, settings("copper_scythe"))
        );

        COPPER_LUMBER_AXE = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(TokimisToolshed.MOD_ID, "copper_lumber_axe"),
                //?} else {
                /*Identifier.of(TokimisToolshed.MOD_ID, "copper_lumber_axe"),
                *///?}
                new LumberAxeItem(ModToolMaterials.COPPER, 10.0F, -3.4F, settings("copper_lumber_axe"))
        );

    }
}
