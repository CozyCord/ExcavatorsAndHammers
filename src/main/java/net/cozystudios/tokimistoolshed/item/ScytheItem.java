package net.cozystudios.tokimistoolshed.item;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class ScytheItem extends HoeItem {
    private static boolean isScything = false;

    public ScytheItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null || context.getPlayer().isShiftKeyDown()) {
            return super.useOn(context);
        }

        InteractionResult centerResult = super.useOn(context);
        if (!centerResult.consumesAction()) {
            return centerResult;
        }

        BlockPos center = context.getClickedPos();
        Level world = context.getLevel();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;

                if (context.getItemInHand().isEmpty()) return centerResult;

                BlockPos targetPos = center.offset(dx, 0, dz);
                BlockHitResult hit = new BlockHitResult(
                        new Vec3(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5),
                        Direction.UP,
                        targetPos,
                        false
                );
                UseOnContext targetContext = new UseOnContext(
                        world, context.getPlayer(), context.getHand(),
                        context.getItemInHand(), hit
                );
                super.useOn(targetContext);
            }
        }

        return centerResult;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = super.mineBlock(stack, world, state, pos, miner);

        if (world.isClientSide() || isScything) return result;
        if (!(miner instanceof ServerPlayer serverPlayer)) return result;
        boolean isCreative = serverPlayer.gameMode.getGameModeForPlayer().isCreative();
        boolean isSurvival = serverPlayer.gameMode.getGameModeForPlayer().isSurvival();
        if (!state.is(BlockTags.MINEABLE_WITH_HOE) && !(state.getBlock() instanceof VegetationBlock)) return result;
        if (serverPlayer.isShiftKeyDown()) return result;
        if (!isCreative && !isSurvival) return result;

        try {
            isScything = true;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dz == 0) continue;

                    BlockPos targetPos = pos.offset(dx, 0, dz);
                    BlockState targetState = world.getBlockState(targetPos);

                    if (targetState.isAir()) continue;
                    if (!targetState.is(BlockTags.MINEABLE_WITH_HOE) && !(targetState.getBlock() instanceof VegetationBlock)) continue;
                    if (targetState.getDestroySpeed(world, targetPos) < 0) continue;

                    serverPlayer.gameMode.destroyBlock(targetPos);
                }
            }
        } finally {
            isScything = false;
        }

        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.literal("\u00a77Tills a \u00a7a3\u00d73 \u00a77area"));
        textConsumer.accept(Component.literal("\u00a77Cuts vegetation in a \u00a7a3\u00d73 \u00a77area"));
        textConsumer.accept(Component.literal("\u00a77Hold \u00a7eShift \u00a77for single block"));
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
    }
}
