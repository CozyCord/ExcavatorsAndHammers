package net.cozystudios.tokimistoolshed.mixin;

import net.cozystudios.tokimistoolshed.item.CopperBucketItem;
import net.cozystudios.tokimistoolshed.registry.ModItems;
//? if <1.21.5 {
import net.minecraft.entity.passive.CowEntity;
//?} else {
/*import net.minecraft.entity.passive.AbstractCowEntity;
*///?}
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if <1.21.5 {
@Mixin(CowEntity.class)
//?} else {
/*@Mixin(AbstractCowEntity.class)
*///?}
public class CowMilkMixin {
    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        //? if <1.21.5 {
        CowEntity self = (CowEntity) (Object) this;
        //?} else {
        /*AbstractCowEntity self = (AbstractCowEntity) (Object) this;
        *///?}

        if (stack.getItem() instanceof CopperBucketItem copperBucket && self.isAlive() && !self.isBaby()) {
            if (copperBucket != ModItems.COPPER_BUCKET) return;

            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);

            ItemStack milkStack = new ItemStack(ModItems.COPPER_MILK_BUCKET);
            milkStack.setDamage(stack.getDamage());

            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }

            if (stack.isEmpty()) {
                player.setStackInHand(hand, milkStack);
            } else {
                if (!player.getInventory().insertStack(milkStack)) {
                    player.dropItem(milkStack, false);
                }
            }

            //? if <1.21.2 {
            cir.setReturnValue(ActionResult.success(self.getWorld().isClient));
            //?} else {
            /*cir.setReturnValue(ActionResult.SUCCESS);
            *///?}
        }
    }
}
