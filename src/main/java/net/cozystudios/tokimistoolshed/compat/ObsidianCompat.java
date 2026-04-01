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

public class ObsidianCompat {

    public static Item OBSIDIAN_EXCAVATOR;
    public static Item OBSIDIAN_HAMMER;
    public static Item OBSIDIAN_SCYTHE;
    public static Item OBSIDIAN_LUMBER_AXE;

    private static Item.Settings settings(String name) {
        //? if >=1.21.2 {
        /*return new Item.Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TokimisToolshed.MOD_ID, name)));
        *///?} else {
        return new Item.Settings();
        //?}
    }

    public static void register() {
        OBSIDIAN_EXCAVATOR = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(TokimisToolshed.MOD_ID, "obsidian_excavator"),
                //?} else {
                /*Identifier.of(TokimisToolshed.MOD_ID, "obsidian_excavator"),
                *///?}
                new ExcavatorItem(ModToolMaterials.OBSIDIAN, 2.75F, -3.0F, settings("obsidian_excavator").fireproof())
        );

        OBSIDIAN_HAMMER = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(TokimisToolshed.MOD_ID, "obsidian_hammer"),
                //?} else {
                /*Identifier.of(TokimisToolshed.MOD_ID, "obsidian_hammer"),
                *///?}
                new HammerItem(ModToolMaterials.OBSIDIAN, 2.75F, -3.2F, settings("obsidian_hammer").fireproof())
        );

        OBSIDIAN_SCYTHE = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(TokimisToolshed.MOD_ID, "obsidian_scythe"),
                //?} else {
                /*Identifier.of(TokimisToolshed.MOD_ID, "obsidian_scythe"),
                *///?}
                new ScytheItem(ModToolMaterials.OBSIDIAN, 3.75F, -2.6F, settings("obsidian_scythe").fireproof())
        );

        OBSIDIAN_LUMBER_AXE = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(TokimisToolshed.MOD_ID, "obsidian_lumber_axe"),
                //?} else {
                /*Identifier.of(TokimisToolshed.MOD_ID, "obsidian_lumber_axe"),
                *///?}
                new LumberAxeItem(ModToolMaterials.OBSIDIAN, 5.0F, -3.0F, settings("obsidian_lumber_axe").fireproof())
        );
    }
}
