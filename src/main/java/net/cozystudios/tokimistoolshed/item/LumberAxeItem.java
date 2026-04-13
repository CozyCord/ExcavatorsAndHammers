package net.cozystudios.tokimistoolshed.item;

import net.cozystudios.tokimistoolshed.util.LeafDecayScheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class LumberAxeItem extends Item {
    private static boolean isLumbering = false;
    private static final int MAX_TREE_SIZE = 256;
    private static final int MIN_LEAVES_REQUIRED = 3;

    public LumberAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        super(settings.axe(material, attackDamage, attackSpeed));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = super.mineBlock(stack, world, state, pos, miner);

        if (world.isClientSide() || isLumbering) return result;
        if (!(miner instanceof ServerPlayer serverPlayer)) return result;
        boolean isCreative = serverPlayer.gameMode.getGameModeForPlayer().isCreative();
        boolean isSurvival = serverPlayer.gameMode.getGameModeForPlayer().isSurvival();
        if (!state.is(BlockTags.LOGS)) return result;
        if (serverPlayer.isShiftKeyDown()) return result;
        if (!isCreative && !isSurvival) return result;

        try {
            isLumbering = true;

            List<BlockPos> treeLogs = detectTree(world, pos, state);
            if (treeLogs.isEmpty()) return result;

            ServerLevel serverWorld = (ServerLevel) world;
            List<ItemStack> allDrops = new ArrayList<>();

            for (BlockPos logPos : treeLogs) {
                if (!isCreative && stack.isEmpty()) break;

                BlockState logState = world.getBlockState(logPos);
                if (!logState.is(BlockTags.LOGS)) continue;

                if (!isCreative) {
                    allDrops.addAll(Block.getDrops(logState, serverWorld, logPos, world.getBlockEntity(logPos), miner, stack));
                }

                world.removeBlock(logPos, false);
            }

            for (BlockPos logPos : treeLogs) {
                LeafDecayScheduler.scheduleNeighborLeaves(serverWorld, logPos, 4);
            }

            for (ItemStack drop : allDrops) {
                Block.popResource(world, pos, drop);
            }

            if (!isCreative && !stack.isEmpty()) {
                int additionalDamage = treeLogs.size();
                stack.hurtAndBreak(additionalDamage, serverWorld, serverPlayer, (item) -> {});
            }
        } finally {
            isLumbering = false;
        }

        return result;
    }

    private List<BlockPos> detectTree(Level world, BlockPos startPos, BlockState startState) {
        Block logType = startState.getBlock();
        int startY = startPos.getY();

        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();
        List<BlockPos> treeLogs = new ArrayList<>();

        for (BlockPos neighbor : getNeighbors(startPos)) {
            if (neighbor.getY() >= startY) {
                BlockState neighborState = world.getBlockState(neighbor);
                if (neighborState.is(BlockTags.LOGS) && neighborState.getBlock() == logType) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        visited.add(startPos);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            BlockState currentState = world.getBlockState(current);

            if (!currentState.is(BlockTags.LOGS) || currentState.getBlock() != logType) continue;

            if (hasNonNaturalNeighbor(world, current)) {
                return List.of();
            }

            treeLogs.add(current);

            if (treeLogs.size() > MAX_TREE_SIZE) {
                return List.of();
            }

            for (BlockPos neighbor : getNeighbors(current)) {
                if (neighbor.getY() < startY) continue;
                if (visited.contains(neighbor)) continue;
                visited.add(neighbor);

                BlockState neighborState = world.getBlockState(neighbor);
                if (neighborState.is(BlockTags.LOGS) && neighborState.getBlock() == logType) {
                    queue.add(neighbor);
                }
            }
        }

        if (treeLogs.isEmpty()) return List.of();

        if (!hasEnoughLeaves(world, treeLogs)) {
            return List.of();
        }

        return treeLogs;
    }

    private List<BlockPos> getNeighbors(BlockPos pos) {
        List<BlockPos> neighbors = new ArrayList<>(26);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    neighbors.add(pos.offset(dx, dy, dz));
                }
            }
        }
        return neighbors;
    }

    private boolean hasNonNaturalNeighbor(Level world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (!isNaturalBlock(neighborState)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNaturalBlock(BlockState state) {
        if (state.isAir()) return true;
        if (!state.getFluidState().isEmpty()) return true;
        if (state.is(BlockTags.LOGS)) return true;
        if (state.is(BlockTags.LEAVES)) return true;
        if (state.is(BlockTags.DIRT)) return true;
        if (state.is(BlockTags.SAND)) return true;
        if (state.is(BlockTags.SNOW)) return true;
        if (state.is(BlockTags.BEEHIVES)) return true;
        if (state.is(BlockTags.FIRE)) return true;
        if (state.getBlock() instanceof VegetationBlock) return true;
        if (state.canBeReplaced()) return true;
        return false;
    }

    private boolean hasEnoughLeaves(Level world, List<BlockPos> treeLogs) {
        int maxY = treeLogs.stream().mapToInt(BlockPos::getY).max().orElse(0);

        int leafCount = 0;
        for (BlockPos logPos : treeLogs) {
            if (logPos.getY() < maxY - 1) continue;

            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = logPos.relative(dir);
                BlockState neighborState = world.getBlockState(neighborPos);

                if (neighborState.is(BlockTags.LEAVES)) {
                    if (neighborState.hasProperty(LeavesBlock.PERSISTENT) && neighborState.getValue(LeavesBlock.PERSISTENT)) {
                        continue;
                    }
                    leafCount++;
                    if (leafCount >= MIN_LEAVES_REQUIRED) return true;
                }
            }
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.literal("\u00a77Chops down \u00a7aentire trees"));
        textConsumer.accept(Component.literal("\u00a77Hold \u00a7eShift \u00a77to chop single block"));
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
    }
}
