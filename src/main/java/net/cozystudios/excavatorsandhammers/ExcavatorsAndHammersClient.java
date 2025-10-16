package net.cozystudios.excavatorsandhammers;

import net.cozystudios.excavatorsandhammers.item.ExcavatorItem;
import net.cozystudios.excavatorsandhammers.item.HammerItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class ExcavatorsAndHammersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::renderAOEOutline);
    }

    private void renderAOEOutline(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;


        if (!(client.player.getMainHandStack().getItem() instanceof ExcavatorItem
                || client.player.getMainHandStack().getItem() instanceof HammerItem)) {
            return;
        }


        if (client.crosshairTarget == null || client.crosshairTarget.getType() != HitResult.Type.BLOCK)
            return;

        BlockHitResult hit = (BlockHitResult) client.crosshairTarget;
        BlockPos pos = hit.getBlockPos();
        Direction face = hit.getSide();

        boolean sneaking = client.player.isSneaking();
        if (sneaking) return;

        render3x3Outline(context, client.world, pos, face, client.gameRenderer.getCamera());
    }

    private void render3x3Outline(WorldRenderContext context, World world, BlockPos center, Direction face, Camera camera) {
        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider consumers = context.consumers();
        Vec3d camPos = camera.getPos();

        if (consumers == null) return;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                BlockPos targetPos = switch (face) {
                    case UP, DOWN -> center.add(dx, 0, dy);
                    case NORTH, SOUTH -> center.add(dx, dy, 0);
                    case EAST, WEST -> center.add(0, dy, dx);
                };

                VoxelShape shape = world.getBlockState(targetPos).getOutlineShape(world, targetPos);
                if (shape.isEmpty()) continue;


                WorldRenderer.drawShapeOutline(
                        matrices,
                        consumers.getBuffer(RenderLayer.getLines()),
                        shape,
                        targetPos.getX() - camPos.x,
                        targetPos.getY() - camPos.y,
                        targetPos.getZ() - camPos.z,
                        0.0f, 0.0f, 0.0f, 0.4f,
                        true
                );
            }
        }
    }
}