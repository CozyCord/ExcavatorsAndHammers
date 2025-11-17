package net.cozystudios.excavatorsandhammers.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.item.Items;

public enum ModToolMaterials implements ToolMaterial {
    WOOD(250, 4.0f, 1.0f, 0, 10, Ingredient.ofItems(Items.OAK_PLANKS)),
    STONE(600, 5.0f, 1.5f, 1, 8, Ingredient.ofItems(Items.COBBLESTONE)),
    IRON(1250, 6.0f, 2.0f, 2, 14, Ingredient.ofItems(Items.IRON_INGOT)),
    GOLD(65, 12.0f, 1.0f, 1, 4, Ingredient.ofItems(Items.GOLD_INGOT)),
    DIAMOND(2850, 8.0f, 2.5f, 3, 10, Ingredient.ofItems(Items.DIAMOND)),
    NETHERITE(6500, 9.0f, 3.0f, 4, 15, Ingredient.ofItems(Items.NETHERITE_INGOT));

    private final int durability;
    private final float miningSpeedMultiplier;
    private final float attackDamage;
    private final int miningLevel;
    private final int enchantability;
    private final Ingredient repairIngredient;

    ModToolMaterials(int durability, float miningSpeedMultiplier, float attackDamage,
                     int miningLevel, int enchantability, Ingredient repairIngredient) {
        this.durability = durability;
        this.miningSpeedMultiplier = miningSpeedMultiplier;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return miningSpeedMultiplier;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }
}