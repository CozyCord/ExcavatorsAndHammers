package net.cozystudios.tokimistoolshed.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Block.class)
public class ShearsLootMixin {
    @ModifyVariable(
            method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private static ItemStack redirectShearsStack(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ShearsItem && !stack.isOf(Items.SHEARS)) {
            return new ItemStack(Items.SHEARS);
        }
        return stack;
    }
}
