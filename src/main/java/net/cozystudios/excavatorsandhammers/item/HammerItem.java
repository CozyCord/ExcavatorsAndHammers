package net.cozystudios.excavatorsandhammers.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HammerItem extends PickaxeItem {
    // Prevent recursive breaking loops
    private static boolean isHammering = false;

    public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, (int) attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = super.postMine(stack, world, state, pos, miner);

        if (world.isClient || isHammering) return result;
        if (!(miner instanceof ServerPlayerEntity serverPlayer)) return result;
        boolean isCreative = serverPlayer.interactionManager.getGameMode().isCreative();
        boolean isSurvival = serverPlayer.interactionManager.getGameMode().isSurvivalLike();
        if (!state.isIn(BlockTags.PICKAXE_MINEABLE)) return result;
        if (serverPlayer.isSneaking()) return result;
        if (!isCreative && !isSurvival) return result;

        try {
            isHammering = true;

            // Determine face direction based on look vector
            Vec3d lookVec = miner.getRotationVec(1.0F);
            Direction face = getClosestFace(lookVec);

            // Break a 3×3 plane relative to the face
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;

                    BlockPos targetPos = getOffsetPos(pos, face, dx, dy);
                    BlockState targetState = world.getBlockState(targetPos);

                    if (targetState.isAir()) continue;
                    if (!targetState.isIn(BlockTags.PICKAXE_MINEABLE)) continue;
                    if (targetState.getHardness(world, targetPos) < 0) continue;

                    serverPlayer.interactionManager.tryBreakBlock(targetPos);
                }
            }

        } finally {
            isHammering = false;
        }

        return result;
    }

    private Direction getClosestFace(Vec3d look) {
        Direction best = Direction.NORTH;
        double bestDot = -1.0;
        for (Direction dir : Direction.values()) {
            double dot = look.dotProduct(Vec3d.of(dir.getVector()));
            if (dot > bestDot) {
                bestDot = dot;
                best = dir;
            }
        }
        return best;
    }

    private BlockPos getOffsetPos(BlockPos origin, Direction face, int xOffset, int yOffset) {
        return switch (face) {
            case UP, DOWN -> origin.add(xOffset, 0, yOffset);
            case NORTH, SOUTH -> origin.add(xOffset, yOffset, 0);
            case EAST, WEST -> origin.add(0, yOffset, xOffset);
        };
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, java.util.List<net.minecraft.text.Text> tooltip, net.minecraft.client.item.TooltipContext context) {
        tooltip.add(net.minecraft.text.Text.literal("§7Mines a §a3×3 §7area"));
        tooltip.add(net.minecraft.text.Text.literal("§7Hold §eShift §7to mine a single block"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}