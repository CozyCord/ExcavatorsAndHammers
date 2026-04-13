package net.cozystudios.tokimistoolshed.util;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public class LastBreakData {

    private static final ConcurrentHashMap<UUID, Direction> LAST_FACE = new ConcurrentHashMap<>();

    public static void setFace(Player player, Direction face) {
        LAST_FACE.put(player.getUUID(), face);
    }

    public static Direction getFace(Player player) {
        return LAST_FACE.getOrDefault(player.getUUID(), Direction.NORTH);
    }
}