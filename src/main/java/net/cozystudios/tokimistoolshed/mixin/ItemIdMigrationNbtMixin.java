package net.cozystudios.tokimistoolshed.mixin;

//? if >=1.21.5 {
/*import net.cozystudios.tokimistoolshed.TokimisToolshed;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// For 1.21.11+ where ItemStack.fromNbt no longer exists.
// Intercepts NbtCompound.getString to remap old item IDs before
// the Codec-based ItemStack deserializer reads them.
@Mixin(NbtCompound.class)
public abstract class ItemIdMigrationNbtMixin {

    private static final String OLD_NAMESPACE = "excavatorsandhammers:";
    private static final String NEW_NAMESPACE = "tokimistoolshed:";

    @Inject(method = "getString", at = @At("RETURN"), cancellable = true)
    private void tokimistoolshed$remapOldIds(String key, CallbackInfoReturnable<String> cir) {
        if (!"id".equals(key)) return;

        String value = cir.getReturnValue();
        if (value == null || !value.startsWith(OLD_NAMESPACE)) return;

        String newId = NEW_NAMESPACE + value.substring(OLD_NAMESPACE.length());
        cir.setReturnValue(newId);

        TokimisToolshed.LOGGER.info("[TokimisToolshed] Migrated item {} -> {}", value, newId);
    }
}
*///?} else {
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.nbt.NbtCompound;

@Mixin(NbtCompound.class)
public abstract class ItemIdMigrationNbtMixin {
}
//?}
