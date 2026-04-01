package net.cozystudios.tokimistoolshed.item;

import net.cozystudios.tokimistoolshed.util.LastBreakData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
//? if >=1.21 {
/*import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipType;
*///?}
//? if >=1.21.5 {
/*import net.minecraft.component.type.TooltipDisplayComponent;
import java.util.function.Consumer;
import net.minecraft.text.Text;
*///?}

public class ExcavatorItem extends ShovelItem {
    private static boolean isExcavating = false;

    //? if <1.21 {
    public ExcavatorItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    //?} elif <1.21.2 {
    /*public ExcavatorItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, settings.attributeModifiers(ShovelItem.createAttributeModifiers(material, attackDamage, attackSpeed)));
    }
    *///?} else {
    /*public ExcavatorItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    *///?}

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = super.postMine(stack, world, state, pos, miner);

        //? if <1.21.11 {
        if (world.isClient || isExcavating) return result;
        //?} else {
        /*if (world.isClient() || isExcavating) return result;
        *///?}
        if (!(miner instanceof ServerPlayerEntity serverPlayer)) return result;
        boolean isCreative = serverPlayer.interactionManager.getGameMode().isCreative();
        boolean isSurvival = serverPlayer.interactionManager.getGameMode().isSurvivalLike();
        if (!state.isIn(BlockTags.SHOVEL_MINEABLE)) return result;
        if (serverPlayer.isSneaking()) return result;
        if (!isCreative && !isSurvival) return result;

        try {
            isExcavating = true;

            Direction face = LastBreakData.getFace(serverPlayer);

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;

                    BlockPos targetPos = getOffsetPos(pos, face, dx, dy);
                    BlockState targetState = world.getBlockState(targetPos);

                    if (targetState.isAir()) continue;
                    if (!targetState.isIn(BlockTags.SHOVEL_MINEABLE)) continue;
                    if (targetState.getHardness(world, targetPos) < 0) continue;

                    serverPlayer.interactionManager.tryBreakBlock(targetPos);
                }
            }
        } finally {
            isExcavating = false;
        }

        return result;
    }

    private BlockPos getOffsetPos(BlockPos origin, Direction face, int xOffset, int yOffset) {
        return switch (face) {
            case UP, DOWN -> origin.add(xOffset, 0, yOffset);
            case NORTH, SOUTH -> origin.add(xOffset, yOffset, 0);
            case EAST, WEST -> origin.add(0, yOffset, xOffset);
        };
    }

    //? if <1.21 {
    @Override
    public void appendTooltip(ItemStack stack, World world, java.util.List<net.minecraft.text.Text> tooltip, net.minecraft.client.item.TooltipContext context) {
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Mines a \u00a7a3\u00d73 \u00a77area"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Hold \u00a7eShift \u00a77to mine a single block"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    //?} elif <1.21.5 {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, java.util.List<net.minecraft.text.Text> tooltip, TooltipType type) {
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Mines a \u00a7a3\u00d73 \u00a77area"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Hold \u00a7eShift \u00a77to mine a single block"));
        super.appendTooltip(stack, context, tooltip, type);
    }
    *///?} else {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.literal("\u00a77Mines a \u00a7a3\u00d73 \u00a77area"));
        textConsumer.accept(Text.literal("\u00a77Hold \u00a7eShift \u00a77to mine a single block"));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
    *///?}
}
