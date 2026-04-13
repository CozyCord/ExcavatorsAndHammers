package net.cozystudios.tokimistoolshed.item;

//? if <1.21 {
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.item.Items;

public enum ModToolMaterials implements ToolMaterial {
    WOOD(144, 2.0f, 1.0f, 0, 10, Ingredient.ofItems(Items.OAK_PLANKS)),
    STONE(324, 4.0f, 1.5f, 1, 8, Ingredient.ofItems(Items.COBBLESTONE)),
    COPPER(477, 5.0f, 1.75f, 1, 11, Ingredient.ofItems(Items.COPPER_INGOT)),
    IRON(621, 6.0f, 2.0f, 2, 14, Ingredient.ofItems(Items.IRON_INGOT)),
    GOLD(81, 12.0f, 1.0f, 1, 4, Ingredient.ofItems(Items.GOLD_INGOT)),
    DIAMOND(3906, 8.0f, 2.5f, 3, 10, Ingredient.ofItems(Items.DIAMOND)),
    NETHERITE(5076, 9.0f, 3.0f, 4, 15, Ingredient.ofItems(Items.NETHERITE_INGOT)),
    OBSIDIAN(4000, 8.5f, 2.75f, 4, 12, Ingredient.ofItems(Items.OBSIDIAN));

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
    public int getDurability() { return durability; }

    @Override
    public float getMiningSpeedMultiplier() { return miningSpeedMultiplier; }

    @Override
    public float getAttackDamage() { return attackDamage; }

    @Override
    public int getMiningLevel() { return miningLevel; }

    @Override
    public int getEnchantability() { return enchantability; }

    @Override
    public Ingredient getRepairIngredient() { return repairIngredient; }
}
//?} elif <1.21.2 {
/*import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public enum ModToolMaterials implements ToolMaterial {
    WOOD(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 250, 4.0f, 1.0f, 10, Ingredient.ofItems(Items.OAK_PLANKS)),
    STONE(BlockTags.INCORRECT_FOR_STONE_TOOL, 600, 5.0f, 1.5f, 8, Ingredient.ofItems(Items.COBBLESTONE)),
    COPPER(BlockTags.INCORRECT_FOR_STONE_TOOL, 900, 5.5f, 1.75f, 11, Ingredient.ofItems(Items.COPPER_INGOT)),
    IRON(BlockTags.INCORRECT_FOR_IRON_TOOL, 1250, 6.0f, 2.0f, 14, Ingredient.ofItems(Items.IRON_INGOT)),
    GOLD(BlockTags.INCORRECT_FOR_GOLD_TOOL, 65, 12.0f, 1.0f, 4, Ingredient.ofItems(Items.GOLD_INGOT)),
    DIAMOND(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2850, 8.0f, 2.5f, 10, Ingredient.ofItems(Items.DIAMOND)),
    NETHERITE(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 6500, 9.0f, 3.0f, 15, Ingredient.ofItems(Items.NETHERITE_INGOT)),
    OBSIDIAN(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 4000, 8.5f, 2.75f, 12, Ingredient.ofItems(Items.OBSIDIAN));

    private final TagKey<Block> inverseTag;
    private final int durability;
    private final float miningSpeedMultiplier;
    private final float attackDamage;
    private final int enchantability;
    private final Ingredient repairIngredient;

    ModToolMaterials(TagKey<Block> inverseTag, int durability, float miningSpeedMultiplier, float attackDamage,
                     int enchantability, Ingredient repairIngredient) {
        this.inverseTag = inverseTag;
        this.durability = durability;
        this.miningSpeedMultiplier = miningSpeedMultiplier;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() { return durability; }

    @Override
    public float getMiningSpeedMultiplier() { return miningSpeedMultiplier; }

    @Override
    public float getAttackDamage() { return attackDamage; }

    @Override
    public TagKey<Block> getInverseTag() { return inverseTag; }

    @Override
    public int getEnchantability() { return enchantability; }

    @Override
    public Ingredient getRepairIngredient() { return repairIngredient; }
}
*///?} else {
/*import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModToolMaterials {
    private static final TagKey<Item> COPPER_REPAIR_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of("tokimistoolshed", "copper_repair_materials"));

    public static final ToolMaterial WOOD = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 250, 4.0f, 1.0f, 10, ItemTags.PLANKS);
    public static final ToolMaterial STONE = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 600, 5.0f, 1.5f, 8, ItemTags.STONE_TOOL_MATERIALS);
    //? if <1.21.9 {
    public static final ToolMaterial COPPER = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 900, 5.5f, 1.75f, 11, COPPER_REPAIR_MATERIALS);
    //?} else {
    public static final ToolMaterial COPPER = new ToolMaterial(BlockTags.INCORRECT_FOR_COPPER_TOOL, 900, 5.5f, 1.75f, 11, COPPER_REPAIR_MATERIALS);
    //?}
    public static final ToolMaterial IRON = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 1250, 6.0f, 2.0f, 14, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial GOLD = new ToolMaterial(BlockTags.INCORRECT_FOR_GOLD_TOOL, 65, 12.0f, 1.0f, 4, ItemTags.GOLD_TOOL_MATERIALS);
    public static final ToolMaterial DIAMOND = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2850, 8.0f, 2.5f, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial NETHERITE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 6500, 9.0f, 3.0f, 15, ItemTags.NETHERITE_TOOL_MATERIALS);
    public static final ToolMaterial OBSIDIAN = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 4000, 8.5f, 2.75f, 12, ItemTags.DIAMOND_TOOL_MATERIALS);
}
*///?}
