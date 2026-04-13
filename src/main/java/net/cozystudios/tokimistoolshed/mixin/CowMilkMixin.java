package net.cozystudios.tokimistoolshed.mixin;

import net.cozystudios.tokimistoolshed.item.CopperBucketItem;
import net.cozystudios.tokimistoolshed.registry.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.cow.AbstractCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCow.class)
public class CowMilkMixin {
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(hand);
        AbstractCow self = (AbstractCow) (Object) this;

        if (stack.getItem() instanceof CopperBucketItem copperBucket && self.isAlive() && !self.isBaby()) {
            if (copperBucket != ModItems.COPPER_BUCKET) return;

            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);

            ItemStack milkStack = new ItemStack(ModItems.COPPER_MILK_BUCKET);
            milkStack.setDamageValue(stack.getDamageValue());

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            if (stack.isEmpty()) {
                player.setItemInHand(hand, milkStack);
            } else {
                if (!player.getInventory().add(milkStack)) {
                    player.drop(milkStack, false);
                }
            }

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
