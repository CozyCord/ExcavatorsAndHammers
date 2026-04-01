package net.cozystudios.tokimistoolshed.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
//? if <1.21.2 {
import net.minecraft.util.TypedActionResult;
//?} else {
/*import net.minecraft.util.ActionResult;
*///?}
//? if >=1.21 {
/*import net.minecraft.item.tooltip.TooltipType;
*///?}
//? if >=1.21.5 {
/*import net.minecraft.component.type.TooltipDisplayComponent;
import java.util.function.Consumer;
import net.minecraft.text.Text;
*///?}

import java.util.function.Supplier;

public class CopperPowderSnowBucketItem extends Item {
    private final Supplier<Item> emptyBucket;

    public CopperPowderSnowBucketItem(Supplier<Item> emptyBucket, Settings settings) {
        super(settings);
        this.emptyBucket = emptyBucket;
    }

    private ItemStack handleUse(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BlockHitResult hitResult = Item.raycast(world, user, RaycastContext.FluidHandling.NONE);

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return null;
        }

        BlockPos pos = hitResult.getBlockPos();
        Direction direction = hitResult.getSide();
        BlockPos placePos = pos.offset(direction);

        //? if <1.21.5 {
        if (!world.canPlayerModifyAt(user, placePos) || !user.canPlaceOn(placePos, direction, stack)) {
        //?} else {
        /*if (!world.canEntityModifyAt(user, placePos) || !user.canPlaceOn(placePos, direction, stack)) {
        *///?}
            return null;
        }

        BlockState targetState = world.getBlockState(placePos);
        if (!targetState.isReplaceable()) {
            return null;
        }

        world.setBlockState(placePos, Blocks.POWDER_SNOW.getDefaultState());
        world.emitGameEvent(user, GameEvent.BLOCK_PLACE, placePos);
        world.playSound(null, placePos, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, SoundCategory.BLOCKS, 1.0F, 1.0F);
        user.incrementStat(Stats.USED.getOrCreateStat(this));

        return getEmptiedCopperStack(stack, user, world);
    }

    private ItemStack getEmptiedCopperStack(ItemStack stack, PlayerEntity player, World world) {
        if (player.getAbilities().creativeMode) {
            return stack;
        }
        int newDamage = stack.getDamage() + 1;
        if (newDamage >= stack.getMaxDamage()) {
            //? if <1.21.11 {
            if (!world.isClient) {
            //?} else {
            /*if (!world.isClient()) {
            *///?}
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return ItemStack.EMPTY;
        }
        ItemStack result = new ItemStack(emptyBucket.get());
        result.setDamage(newDamage);
        return result;
    }

    //? if <1.21.2 {
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack result = handleUse(world, user, hand);
        if (result == null) return TypedActionResult.pass(user.getStackInHand(hand));
        return TypedActionResult.success(result, world.isClient);
    }
    //?} else {
    /*@Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack result = handleUse(world, user, hand);
        if (result == null) return ActionResult.PASS;
        return ActionResult.SUCCESS.withNewHandStack(result);
    }
    *///?}

    //? if <1.21 {
    @Override
    public void appendTooltip(ItemStack stack, World world, java.util.List<net.minecraft.text.Text> tooltip, net.minecraft.client.item.TooltipContext context) {
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Weathers with use"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77\u00a7c" + (stack.getMaxDamage() - stack.getDamage()) + " \u00a77uses before breaking"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    //?} elif <1.21.5 {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, java.util.List<net.minecraft.text.Text> tooltip, TooltipType type) {
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Weathers with use"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77\u00a7c" + (stack.getMaxDamage() - stack.getDamage()) + " \u00a77uses before breaking"));
        super.appendTooltip(stack, context, tooltip, type);
    }
    *///?} else {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.literal("\u00a77Weathers with use"));
        textConsumer.accept(Text.literal("\u00a77\u00a7c" + (stack.getMaxDamage() - stack.getDamage()) + " \u00a77uses before breaking"));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
    *///?}
}
