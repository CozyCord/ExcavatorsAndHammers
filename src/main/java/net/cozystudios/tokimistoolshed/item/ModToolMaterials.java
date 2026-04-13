package net.cozystudios.tokimistoolshed.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ModToolMaterials {
    private static final TagKey<Item> COPPER_REPAIR_MATERIALS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("tokimistoolshed", "copper_repair_materials"));

    public static final ToolMaterial WOOD = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 250, 4.0f, 1.0f, 10, ItemTags.PLANKS);
    public static final ToolMaterial STONE = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 600, 5.0f, 1.5f, 8, ItemTags.STONE_TOOL_MATERIALS);
    public static final ToolMaterial COPPER = new ToolMaterial(BlockTags.INCORRECT_FOR_COPPER_TOOL, 900, 5.5f, 1.75f, 11, COPPER_REPAIR_MATERIALS);
    public static final ToolMaterial IRON = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 1250, 6.0f, 2.0f, 14, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial GOLD = new ToolMaterial(BlockTags.INCORRECT_FOR_GOLD_TOOL, 65, 12.0f, 1.0f, 4, ItemTags.GOLD_TOOL_MATERIALS);
    public static final ToolMaterial DIAMOND = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2850, 8.0f, 2.5f, 10, ItemTags.DIAMOND_TOOL_MATERIALS);
    public static final ToolMaterial NETHERITE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 6500, 9.0f, 3.0f, 15, ItemTags.NETHERITE_TOOL_MATERIALS);
    public static final ToolMaterial OBSIDIAN = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 4000, 8.5f, 2.75f, 12, ItemTags.DIAMOND_TOOL_MATERIALS);
}
