package net.cozystudios.tokimistoolshed.mixin;

import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Block.class)
public class ShearsLootMixin {
    @ModifyVariable(
            method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemInstance;)Ljava/util/List;",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private static ItemInstance redirectShearsStack(ItemInstance instance) {
        if (instance instanceof ItemStack stack && stack.getItem() instanceof ShearsItem && !stack.is(Items.SHEARS)) {
            return new ItemStack(Items.SHEARS);
        }
        return instance;
    }
}
