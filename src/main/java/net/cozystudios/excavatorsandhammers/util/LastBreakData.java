package net.cozystudios.excavatorsandhammers.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LastBreakData {

    private static final ConcurrentHashMap<UUID, Direction> LAST_FACE = new ConcurrentHashMap<>();

    public static void setFace(PlayerEntity player, Direction face) {
        LAST_FACE.put(player.getUuid(), face);
    }

    public static Direction getFace(PlayerEntity player) {
        return LAST_FACE.getOrDefault(player.getUuid(), Direction.NORTH);
    }
}