package net.cozystudios.tokimistoolshed.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.cozystudios.tokimistoolshed.item.AbacusItem;
import net.cozystudios.tokimistoolshed.registry.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AbacusOutlineRenderer {

    private static final int MAX_AXIS_LENGTH = 128;

    public static void register() {
        LevelRenderEvents.AFTER_SOLID_FEATURES.register(AbacusOutlineRenderer::render);
    }

    private static void render(LevelRenderContext context) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.level == null) return;

        ItemStack abacusStack = getHeldAbacus(client);
        if (abacusStack == null || !AbacusItem.isBound(abacusStack)) return;

        if (client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) return;

        BlockPos targetPos = ((BlockHitResult) client.hitResult).getBlockPos();
        BlockPos boundPos = AbacusItem.getBoundPos(abacusStack);
        if (boundPos == null) return;

        int distance = AbacusItem.getDistance(abacusStack, targetPos);
        if (distance < 0) return;

        renderBoundingBox(context, boundPos, targetPos, client.gameRenderer.getMainCamera());
    }

    private static ItemStack getHeldAbacus(Minecraft client) {
        ItemStack mainHand = client.player.getMainHandItem();
        if (mainHand.is(ModItems.ABACUS)) return mainHand;
        ItemStack offHand = client.player.getOffhandItem();
        if (offHand.is(ModItems.ABACUS)) return offHand;
        return null;
    }

    private static void renderBoundingBox(LevelRenderContext context, BlockPos boundPos, BlockPos targetPos, Camera camera) {
        PoseStack matrices = context.poseStack();
        MultiBufferSource consumers = context.bufferSource();
        Vec3 camPos = camera.position();

        if (consumers == null) return;

        int clampedTargetX = Math.clamp(targetPos.getX(), boundPos.getX() - MAX_AXIS_LENGTH + 1, boundPos.getX() + MAX_AXIS_LENGTH - 1);
        int clampedTargetY = Math.clamp(targetPos.getY(), boundPos.getY() - MAX_AXIS_LENGTH + 1, boundPos.getY() + MAX_AXIS_LENGTH - 1);
        int clampedTargetZ = Math.clamp(targetPos.getZ(), boundPos.getZ() - MAX_AXIS_LENGTH + 1, boundPos.getZ() + MAX_AXIS_LENGTH - 1);

        int minX = Math.min(boundPos.getX(), clampedTargetX);
        int maxX = Math.max(boundPos.getX(), clampedTargetX);
        int minY = Math.min(boundPos.getY(), clampedTargetY);
        int maxY = Math.max(boundPos.getY(), clampedTargetY);
        int minZ = Math.min(boundPos.getZ(), clampedTargetZ);
        int maxZ = Math.max(boundPos.getZ(), clampedTargetZ);

        int sizeX = maxX - minX + 1;
        int sizeY = maxY - minY + 1;
        int sizeZ = maxZ - minZ + 1;

        VoxelShape box = Shapes.box(0, 0, 0, sizeX, sizeY, sizeZ);

        ShapeRenderer.renderShape(
                matrices,
                consumers.getBuffer(RenderTypes.LINES),
                box,
                minX - camPos.x,
                minY - camPos.y,
                minZ - camPos.z,
                0xFFFFA600,
                3.0f
        );

    }
}
