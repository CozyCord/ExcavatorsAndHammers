package net.cozystudios.tokimistoolshed.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
//? if <1.21 {
import net.minecraft.client.item.TooltipContext;
//?} else {
/*import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.tooltip.TooltipType;
*///?}
//? if >=1.21.5 {
/*import net.minecraft.component.type.TooltipDisplayComponent;
import java.util.function.Consumer;
*///?}

import java.util.List;

public class AbacusItem extends Item {

    private static final String TAG_BOUND_X = "boundPosX";
    private static final String TAG_BOUND_Y = "boundPosY";
    private static final String TAG_BOUND_Z = "boundPosZ";
    private static final int UNBOUND_SENTINEL = -999;

    public AbacusItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResult.PASS;

        ItemStack stack = context.getStack();
        World world = context.getWorld();

        if (isBound(stack)) {
            unbind(stack);
            //? if <1.21.11 {
            if (!world.isClient) {
            //?} else {
            /*if (!world.isClient()) {
            *///?}
                player.sendMessage(
                        Text.literal("Abacus unbound").formatted(Formatting.GOLD),
                        true
                );
            }
            return ActionResult.SUCCESS;
        } else {
            BlockPos pos = context.getBlockPos();
            bind(stack, pos);
            //? if <1.21.11 {
            if (!world.isClient) {
            //?} else {
            /*if (!world.isClient()) {
            *///?}
                player.sendMessage(
                        Text.literal("Abacus bound to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ())
                                .formatted(Formatting.GOLD),
                        true
                );
            }
            return ActionResult.SUCCESS;
        }
    }

    public static boolean isBound(ItemStack stack) {
        //? if <1.21 {
        if (!stack.hasNbt()) return false;
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(TAG_BOUND_Y)) return false;
        return nbt.getInt(TAG_BOUND_Y) != UNBOUND_SENTINEL;
        //?} elif <1.21.5 {
        /*NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (component == null) return false;
        NbtCompound nbt = component.copyNbt();
        if (!nbt.contains(TAG_BOUND_Y)) return false;
        return nbt.getInt(TAG_BOUND_Y) != UNBOUND_SENTINEL;
        *///?} else {
        /*NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (component == null) return false;
        NbtCompound nbt = component.copyNbt();
        java.util.Optional<Integer> boundY = nbt.getInt(TAG_BOUND_Y);
        if (boundY.isEmpty()) return false;
        return boundY.get() != UNBOUND_SENTINEL;
        *///?}
    }

    @Nullable
    public static BlockPos getBoundPos(ItemStack stack) {
        if (!isBound(stack)) return null;
        //? if <1.21 {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return null;
        return new BlockPos(
                nbt.getInt(TAG_BOUND_X),
                nbt.getInt(TAG_BOUND_Y),
                nbt.getInt(TAG_BOUND_Z)
        );
        //?} elif <1.21.5 {
        /*NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (component == null) return null;
        NbtCompound nbt = component.copyNbt();
        return new BlockPos(
                nbt.getInt(TAG_BOUND_X),
                nbt.getInt(TAG_BOUND_Y),
                nbt.getInt(TAG_BOUND_Z)
        );
        *///?} else {
        /*NbtComponent component = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (component == null) return null;
        NbtCompound nbt = component.copyNbt();
        return new BlockPos(
                nbt.getInt(TAG_BOUND_X).orElse(0),
                nbt.getInt(TAG_BOUND_Y).orElse(0),
                nbt.getInt(TAG_BOUND_Z).orElse(0)
        );
        *///?}
    }

    public static void bind(ItemStack stack, BlockPos pos) {
        //? if <1.21 {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(TAG_BOUND_X, pos.getX());
        nbt.putInt(TAG_BOUND_Y, pos.getY());
        nbt.putInt(TAG_BOUND_Z, pos.getZ());
        //?} else {
        /*NbtCompound nbt = new NbtCompound();
        NbtComponent existing = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (existing != null) {
            nbt = existing.copyNbt();
        }
        nbt.putInt(TAG_BOUND_X, pos.getX());
        nbt.putInt(TAG_BOUND_Y, pos.getY());
        nbt.putInt(TAG_BOUND_Z, pos.getZ());
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        *///?}
    }

    public static void unbind(ItemStack stack) {
        //? if <1.21 {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(TAG_BOUND_Y, UNBOUND_SENTINEL);
        //?} else {
        /*NbtCompound nbt = new NbtCompound();
        NbtComponent existing = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (existing != null) {
            nbt = existing.copyNbt();
        }
        nbt.putInt(TAG_BOUND_Y, UNBOUND_SENTINEL);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
        *///?}
    }

    public static int getDistance(ItemStack stack, @Nullable BlockPos target) {
        BlockPos bound = getBoundPos(stack);
        if (bound == null || target == null) return -1;
        return bound.getManhattanDistance(target);
    }

    //? if <1.21 {
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        addTooltipLines(stack, tooltip::add);
        super.appendTooltip(stack, world, tooltip, context);
    }
    //?} elif <1.21.5 {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        addTooltipLines(stack, tooltip::add);
        super.appendTooltip(stack, context, tooltip, type);
    }
    *///?} else {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        addTooltipLines(stack, textConsumer);
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
    *///?}

    private void addTooltipLines(ItemStack stack, java.util.function.Consumer<Text> adder) {
        if (isBound(stack)) {
            BlockPos pos = getBoundPos(stack);
            if (pos != null) {
                adder.accept(Text.literal("Bound to: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ())
                        .formatted(Formatting.GOLD));
            }
        } else {
            adder.accept(Text.literal("Right-click a block to bind").formatted(Formatting.GRAY));
        }
        adder.accept(Text.literal("Right-click again to unbind").formatted(Formatting.DARK_GRAY));
    }
}
