package net.cozystudios.tokimistoolshed.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LeafDecayScheduler {
    private static final Set<ScheduledLeaf> scheduledLeaves = ConcurrentHashMap.newKeySet();
    private static final int TICK_DELAY = 4;

    public static void scheduleLeafCheck(ServerWorld world, BlockPos pos, int delay) {
        scheduledLeaves.add(new ScheduledLeaf(world, pos.toImmutable(), delay));
    }

    public static void scheduleNeighborLeaves(ServerWorld world, BlockPos removedPos, int delay) {
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = removedPos.offset(dir);
            BlockState state = world.getBlockState(neighbor);
            if (state.isIn(BlockTags.LEAVES)) {
                scheduleLeafCheck(world, neighbor, delay);
            }
        }
    }

    public static void onServerTick() {
        List<ScheduledLeaf> toProcess = new ArrayList<>();
        List<ScheduledLeaf> toKeep = new ArrayList<>();

        Iterator<ScheduledLeaf> it = scheduledLeaves.iterator();
        while (it.hasNext()) {
            ScheduledLeaf leaf = it.next();
            it.remove();
            leaf.ticksRemaining--;
            if (leaf.ticksRemaining <= 0) {
                toProcess.add(leaf);
            } else {
                toKeep.add(leaf);
            }
        }
        scheduledLeaves.addAll(toKeep);

        for (ScheduledLeaf leaf : toProcess) {
            ServerWorld world = leaf.world;
            BlockPos pos = leaf.pos;

            //? if <1.21.11 {
            if (!world.isChunkLoaded(pos)) continue;
            //?} else {
            /*if (!world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4)) continue;
            *///?}

            BlockState state = world.getBlockState(pos);
            if (!state.isIn(BlockTags.LEAVES)) continue;
            if (state.contains(LeavesBlock.PERSISTENT) && state.get(LeavesBlock.PERSISTENT)) continue;

            state.scheduledTick(world, pos, world.getRandom());

            BlockState updatedState = world.getBlockState(pos);
            if (!updatedState.isIn(BlockTags.LEAVES)) {
                playLeafBreakSound(world, pos, state);
                scheduleNeighborLeaves(world, pos, TICK_DELAY);
                continue;
            }

            updatedState.randomTick(world, pos, world.getRandom());

            BlockState afterState = world.getBlockState(pos);
            if (!afterState.isIn(BlockTags.LEAVES)) {
                playLeafBreakSound(world, pos, updatedState);
                scheduleNeighborLeaves(world, pos, TICK_DELAY);
            }
        }
    }

    private static void playLeafBreakSound(ServerWorld world, BlockPos pos, BlockState state) {
        BlockSoundGroup soundGroup = state.getSoundGroup();
        world.playSound(null, pos, soundGroup.getBreakSound(), SoundCategory.BLOCKS,
                (soundGroup.getVolume() + 1.0F) / 2.0F * 0.3F,
                soundGroup.getPitch() * 0.8F);
    }

    public static void onWorldUnload(ServerWorld world) {
        scheduledLeaves.removeIf(leaf -> leaf.world == world);
    }

    private static class ScheduledLeaf {
        final ServerWorld world;
        final BlockPos pos;
        int ticksRemaining;

        ScheduledLeaf(ServerWorld world, BlockPos pos, int ticksRemaining) {
            this.world = world;
            this.pos = pos;
            this.ticksRemaining = ticksRemaining;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ScheduledLeaf other)) return false;
            return Objects.equals(world, other.world) && Objects.equals(pos, other.pos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(world, pos);
        }
    }
}
