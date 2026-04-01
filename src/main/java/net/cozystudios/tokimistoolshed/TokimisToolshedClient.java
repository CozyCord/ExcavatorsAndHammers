package net.cozystudios.tokimistoolshed;

import net.cozystudios.tokimistoolshed.client.AbacusHudRenderer;
import net.cozystudios.tokimistoolshed.client.AbacusOutlineRenderer;
import net.cozystudios.tokimistoolshed.item.ClippersItem;
import net.cozystudios.tokimistoolshed.item.ExcavatorItem;
import net.cozystudios.tokimistoolshed.item.HammerItem;
import net.cozystudios.tokimistoolshed.item.ScytheItem;
import net.fabricmc.api.ClientModInitializer;
//? if <1.21.11 {
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
//?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
*///?}
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
//? if <1.21.11 {
import net.minecraft.client.render.RenderLayer;
//?} else {
/*import net.minecraft.client.render.RenderLayers;
*///?}
import net.minecraft.client.render.VertexConsumerProvider;
//? if <1.21.2 {
import net.minecraft.client.render.WorldRenderer;
//?} elif <1.21.11 {
/*import net.minecraft.client.render.debug.DebugRenderer;
*///?} else {
/*import net.minecraft.client.render.VertexRendering;
*///?}
//? if <1.21.11 {
import com.mojang.blaze3d.systems.RenderSystem;
//?}
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class TokimisToolshedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //? if <1.21.11 {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(this::renderAOEOutline);
        //?} else {
        /*WorldRenderEvents.AFTER_ENTITIES.register(this::renderAOEOutline);
        *///?}

        AbacusOutlineRenderer.register();
        AbacusHudRenderer.register();
    }

    private void renderAOEOutline(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        boolean isExcavatorOrHammer = client.player.getMainHandStack().getItem() instanceof ExcavatorItem
                || client.player.getMainHandStack().getItem() instanceof HammerItem
                || client.player.getMainHandStack().getItem() instanceof ClippersItem;
        boolean isScythe = client.player.getMainHandStack().getItem() instanceof ScytheItem;

        if (!isExcavatorOrHammer && !isScythe) {
            return;
        }

        if (client.crosshairTarget == null || client.crosshairTarget.getType() != HitResult.Type.BLOCK)
            return;

        BlockHitResult hit = (BlockHitResult) client.crosshairTarget;
        BlockPos pos = hit.getBlockPos();
        Direction face = isScythe ? Direction.UP : hit.getSide();

        boolean sneaking = client.player.isSneaking();
        if (sneaking) return;

        render3x3Outline(context, client.world, pos, face, client.gameRenderer.getCamera());
    }

    private void render3x3Outline(WorldRenderContext context, World world, BlockPos center, Direction face, Camera camera) {
        //? if <1.21.11 {
        MatrixStack matrices = context.matrixStack();
        //?} else {
        /*MatrixStack matrices = context.matrices();
        *///?}
        VertexConsumerProvider consumers = context.consumers();
        //? if <1.21.11 {
        Vec3d camPos = camera.getPos();
        //?} else {
        /*Vec3d camPos = camera.getCameraPos();
        *///?}

        if (consumers == null) return;

        //? if <1.21.11 {
        RenderSystem.lineWidth(3.0f);
        //?}

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

                //? if <1.21.2 {
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
                //?} elif <1.21.11 {
                /*DebugRenderer.drawVoxelShapeOutlines(
                        matrices,
                        consumers.getBuffer(RenderLayer.getLines()),
                        shape,
                        targetPos.getX() - camPos.x,
                        targetPos.getY() - camPos.y,
                        targetPos.getZ() - camPos.z,
                        0.0f, 0.0f, 0.0f, 0.4f,
                        true
                );
                *///?} else {
                /*VertexRendering.drawOutline(
                        matrices,
                        consumers.getBuffer(RenderLayers.LINES),
                        shape,
                        targetPos.getX() - camPos.x,
                        targetPos.getY() - camPos.y,
                        targetPos.getZ() - camPos.z,
                        0x66000000,
                        3.0f
                );
                *///?}
            }
        }

        //? if <1.21.11 {
        RenderSystem.lineWidth(1.0f);
        //?}
    }
}
