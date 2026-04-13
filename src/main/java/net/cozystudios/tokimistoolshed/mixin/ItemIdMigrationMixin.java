package net.cozystudios.tokimistoolshed.mixin;

import net.cozystudios.tokimistoolshed.TokimisToolshed;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemIdMigrationMixin {

    @Unique
    private static final String OLD_NAMESPACE = "excavatorsandhammers:";
    @Unique
    private static final String NEW_NAMESPACE = "tokimistoolshed:";

}
