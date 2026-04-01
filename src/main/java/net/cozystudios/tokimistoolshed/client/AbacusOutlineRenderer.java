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

        //? if <1.21.11 {
        RenderSystem.lineWidth(3.0f);
        //?}

        int minX = Math.min(boundPos.getX(), targetPos.getX());
        int maxX = Math.max(boundPos.getX(), targetPos.getX());
        int minY = Math.min(boundPos.getY(), targetPos.getY());
        int maxY = Math.max(boundPos.getY(), targetPos.getY());
        int minZ = Math.min(boundPos.getZ(), targetPos.getZ());
        int maxZ = Math.max(boundPos.getZ(), targetPos.getZ());

        VoxelShape fullCube = VoxelShapes.fullCube();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    //? if <1.21.2 {
                    WorldRenderer.drawShapeOutline(
                            matrices,
                            consumers.getBuffer(RenderLayer.getLines()),
                            fullCube,
                            x - camPos.x,
                            y - camPos.y,
                            z - camPos.z,
                            1.0f, 0.65f, 0.0f, 1.0f,
                            true
                    );
                    //?} elif <1.21.11 {
                    /*DebugRenderer.drawVoxelShapeOutlines(
                            matrices,
                            consumers.getBuffer(RenderLayer.getLines()),
                            fullCube,
                            x - camPos.x,
                            y - camPos.y,
                            z - camPos.z,
                            1.0f, 0.65f, 0.0f, 1.0f,
                            true
                    );
                    *///?} else {
                    /*VertexRendering.drawOutline(
                            matrices,
                            consumers.getBuffer(RenderLayers.LINES),
                            fullCube,
                            x - camPos.x,
                            y - camPos.y,
                            z - camPos.z,
                            0xFFFFA600,
                            3.0f
                    );
                    *///?}
                }
            }
        }

        //? if <1.21.11 {
        RenderSystem.lineWidth(1.0f);
        //?}
    }
}
