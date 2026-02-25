package net.cozystudios.excavatorsandhammers.registry;

import net.cozystudios.excavatorsandhammers.ExcavatorsAndHammers;
import net.cozystudios.excavatorsandhammers.item.ExcavatorItem;
import net.cozystudios.excavatorsandhammers.item.HammerItem;
import net.cozystudios.excavatorsandhammers.item.ModToolMaterials;
//? if <1.21.2 {
import net.fabricmc.fabric.api.registry.FuelRegistry;
//?} else {
/*import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
*///?}
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
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

public class ModItems {

    public static Item WOODEN_EXCAVATOR;
    public static Item STONE_EXCAVATOR;
    public static Item IRON_EXCAVATOR;
    public static Item GOLDEN_EXCAVATOR;
    public static Item DIAMOND_EXCAVATOR;
    public static Item NETHERITE_EXCAVATOR;

    public static Item WOODEN_HAMMER;
    public static Item STONE_HAMMER;
    public static Item IRON_HAMMER;
    public static Item GOLDEN_HAMMER;
    public static Item DIAMOND_HAMMER;
    public static Item NETHERITE_HAMMER;

    private static Item.Settings settings(String name) {
        //? if >=1.21.2 {
        /*return new Item.Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ExcavatorsAndHammers.MOD_ID, name)));
        *///?} else {
        return new Item.Settings();
        //?}
    }

    public static void registerItems() {
        WOODEN_EXCAVATOR = register("wooden_excavator", ModToolMaterials.WOOD, 1.0F, -3.0F, settings("wooden_excavator"));
        STONE_EXCAVATOR = register("stone_excavator", ModToolMaterials.STONE, 1.5F, -3.0F, settings("stone_excavator"));
        IRON_EXCAVATOR = register("iron_excavator", ModToolMaterials.IRON, 2.0F, -3.0F, settings("iron_excavator"));
        GOLDEN_EXCAVATOR = register("golden_excavator", ModToolMaterials.GOLD, 1.0F, -3.2F, settings("golden_excavator"));
        DIAMOND_EXCAVATOR = register("diamond_excavator", ModToolMaterials.DIAMOND, 2.5F, -3.0F, settings("diamond_excavator"));
        NETHERITE_EXCAVATOR = register("netherite_excavator", ModToolMaterials.NETHERITE, 3.0F, -3.0F, settings("netherite_excavator").fireproof());

        WOODEN_HAMMER = registerHammer("wooden_hammer", ModToolMaterials.WOOD, 1.0F, -3.2F, settings("wooden_hammer"));
        STONE_HAMMER = registerHammer("stone_hammer", ModToolMaterials.STONE, 1.5F, -3.2F, settings("stone_hammer"));
        IRON_HAMMER = registerHammer("iron_hammer", ModToolMaterials.IRON, 2.0F, -3.2F, settings("iron_hammer"));
        GOLDEN_HAMMER = registerHammer("golden_hammer", ModToolMaterials.GOLD, 1.0F, -2.8F, settings("golden_hammer"));
        DIAMOND_HAMMER = registerHammer("diamond_hammer", ModToolMaterials.DIAMOND, 2.5F, -3.2F, settings("diamond_hammer"));
        NETHERITE_HAMMER = registerHammer("netherite_hammer", ModToolMaterials.NETHERITE, 3.0F, -3.2F, settings("netherite_hammer").fireproof());

        registerFuels();
    }

    private static Item register(String name, ToolMaterial material, float damage, float speed, Item.Settings settings) {
        return Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(ExcavatorsAndHammers.MOD_ID, name),
                //?} else {
                /*Identifier.of(ExcavatorsAndHammers.MOD_ID, name),
                *///?}
                new ExcavatorItem(material, damage, speed, settings)
        );
    }

    private static Item registerHammer(String name, ToolMaterial material, float damage, float speed, Item.Settings settings) {
        return Registry.register(
                Registries.ITEM,
                //? if <1.21 {
                new Identifier(ExcavatorsAndHammers.MOD_ID, name),
                //?} else {
                /*Identifier.of(ExcavatorsAndHammers.MOD_ID, name),
                *///?}
                new HammerItem(material, damage, speed, settings)
        );
    }

    private static void registerFuels() {
        //? if <1.21.2 {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(WOODEN_EXCAVATOR, 300);
        registry.add(WOODEN_HAMMER, 300);
        //?} else {
        /*FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(WOODEN_EXCAVATOR, 300);
            builder.add(WOODEN_HAMMER, 300);
        });
        *///?}
    }
}
