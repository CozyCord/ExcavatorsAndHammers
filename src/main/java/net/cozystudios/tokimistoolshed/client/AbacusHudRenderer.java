package net.cozystudios.tokimistoolshed.client;

import net.cozystudios.tokimistoolshed.TokimisToolshed;
import net.cozystudios.tokimistoolshed.item.AbacusItem;
import net.cozystudios.tokimistoolshed.registry.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class AbacusHudRenderer {

    private static final int MAX_AXIS_LENGTH = 128;

    public static void register() {
        HudElementRegistry.addLast(
                Identifier.fromNamespaceAndPath(TokimisToolshed.MOD_ID, "abacus_hud"),
                (HudElement) (drawContext, deltaTracker) -> renderHud(drawContext)
        );
    }

    private static void renderHud(GuiGraphicsExtractor drawContext) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.level == null) return;
        if (client.options.hideGui) return;

        ItemStack abacusStack = getHeldAbacus(client);
        if (abacusStack == null || !AbacusItem.isBound(abacusStack)) return;

        HitResult hit = client.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return;

        BlockPos targetPos = ((BlockHitResult) hit).getBlockPos();
        BlockPos boundPos = AbacusItem.getBoundPos(abacusStack);
        if (boundPos == null) return;

        int distance = AbacusItem.getDistance(abacusStack, targetPos);
        if (distance < 0) return;

        int dx = Math.min(Math.abs(targetPos.getX() - boundPos.getX()), MAX_AXIS_LENGTH - 1);
        int dy = Math.min(Math.abs(targetPos.getY() - boundPos.getY()), MAX_AXIS_LENGTH - 1);
        int dz = Math.min(Math.abs(targetPos.getZ() - boundPos.getZ()), MAX_AXIS_LENGTH - 1);

        int screenWidth = drawContext.guiWidth();
        int screenHeight = drawContext.guiHeight();

        int iconX = (screenWidth / 2) + 10;
        int iconY = (screenHeight / 2) - 7;

        drawContext.item(abacusStack, iconX, iconY);

        Font textRenderer = client.font;

        if (dx > 0) {
            String xText = String.valueOf(dx + 1);
            int xTextWidth = textRenderer.width(xText);
            drawContext.text(textRenderer, xText,
                    iconX + 8 - xTextWidth / 2, iconY - 10, 0xFFFFFFFF);
        }

        if (dz > 0) {
            String zText = String.valueOf(dz + 1);
            drawContext.text(textRenderer, zText,
                    iconX + 18, iconY + 4, 0xFFFFFFFF);
        }

        if (dy > 0) {
            String yText = String.valueOf(dy + 1);
            int yTextWidth = textRenderer.width(yText);
            drawContext.text(textRenderer, yText,
                    iconX + 8 - yTextWidth / 2, iconY + 18, 0xFFFFFFFF);
        }
    }

    private static ItemStack getHeldAbacus(Minecraft client) {
        ItemStack mainHand = client.player.getMainHandItem();
        if (mainHand.is(ModItems.ABACUS)) return mainHand;
        ItemStack offHand = client.player.getOffhandItem();
        if (offHand.is(ModItems.ABACUS)) return offHand;
        return null;
    }
}
