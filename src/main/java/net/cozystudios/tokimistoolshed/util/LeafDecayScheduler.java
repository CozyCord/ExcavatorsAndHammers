package net.cozystudios.tokimistoolshed.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class LeafDecayScheduler {
    private static final Set<ScheduledLeaf> scheduledLeaves = ConcurrentHashMap.newKeySet();
    private static final int TICK_DELAY = 4;

    public static void scheduleLeafCheck(ServerLevel world, BlockPos pos, int delay) {
        scheduledLeaves.add(new ScheduledLeaf(world, pos.immutable(), delay));
    }

    public static void scheduleNeighborLeaves(ServerLevel world, BlockPos removedPos, int delay) {
        for (Direction dir : Direction.values()) {
            BlockPos neighbor = removedPos.relative(dir);
            BlockState state = world.getBlockState(neighbor);
            if (state.is(BlockTags.LEAVES)) {
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
            ServerLevel world = leaf.world;
            BlockPos pos = leaf.pos;

            if (!world.hasChunk(pos.getX() >> 4, pos.getZ() >> 4)) continue;

            BlockState state = world.getBlockState(pos);
            if (!state.is(BlockTags.LEAVES)) continue;
            if (state.hasProperty(LeavesBlock.PERSISTENT) && state.getValue(LeavesBlock.PERSISTENT)) continue;

            state.tick(world, pos, world.getRandom());

            BlockState updatedState = world.getBlockState(pos);
            if (!updatedState.is(BlockTags.LEAVES)) {
                playLeafBreakSound(world, pos, state);
                scheduleNeighborLeaves(world, pos, TICK_DELAY);
                continue;
            }

            updatedState.randomTick(world, pos, world.getRandom());

            BlockState afterState = world.getBlockState(pos);
            if (!afterState.is(BlockTags.LEAVES)) {
                playLeafBreakSound(world, pos, updatedState);
                scheduleNeighborLeaves(world, pos, TICK_DELAY);
            }
        }
    }

    private static void playLeafBreakSound(ServerLevel world, BlockPos pos, BlockState state) {
        SoundType soundGroup = state.getSoundType();
        world.playSound(null, pos, soundGroup.getBreakSound(), SoundSource.BLOCKS,
                (soundGroup.getVolume() + 1.0F) / 2.0F * 0.3F,
                soundGroup.getPitch() * 0.8F);
    }

    public static void onWorldUnload(ServerLevel world) {
        scheduledLeaves.removeIf(leaf -> leaf.world == world);
    }

    private static class ScheduledLeaf {
        final ServerLevel world;
        final BlockPos pos;
        int ticksRemaining;

        ScheduledLeaf(ServerLevel world, BlockPos pos, int ticksRemaining) {
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
