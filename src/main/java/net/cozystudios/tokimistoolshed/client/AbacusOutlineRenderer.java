package net.cozystudios.tokimistoolshed.client;

import net.cozystudios.tokimistoolshed.item.AbacusItem;
import net.cozystudios.tokimistoolshed.registry.ModItems;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class AbacusOutlineRenderer {

    private static final int MAX_AXIS_LENGTH = 128;

    public static void register() {
        //? if <1.21.11 {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(AbacusOutlineRenderer::render);
        //?} else {
        /*WorldRenderEvents.AFTER_ENTITIES.register(AbacusOutlineRenderer::render);
        *///?}
    }

    private static void render(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        ItemStack abacusStack = getHeldAbacus(client);
        if (abacusStack == null || !AbacusItem.isBound(abacusStack)) return;

        if (client.crosshairTarget == null || client.crosshairTarget.getType() != HitResult.Type.BLOCK) return;

        BlockPos targetPos = ((BlockHitResult) client.crosshairTarget).getBlockPos();
        BlockPos boundPos = AbacusItem.getBoundPos(abacusStack);
        if (boundPos == null) return;

        int distance = AbacusItem.getDistance(abacusStack, targetPos);
        if (distance < 0) return;

        renderBoundingBox(context, boundPos, targetPos, client.gameRenderer.getCamera());
    }

    private static ItemStack getHeldAbacus(MinecraftClient client) {
        ItemStack mainHand = client.player.getMainHandStack();
        if (mainHand.isOf(ModItems.ABACUS)) return mainHand;
        ItemStack offHand = client.player.getOffHandStack();
        if (offHand.isOf(ModItems.ABACUS)) return offHand;
        return null;
    }

    private static void renderBoundingBox(WorldRenderContext context, BlockPos boundPos, BlockPos targetPos, Camera camera) {
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

        int clampedTargetX = Math.max(boundPos.getX() - MAX_AXIS_LENGTH + 1, Math.min(targetPos.getX(), boundPos.getX() + MAX_AXIS_LENGTH - 1));
        int clampedTargetY = Math.max(boundPos.getY() - MAX_AXIS_LENGTH + 1, Math.min(targetPos.getY(), boundPos.getY() + MAX_AXIS_LENGTH - 1));
        int clampedTargetZ = Math.max(boundPos.getZ() - MAX_AXIS_LENGTH + 1, Math.min(targetPos.getZ(), boundPos.getZ() + MAX_AXIS_LENGTH - 1));

        int minX = Math.min(boundPos.getX(), clampedTargetX);
        int maxX = Math.max(boundPos.getX(), clampedTargetX);
        int minY = Math.min(boundPos.getY(), clampedTargetY);
        int maxY = Math.max(boundPos.getY(), clampedTargetY);
        int minZ = Math.min(boundPos.getZ(), clampedTargetZ);
        int maxZ = Math.max(boundPos.getZ(), clampedTargetZ);

        int sizeX = maxX - minX + 1;
        int sizeY = maxY - minY + 1;
        int sizeZ = maxZ - minZ + 1;

        VoxelShape box = VoxelShapes.cuboid(0, 0, 0, sizeX, sizeY, sizeZ);

        //? if <1.21.2 {
        RenderSystem.lineWidth(3.0f);
        WorldRenderer.drawShapeOutline(
                matrices,
                consumers.getBuffer(RenderLayer.getLines()),
                box,
                minX - camPos.x,
                minY - camPos.y,
                minZ - camPos.z,
                1.0f, 0.65f, 0.0f, 1.0f,
                true
        );
        RenderSystem.lineWidth(1.0f);
        //?} elif <1.21.11 {
        /*RenderSystem.lineWidth(3.0f);
        DebugRenderer.drawVoxelShapeOutlines(
                matrices,
                consumers.getBuffer(RenderLayer.getLines()),
                box,
                minX - camPos.x,
                minY - camPos.y,
                minZ - camPos.z,
                1.0f, 0.65f, 0.0f, 1.0f,
                true
        );
        RenderSystem.lineWidth(1.0f);
        *///?} else {
        /*VertexRendering.drawOutline(
                matrices,
                consumers.getBuffer(RenderLayers.LINES),
                box,
                minX - camPos.x,
                minY - camPos.y,
                minZ - camPos.z,
                0xFFFFA600,
                3.0f
        );
        *///?}
    }
}
