package net.cozystudios.tokimistoolshed;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cozystudios.tokimistoolshed.client.AbacusHudRenderer;
import net.cozystudios.tokimistoolshed.client.AbacusOutlineRenderer;
import net.cozystudios.tokimistoolshed.item.ClippersItem;
import net.cozystudios.tokimistoolshed.item.ExcavatorItem;
import net.cozystudios.tokimistoolshed.item.HammerItem;
import net.cozystudios.tokimistoolshed.item.ScytheItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TokimisToolshedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LevelRenderEvents.AFTER_SOLID_FEATURES.register(this::renderAOEOutline);

        AbacusOutlineRenderer.register();
        AbacusHudRenderer.register();
    }

    private void renderAOEOutline(LevelRenderContext context) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.level == null) return;

        boolean isExcavatorOrHammer = client.player.getMainHandItem().getItem() instanceof ExcavatorItem
                || client.player.getMainHandItem().getItem() instanceof HammerItem
                || client.player.getMainHandItem().getItem() instanceof ClippersItem;
        boolean isScythe = client.player.getMainHandItem().getItem() instanceof ScytheItem;

        if (!isExcavatorOrHammer && !isScythe) {
            return;
        }

        if (client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK)
            return;

        BlockHitResult hit = (BlockHitResult) client.hitResult;
        BlockPos pos = hit.getBlockPos();
        Direction face = isScythe ? Direction.UP : hit.getDirection();

        boolean sneaking = client.player.isShiftKeyDown();
        if (sneaking) return;

        render3x3Outline(context, client.level, pos, face, client.gameRenderer.getMainCamera());
    }

    private void render3x3Outline(LevelRenderContext context, Level world, BlockPos center, Direction face, Camera camera) {
        PoseStack matrices = context.poseStack();
        MultiBufferSource consumers = context.bufferSource();
        Vec3 camPos = camera.position();

        if (consumers == null) return;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                BlockPos targetPos = switch (face) {
                    case UP, DOWN -> center.offset(dx, 0, dy);
                    case NORTH, SOUTH -> center.offset(dx, dy, 0);
                    case EAST, WEST -> center.offset(0, dy, dx);
                };

                VoxelShape shape = world.getBlockState(targetPos).getShape(world, targetPos);
                if (shape.isEmpty()) continue;

                ShapeRenderer.renderShape(
                        matrices,
                        consumers.getBuffer(RenderTypes.LINES),
                        shape,
                        targetPos.getX() - camPos.x,
                        targetPos.getY() - camPos.y,
                        targetPos.getZ() - camPos.z,
                        0x66000000,
                        3.0f
                );
            }
        }

    }
}
