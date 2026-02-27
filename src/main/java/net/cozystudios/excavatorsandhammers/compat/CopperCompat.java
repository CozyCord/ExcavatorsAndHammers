package net.cozystudios.excavatorsandhammers.compat;

import net.cozystudios.excavatorsandhammers.ExcavatorsAndHammers;
import net.cozystudios.excavatorsandhammers.item.ExcavatorItem;
import net.cozystudios.excavatorsandhammers.item.HammerItem;
import net.cozystudios.excavatorsandhammers.item.ModToolMaterials;
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

    private static Item.Settings settings(String name) {
        //? if >=1.21.2 {
        /*return new Item.Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ExcavatorsAndHammers.MOD_ID, name)));
        *///?} else {
        return new Item.Settings();
        //?}
    }

    public static void register() {
        COPPER_EXCAVATOR = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(ExcavatorsAndHammers.MOD_ID, "copper_excavator"),
                //?} else {
                /*Identifier.of(ExcavatorsAndHammers.MOD_ID, "copper_excavator"),
                *///?}
                new ExcavatorItem(ModToolMaterials.COPPER, 1.75F, -3.0F, settings("copper_excavator"))
        );

        COPPER_HAMMER = Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(ExcavatorsAndHammers.MOD_ID, "copper_hammer"),
                //?} else {
                /*Identifier.of(ExcavatorsAndHammers.MOD_ID, "copper_hammer"),
                *///?}
                new HammerItem(ModToolMaterials.COPPER, 1.75F, -3.2F, settings("copper_hammer"))
        );
    }
}
