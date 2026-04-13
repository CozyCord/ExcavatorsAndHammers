package net.cozystudios.tokimistoolshed.item;

import net.cozystudios.tokimistoolshed.util.LastBreakData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import java.util.function.Consumer;

public class HammerItem extends Item {
    private static boolean isHammering = false;

    public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        super(settings.pickaxe(material, attackDamage, attackSpeed));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = super.mineBlock(stack, world, state, pos, miner);

        if (world.isClientSide() || isHammering) return result;
        if (!(miner instanceof ServerPlayer serverPlayer)) return result;
        boolean isCreative = serverPlayer.gameMode.getGameModeForPlayer().isCreative();
        boolean isSurvival = serverPlayer.gameMode.getGameModeForPlayer().isSurvival();
        if (!state.is(BlockTags.MINEABLE_WITH_PICKAXE)) return result;
        if (serverPlayer.isShiftKeyDown()) return result;
        if (!isCreative && !isSurvival) return result;

        try {
            isHammering = true;

            Direction face = LastBreakData.getFace(serverPlayer);

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;

                    BlockPos targetPos = getOffsetPos(pos, face, dx, dy);
                    BlockState targetState = world.getBlockState(targetPos);

                    if (targetState.isAir()) continue;
                    if (!targetState.is(BlockTags.MINEABLE_WITH_PICKAXE)) continue;
                    if (targetState.getDestroySpeed(world, targetPos) < 0) continue;
                    ItemStack held = miner.getMainHandItem();
                    if (!held.isCorrectToolForDrops(targetState)) continue;

                    serverPlayer.gameMode.destroyBlock(targetPos);
                }
            }
        } finally {
            isHammering = false;
        }

        return result;
    }

    private BlockPos getOffsetPos(BlockPos origin, Direction face, int xOffset, int yOffset) {
        return switch (face) {
            case UP, DOWN -> origin.offset(xOffset, 0, yOffset);
            case NORTH, SOUTH -> origin.offset(xOffset, yOffset, 0);
            case EAST, WEST -> origin.offset(0, yOffset, xOffset);
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.literal("\u00a77Mines a \u00a7a3\u00d73 \u00a77area"));
        textConsumer.accept(Component.literal("\u00a77Hold \u00a7eShift \u00a77to mine a single block"));
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
    }
}
