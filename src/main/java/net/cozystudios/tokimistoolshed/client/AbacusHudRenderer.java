package net.cozystudios.tokimistoolshed.client;

import net.cozystudios.tokimistoolshed.item.AbacusItem;
import net.cozystudios.tokimistoolshed.registry.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class AbacusHudRenderer {

    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, unused) -> renderHud(drawContext));
    }

    private static void renderHud(DrawContext drawContext) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (client.options.hudHidden) return;

        ItemStack abacusStack = getHeldAbacus(client);
        if (abacusStack == null || !AbacusItem.isBound(abacusStack)) return;

        HitResult hit = client.crosshairTarget;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return;

        BlockPos targetPos = ((BlockHitResult) hit).getBlockPos();
        BlockPos boundPos = AbacusItem.getBoundPos(abacusStack);
        if (boundPos == null) return;

        int distance = AbacusItem.getDistance(abacusStack, targetPos);
        if (distance < 0) return;

        int dx = Math.abs(targetPos.getX() - boundPos.getX());
        int dy = Math.abs(targetPos.getY() - boundPos.getY());
        int dz = Math.abs(targetPos.getZ() - boundPos.getZ());

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int iconX = (screenWidth / 2) + 10;
        int iconY = (screenHeight / 2) - 7;

        drawContext.drawItem(abacusStack, iconX, iconY);

        TextRenderer textRenderer = client.textRenderer;

        if (dx > 0) {
            String xText = String.valueOf(dx + 1);
            int xTextWidth = textRenderer.getWidth(xText);
            drawContext.drawTextWithShadow(textRenderer, xText,
                    iconX + 8 - xTextWidth / 2, iconY - 10, 0xFFFFFFFF);
        }

        if (dz > 0) {
            String zText = String.valueOf(dz + 1);
            drawContext.drawTextWithShadow(textRenderer, zText,
                    iconX + 18, iconY + 4, 0xFFFFFFFF);
        }

        if (dy > 0) {
            String yText = String.valueOf(dy + 1);
            int yTextWidth = textRenderer.getWidth(yText);
            drawContext.drawTextWithShadow(textRenderer, yText,
                    iconX + 8 - yTextWidth / 2, iconY + 18, 0xFFFFFFFF);
        }
    }

    private static ItemStack getHeldAbacus(MinecraftClient client) {
        ItemStack mainHand = client.player.getMainHandStack();
        if (mainHand.isOf(ModItems.ABACUS)) return mainHand;
        ItemStack offHand = client.player.getOffHandStack();
        if (offHand.isOf(ModItems.ABACUS)) return offHand;
        return null;
    }
}
