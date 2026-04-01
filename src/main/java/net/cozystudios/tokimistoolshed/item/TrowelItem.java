package net.cozystudios.tokimistoolshed.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrowelItem extends Item {

    public TrowelItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player == null) return ActionResult.PASS;

        World world = context.getWorld();

        boolean hasBlocks = false;
        for (int i = 0; i < 9; i++) {
            ItemStack hotbarStack = player.getInventory().getStack(i);
            if (!hotbarStack.isEmpty() && hotbarStack.getItem() instanceof BlockItem) {
                hasBlocks = true;
                break;
            }
        }
        if (!hasBlocks) return ActionResult.PASS;

        //? if <1.21.11 {
        if (world.isClient) return ActionResult.SUCCESS;
        //?} else {
        /*if (world.isClient()) return ActionResult.SUCCESS;
        *///?}

        List<Integer> blockSlots = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ItemStack hotbarStack = player.getInventory().getStack(i);
            if (!hotbarStack.isEmpty() && hotbarStack.getItem() instanceof BlockItem) {
                blockSlots.add(i);
            }
        }

        Collections.shuffle(blockSlots);

        BlockHitResult hitResult = new BlockHitResult(
                context.getHitPos(), context.getSide(), context.getBlockPos(), false
        );

        for (int slot : blockSlots) {
            ItemStack blockStack = player.getInventory().getStack(slot);
            BlockItem blockItem = (BlockItem) blockStack.getItem();

            ItemPlacementContext placementContext = new ItemPlacementContext(
                    world, player, context.getHand(), blockStack, hitResult
            );

            ActionResult result = blockItem.place(placementContext);
            if (result.isAccepted()) {
                BlockPos placedPos = placementContext.getBlockPos();
                BlockSoundGroup soundGroup = world.getBlockState(placedPos).getSoundGroup();
                world.playSound(null, placedPos,
                        soundGroup.getPlaceSound(), SoundCategory.BLOCKS,
                        (soundGroup.getVolume() + 1.0F) / 2.0F,
                        soundGroup.getPitch() * 0.8F);
                return result;
            }
        }

        return ActionResult.PASS;
    }
}
