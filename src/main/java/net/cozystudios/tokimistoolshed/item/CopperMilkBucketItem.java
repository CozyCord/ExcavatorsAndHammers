package net.cozystudios.tokimistoolshed.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
//? if <1.21.2 {
import net.minecraft.util.UseAction;
//?} else {
/*import net.minecraft.item.consume.UseAction;
*///?}
import net.minecraft.world.World;
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

public class CopperMilkBucketItem extends Item {
    private final Supplier<Item> emptyBucket;

    public CopperMilkBucketItem(Supplier<Item> emptyBucket, Settings settings) {
        super(settings);
        this.emptyBucket = emptyBucket;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayer) {
            Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        //? if <1.21.11 {
        if (!world.isClient) {
        //?} else {
        /*if (!world.isClient()) {
        *///?}
            user.clearStatusEffects();
        }

        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
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

        return stack;
    }

    //? if <1.21 {
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }
    //?} else {
    /*@Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }
    *///?}

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    //? if <1.21.2 {
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
    //?} else {
    /*@Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
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
