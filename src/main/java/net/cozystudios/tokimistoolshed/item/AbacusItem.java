package net.cozystudios.tokimistoolshed.item;

import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import java.util.List;

public class AbacusItem extends Item {

    private static final String TAG_BOUND_X = "boundPosX";
    private static final String TAG_BOUND_Y = "boundPosY";
    private static final String TAG_BOUND_Z = "boundPosZ";
    private static final int UNBOUND_SENTINEL = -999;

    public AbacusItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        ItemStack stack = context.getItemInHand();
        Level world = context.getLevel();

        if (isBound(stack)) {
            unbind(stack);
            if (!world.isClientSide()) {
                player.sendOverlayMessage(
                        Component.literal("Abacus unbound").withStyle(ChatFormatting.GOLD)
                );
            }
            return InteractionResult.SUCCESS;
        } else {
            BlockPos pos = context.getClickedPos();
            bind(stack, pos);
            if (!world.isClientSide()) {
                player.sendOverlayMessage(
                        Component.literal("Abacus bound to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ())
                                .withStyle(ChatFormatting.GOLD)
                );
            }
            return InteractionResult.SUCCESS;
        }
    }

    public static boolean isBound(ItemStack stack) {
        CustomData component = stack.get(DataComponents.CUSTOM_DATA);
        if (component == null) return false;
        CompoundTag nbt = component.copyTag();
        java.util.Optional<Integer> boundY = nbt.getInt(TAG_BOUND_Y);
        if (boundY.isEmpty()) return false;
        return boundY.get() != UNBOUND_SENTINEL;
    }

    @Nullable
    public static BlockPos getBoundPos(ItemStack stack) {
        if (!isBound(stack)) return null;
        CustomData component = stack.get(DataComponents.CUSTOM_DATA);
        if (component == null) return null;
        CompoundTag nbt = component.copyTag();
        return new BlockPos(
                nbt.getInt(TAG_BOUND_X).orElse(0),
                nbt.getInt(TAG_BOUND_Y).orElse(0),
                nbt.getInt(TAG_BOUND_Z).orElse(0)
        );
    }

    public static void bind(ItemStack stack, BlockPos pos) {
        CompoundTag nbt = new CompoundTag();
        CustomData existing = stack.get(DataComponents.CUSTOM_DATA);
        if (existing != null) {
            nbt = existing.copyTag();
        }
        nbt.putInt(TAG_BOUND_X, pos.getX());
        nbt.putInt(TAG_BOUND_Y, pos.getY());
        nbt.putInt(TAG_BOUND_Z, pos.getZ());
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }

    public static void unbind(ItemStack stack) {
        CompoundTag nbt = new CompoundTag();
        CustomData existing = stack.get(DataComponents.CUSTOM_DATA);
        if (existing != null) {
            nbt = existing.copyTag();
        }
        nbt.putInt(TAG_BOUND_Y, UNBOUND_SENTINEL);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));
    }

    public static int getDistance(ItemStack stack, @Nullable BlockPos target) {
        BlockPos bound = getBoundPos(stack);
        if (bound == null || target == null) return -1;
        return bound.distManhattan(target);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        addTooltipLines(stack, textConsumer);
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
    }

    private void addTooltipLines(ItemStack stack, java.util.function.Consumer<Component> adder) {
        if (isBound(stack)) {
            BlockPos pos = getBoundPos(stack);
            if (pos != null) {
                adder.accept(Component.literal("Bound to: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ())
                        .withStyle(ChatFormatting.GOLD));
            }
        } else {
            adder.accept(Component.literal("Right-click a block to bind").withStyle(ChatFormatting.GRAY));
        }
        adder.accept(Component.literal("Right-click again to unbind").withStyle(ChatFormatting.DARK_GRAY));
    }
}
