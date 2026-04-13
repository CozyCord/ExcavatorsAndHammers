package net.cozystudios.tokimistoolshed.registry;

import net.cozystudios.tokimistoolshed.TokimisToolshed;
import net.cozystudios.tokimistoolshed.item.ExcavatorItem;
import net.cozystudios.tokimistoolshed.item.HammerItem;
import net.cozystudios.tokimistoolshed.item.ModToolMaterials;
import net.cozystudios.tokimistoolshed.item.ClippersItem;
import net.cozystudios.tokimistoolshed.item.CopperBucketItem;
import net.cozystudios.tokimistoolshed.item.CopperMilkBucketItem;
import net.cozystudios.tokimistoolshed.item.CopperPowderSnowBucketItem;
import net.cozystudios.tokimistoolshed.item.LumberAxeItem;
import net.cozystudios.tokimistoolshed.item.ScytheItem;
import net.cozystudios.tokimistoolshed.item.AbacusItem;
import net.cozystudios.tokimistoolshed.item.TrowelItem;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.level.material.Fluids;

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

    public static Item WOODEN_SCYTHE;
    public static Item STONE_SCYTHE;
    public static Item IRON_SCYTHE;
    public static Item GOLDEN_SCYTHE;
    public static Item DIAMOND_SCYTHE;
    public static Item NETHERITE_SCYTHE;

    public static Item WOODEN_LUMBER_AXE;
    public static Item STONE_LUMBER_AXE;
    public static Item IRON_LUMBER_AXE;
    public static Item GOLDEN_LUMBER_AXE;
    public static Item DIAMOND_LUMBER_AXE;
    public static Item NETHERITE_LUMBER_AXE;

    public static Item CLIPPERS;

    public static Item COPPER_BUCKET;
    public static Item COPPER_WATER_BUCKET;
    public static Item COPPER_LAVA_BUCKET;
    public static Item COPPER_POWDER_SNOW_BUCKET;
    public static Item COPPER_MILK_BUCKET;

    public static Item TROWEL;

    public static Item ABACUS;

    private static Item.Properties settings(String name) {
        return new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name)));
    }

    public static void registerItems() {
        WOODEN_EXCAVATOR = register("wooden_excavator", ModToolMaterials.WOOD, 1.0F, -3.0F, settings("wooden_excavator"));
        STONE_EXCAVATOR = register("stone_excavator", ModToolMaterials.STONE, 1.5F, -3.0F, settings("stone_excavator"));
        IRON_EXCAVATOR = register("iron_excavator", ModToolMaterials.IRON, 2.0F, -3.0F, settings("iron_excavator"));
        GOLDEN_EXCAVATOR = register("golden_excavator", ModToolMaterials.GOLD, 1.0F, -3.2F, settings("golden_excavator"));
        DIAMOND_EXCAVATOR = register("diamond_excavator", ModToolMaterials.DIAMOND, 2.5F, -3.0F, settings("diamond_excavator"));
        NETHERITE_EXCAVATOR = register("netherite_excavator", ModToolMaterials.NETHERITE, 3.0F, -3.0F, settings("netherite_excavator").fireResistant());

        WOODEN_HAMMER = registerHammer("wooden_hammer", ModToolMaterials.WOOD, 1.0F, -3.2F, settings("wooden_hammer"));
        STONE_HAMMER = registerHammer("stone_hammer", ModToolMaterials.STONE, 1.5F, -3.2F, settings("stone_hammer"));
        IRON_HAMMER = registerHammer("iron_hammer", ModToolMaterials.IRON, 2.0F, -3.2F, settings("iron_hammer"));
        GOLDEN_HAMMER = registerHammer("golden_hammer", ModToolMaterials.GOLD, 1.0F, -2.8F, settings("golden_hammer"));
        DIAMOND_HAMMER = registerHammer("diamond_hammer", ModToolMaterials.DIAMOND, 2.5F, -3.2F, settings("diamond_hammer"));
        NETHERITE_HAMMER = registerHammer("netherite_hammer", ModToolMaterials.NETHERITE, 3.0F, -3.2F, settings("netherite_hammer").fireResistant());

        WOODEN_SCYTHE = registerScythe("wooden_scythe", ModToolMaterials.WOOD, 5.0F, -2.6F, settings("wooden_scythe"));
        STONE_SCYTHE = registerScythe("stone_scythe", ModToolMaterials.STONE, 6.0F, -2.6F, settings("stone_scythe"));
        IRON_SCYTHE = registerScythe("iron_scythe", ModToolMaterials.IRON, 7.0F, -2.6F, settings("iron_scythe"));
        GOLDEN_SCYTHE = registerScythe("golden_scythe", ModToolMaterials.GOLD, 5.0F, -2.6F, settings("golden_scythe"));
        DIAMOND_SCYTHE = registerScythe("diamond_scythe", ModToolMaterials.DIAMOND, 8.0F, -2.6F, settings("diamond_scythe"));
        NETHERITE_SCYTHE = registerScythe("netherite_scythe", ModToolMaterials.NETHERITE, 9.0F, -2.6F, settings("netherite_scythe").fireResistant());

        WOODEN_LUMBER_AXE = registerLumberAxe("wooden_lumber_axe", ModToolMaterials.WOOD, 6.0F, -3.4F, settings("wooden_lumber_axe"));
        STONE_LUMBER_AXE = registerLumberAxe("stone_lumber_axe", ModToolMaterials.STONE, 10.0F, -3.4F, settings("stone_lumber_axe"));
        IRON_LUMBER_AXE = registerLumberAxe("iron_lumber_axe", ModToolMaterials.IRON, 10.0F, -3.3F, settings("iron_lumber_axe"));
        GOLDEN_LUMBER_AXE = registerLumberAxe("golden_lumber_axe", ModToolMaterials.GOLD, 6.0F, -3.2F, settings("golden_lumber_axe"));
        DIAMOND_LUMBER_AXE = registerLumberAxe("diamond_lumber_axe", ModToolMaterials.DIAMOND, 10.0F, -3.2F, settings("diamond_lumber_axe"));
        NETHERITE_LUMBER_AXE = registerLumberAxe("netherite_lumber_axe", ModToolMaterials.NETHERITE, 11.0F, -3.2F, settings("netherite_lumber_axe").fireResistant());

        CLIPPERS = registerClippers("clippers", 500, settings("clippers"));

        COPPER_BUCKET = registerBucket("copper_bucket",
                new CopperBucketItem(Fluids.EMPTY,
                        () -> COPPER_BUCKET, () -> COPPER_WATER_BUCKET,
                        () -> COPPER_LAVA_BUCKET, () -> COPPER_POWDER_SNOW_BUCKET,
                        settings("copper_bucket").durability(16)));
        COPPER_WATER_BUCKET = registerBucket("copper_water_bucket",
                new CopperBucketItem(Fluids.WATER,
                        () -> COPPER_BUCKET, () -> COPPER_WATER_BUCKET,
                        () -> COPPER_LAVA_BUCKET, () -> COPPER_POWDER_SNOW_BUCKET,
                        settings("copper_water_bucket").durability(16)));
        COPPER_LAVA_BUCKET = registerBucket("copper_lava_bucket",
                new CopperBucketItem(Fluids.LAVA,
                        () -> COPPER_BUCKET, () -> COPPER_WATER_BUCKET,
                        () -> COPPER_LAVA_BUCKET, () -> COPPER_POWDER_SNOW_BUCKET,
                        settings("copper_lava_bucket").durability(16)));
        COPPER_POWDER_SNOW_BUCKET = registerBucket("copper_powder_snow_bucket",
                new CopperPowderSnowBucketItem(() -> COPPER_BUCKET,
                        settings("copper_powder_snow_bucket").durability(16)));
        COPPER_MILK_BUCKET = registerBucket("copper_milk_bucket",
                new CopperMilkBucketItem(() -> COPPER_BUCKET,
                        settings("copper_milk_bucket").durability(16)
                                .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
                ));

        TROWEL = registerBucket("trowel", new TrowelItem(settings("trowel").stacksTo(1)));

        ABACUS = registerBucket("abacus", new AbacusItem(settings("abacus").stacksTo(1)));

        registerFuels();
    }

    private static Item register(String name, ToolMaterial material, float damage, float speed, Item.Properties settings) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name),
                new ExcavatorItem(material, damage, speed, settings)
        );
    }

    private static Item registerHammer(String name, ToolMaterial material, float damage, float speed, Item.Properties settings) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name),
                new HammerItem(material, damage, speed, settings)
        );
    }

    private static Item registerScythe(String name, ToolMaterial material, float damage, float speed, Item.Properties settings) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name),
                new ScytheItem(material, damage, speed, settings)
        );
    }

    public static Item registerLumberAxe(String name, ToolMaterial material, float damage, float speed, Item.Properties settings) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name),
                new LumberAxeItem(material, damage, speed, settings)
        );
    }

    private static Item registerClippers(String name, int durability, Item.Properties settings) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name),
                new ClippersItem(durability, settings)
        );
    }

    private static Item registerBucket(String name, Item item) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, name),
                item
        );
    }

    private static void registerFuels() {
        FuelValueEvents.BUILD.register((builder, context) -> {
            builder.add(WOODEN_EXCAVATOR, 300);
            builder.add(WOODEN_HAMMER, 300);
            builder.add(WOODEN_SCYTHE, 300);
            builder.add(WOODEN_LUMBER_AXE, 300);
        });
    }
}
