package net.cozystudios.tokimistoolshed.item;

import net.cozystudios.tokimistoolshed.util.LeafDecayScheduler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
//? if <1.21.5 {
import net.minecraft.item.AxeItem;
//?}
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

//? if <1.21.5 {
public class LumberAxeItem extends AxeItem {
//?} else {
/*public class LumberAxeItem extends Item {
*///?}
    private static boolean isLumbering = false;
    private static final int MAX_TREE_SIZE = 256;
    private static final int MIN_LEAVES_REQUIRED = 3;

    //? if <1.21 {
    public LumberAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    //?} elif <1.21.2 {
    /*public LumberAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, settings.attributeModifiers(AxeItem.createAttributeModifiers(material, attackDamage, attackSpeed)));
    }
    *///?} elif <1.21.5 {
    /*public LumberAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    *///?} else {
    /*public LumberAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(settings.axe(material, attackDamage, attackSpeed));
    }
    *///?}

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = super.postMine(stack, world, state, pos, miner);

        //? if <1.21.11 {
        if (world.isClient || isLumbering) return result;
        //?} else {
        /*if (world.isClient() || isLumbering) return result;
        *///?}
        if (!(miner instanceof ServerPlayerEntity serverPlayer)) return result;
        boolean isCreative = serverPlayer.interactionManager.getGameMode().isCreative();
        boolean isSurvival = serverPlayer.interactionManager.getGameMode().isSurvivalLike();
        if (!state.isIn(BlockTags.LOGS)) return result;
        if (serverPlayer.isSneaking()) return result;
        if (!isCreative && !isSurvival) return result;

        try {
            isLumbering = true;

            List<BlockPos> treeLogs = detectTree(world, pos, state);
            if (treeLogs.isEmpty()) return result;

            ServerWorld serverWorld = (ServerWorld) world;
            List<ItemStack> allDrops = new ArrayList<>();

            for (BlockPos logPos : treeLogs) {
                if (!isCreative && stack.isEmpty()) break;

                BlockState logState = world.getBlockState(logPos);
                if (!logState.isIn(BlockTags.LOGS)) continue;

                if (!isCreative) {
                    allDrops.addAll(Block.getDroppedStacks(logState, serverWorld, logPos, world.getBlockEntity(logPos), miner, stack));
                }

                world.removeBlock(logPos, false);
            }

            for (BlockPos logPos : treeLogs) {
                LeafDecayScheduler.scheduleNeighborLeaves(serverWorld, logPos, 4);
            }

            for (ItemStack drop : allDrops) {
                Block.dropStack(world, pos, drop);
            }

            if (!isCreative && !stack.isEmpty()) {
                int additionalDamage = treeLogs.size();
                //? if <1.21.5 {
                for (int i = 0; i < additionalDamage && !stack.isEmpty(); i++) {
                    super.postMine(stack, world, state, pos, miner);
                }
                //?} else {
                /*stack.damage(additionalDamage, serverWorld, serverPlayer, (item) -> {});
                *///?}
            }
        } finally {
            isLumbering = false;
        }

        return result;
    }

    private List<BlockPos> detectTree(World world, BlockPos startPos, BlockState startState) {
        Block logType = startState.getBlock();
        int startY = startPos.getY();

        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();
        List<BlockPos> treeLogs = new ArrayList<>();

        for (BlockPos neighbor : getNeighbors(startPos)) {
            if (neighbor.getY() >= startY) {
                BlockState neighborState = world.getBlockState(neighbor);
                if (neighborState.isIn(BlockTags.LOGS) && neighborState.getBlock() == logType) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
        visited.add(startPos);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            BlockState currentState = world.getBlockState(current);

            if (!currentState.isIn(BlockTags.LOGS) || currentState.getBlock() != logType) continue;

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
                if (neighborState.isIn(BlockTags.LOGS) && neighborState.getBlock() == logType) {
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
                    neighbors.add(pos.add(dx, dy, dz));
                }
            }
        }
        return neighbors;
    }

    private boolean hasNonNaturalNeighbor(World world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.offset(dir);
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
        if (state.isIn(BlockTags.LOGS)) return true;
        if (state.isIn(BlockTags.LEAVES)) return true;
        if (state.isIn(BlockTags.DIRT)) return true;
        if (state.isIn(BlockTags.SAND)) return true;
        if (state.isIn(BlockTags.SNOW)) return true;
        if (state.isIn(BlockTags.BEEHIVES)) return true;
        if (state.isIn(BlockTags.FIRE)) return true;
        if (state.getBlock() instanceof PlantBlock) return true;
        if (state.isReplaceable()) return true;
        return false;
    }

    private boolean hasEnoughLeaves(World world, List<BlockPos> treeLogs) {
        int maxY = treeLogs.stream().mapToInt(BlockPos::getY).max().orElse(0);

        int leafCount = 0;
        for (BlockPos logPos : treeLogs) {
            if (logPos.getY() < maxY - 1) continue;

            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = logPos.offset(dir);
                BlockState neighborState = world.getBlockState(neighborPos);

                if (neighborState.isIn(BlockTags.LEAVES)) {
                    if (neighborState.contains(LeavesBlock.PERSISTENT) && neighborState.get(LeavesBlock.PERSISTENT)) {
                        continue;
                    }
                    leafCount++;
                    if (leafCount >= MIN_LEAVES_REQUIRED) return true;
                }
            }
        }
        return false;
    }

    //? if <1.21 {
    @Override
    public void appendTooltip(ItemStack stack, World world, java.util.List<net.minecraft.text.Text> tooltip, net.minecraft.client.item.TooltipContext context) {
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Chops down \u00a7aentire trees"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Hold \u00a7eShift \u00a77to chop single block"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    //?} elif <1.21.5 {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, java.util.List<net.minecraft.text.Text> tooltip, TooltipType type) {
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Chops down \u00a7aentire trees"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Hold \u00a7eShift \u00a77to chop single block"));
        super.appendTooltip(stack, context, tooltip, type);
    }
    *///?} else {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.literal("\u00a77Chops down \u00a7aentire trees"));
        textConsumer.accept(Text.literal("\u00a77Hold \u00a7eShift \u00a77to chop single block"));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
    *///?}
}
