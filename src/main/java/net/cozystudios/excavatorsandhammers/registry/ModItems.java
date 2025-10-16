package net.cozystudios.excavatorsandhammers.registry;

import net.cozystudios.excavatorsandhammers.ExcavatorsAndHammers;
import net.cozystudios.excavatorsandhammers.item.ExcavatorItem;
import net.cozystudios.excavatorsandhammers.item.HammerItem;
import net.cozystudios.excavatorsandhammers.item.ModToolMaterials;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

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

    public static void registerItems() {
        WOODEN_EXCAVATOR = register("wooden_excavator", ModToolMaterials.WOOD, 1.0F, -3.0F, new Item.Settings());
        STONE_EXCAVATOR = register("stone_excavator", ModToolMaterials.STONE, 1.5F, -3.0F, new Item.Settings());
        IRON_EXCAVATOR = register("iron_excavator", ModToolMaterials.IRON, 2.0F, -3.0F, new Item.Settings());
        GOLDEN_EXCAVATOR = register("golden_excavator", ModToolMaterials.GOLD, 1.0F, -3.2F, new Item.Settings());
        DIAMOND_EXCAVATOR = register("diamond_excavator", ModToolMaterials.DIAMOND, 2.5F, -3.0F, new Item.Settings());
        NETHERITE_EXCAVATOR = register("netherite_excavator", ModToolMaterials.NETHERITE, 3.0F, -3.0F, new Item.Settings().fireproof());

        WOODEN_HAMMER = registerHammer("wooden_hammer", ModToolMaterials.WOOD, 1.0F, -3.2F, new Item.Settings());
        STONE_HAMMER = registerHammer("stone_hammer", ModToolMaterials.STONE, 1.5F, -3.2F, new Item.Settings());
        IRON_HAMMER = registerHammer("iron_hammer", ModToolMaterials.IRON, 2.0F, -3.2F, new Item.Settings());
        GOLDEN_HAMMER = registerHammer("golden_hammer", ModToolMaterials.GOLD, 1.0F, -2.8F, new Item.Settings());
        DIAMOND_HAMMER = registerHammer("diamond_hammer", ModToolMaterials.DIAMOND, 2.5F, -3.2F, new Item.Settings());
        NETHERITE_HAMMER = registerHammer("netherite_hammer", ModToolMaterials.NETHERITE, 3.0F, -3.2F, new Item.Settings().fireproof());

        registerFuels();
    }

    private static Item register(String name, ModToolMaterials material, float damage, float speed, Item.Settings settings) {
        return Registry.register(
                Registries.ITEM,
                new Identifier(ExcavatorsAndHammers.MOD_ID, name),
                new ExcavatorItem(material, damage, speed, settings)
        );
    }

    private static Item registerHammer(String name, ModToolMaterials material, float damage, float speed, Item.Settings settings) {
        return Registry.register(
                Registries.ITEM,
                new Identifier(ExcavatorsAndHammers.MOD_ID, name),
                new HammerItem(material, damage, speed, settings)
        );
    }

    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(WOODEN_EXCAVATOR, 300);
        registry.add(WOODEN_HAMMER, 300);
    }
}