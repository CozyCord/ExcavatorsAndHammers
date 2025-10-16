package net.cozystudios.excavatorsandhammers.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExcavatorItem extends ShovelItem {
    // Flag to prevent recursion during area breaking
    private static boolean isExcavating = false;

    public ExcavatorItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        // Let vanilla handle normal block logic first
        boolean result = super.postMine(stack, world, state, pos, miner);

        // Only run server-side, not recursively
        if (world.isClient || isExcavating) return result;
        if (!(miner instanceof ServerPlayerEntity serverPlayer)) return result;
        boolean isCreative = serverPlayer.interactionManager.getGameMode().isCreative();
        boolean isSurvival = serverPlayer.interactionManager.getGameMode().isSurvivalLike();
        if (!state.isIn(BlockTags.SHOVEL_MINEABLE)) return result;
        if (serverPlayer.isSneaking()) return result;
        if (!isCreative && !isSurvival) return result;

        try {
            isExcavating = true; // start multi-break mode

            // Determine which face the player is looking at
            Vec3d lookVec = miner.getRotationVec(1.0F);
            Direction face = getClosestFace(lookVec);

            // Break 3×3 plane depending on hit face
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
            isExcavating = false; // reset no matter what
        }

        return result;
    }

    /**
     * Determines which face direction the player is looking most closely at.
     */
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

    /**
     * Offsets the 3×3 area based on which face is targeted.
     * For top/bottom faces -> horizontal plane.
     * For sides -> vertical plane.
     */
    private BlockPos getOffsetPos(BlockPos origin, Direction face, int xOffset, int yOffset) {
        return switch (face) {
            case UP, DOWN -> origin.add(xOffset, 0, yOffset); // horizontal plane
            case NORTH, SOUTH -> origin.add(xOffset, yOffset, 0); // vertical (north/south)
            case EAST, WEST -> origin.add(0, yOffset, xOffset); // vertical (east/west)
        };
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, java.util.List<net.minecraft.text.Text> tooltip, net.minecraft.client.item.TooltipContext context) {
        tooltip.add(net.minecraft.text.Text.literal("§7Mines a §a3×3 §7area"));
        tooltip.add(net.minecraft.text.Text.literal("§7Hold §eShift §7to mine a single block"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}