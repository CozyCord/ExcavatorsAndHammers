package net.cozystudios.tokimistoolshed.mixin;

//? if >=1.21.5 {
/*import net.cozystudios.tokimistoolshed.TokimisToolshed;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

// For 1.21.5+ where ItemStack.fromNbt no longer exists and NbtCompound.getString returns Optional.
// Intercepts NbtCompound.getString to remap old item IDs before
// the Codec-based ItemStack deserializer reads them.
@Mixin(NbtCompound.class)
public abstract class ItemIdMigrationNbtMixin {

    private static final String OLD_NAMESPACE = "excavatorsandhammers:";
    private static final String NEW_NAMESPACE = "tokimistoolshed:";

    @SuppressWarnings("unchecked")
    @Inject(method = "getString", at = @At("RETURN"), cancellable = true)
    private void tokimistoolshed$remapOldIds(String key, CallbackInfoReturnable<?> cir) {
        if (!"id".equals(key)) return;

        Object raw = cir.getReturnValue();
        if (!(raw instanceof Optional<?> opt)) return;

        opt.ifPresent(val -> {
            if (!(val instanceof String id)) return;

            String newId = null;
            if (id.startsWith(OLD_NAMESPACE)) {
                newId = NEW_NAMESPACE + id.substring(OLD_NAMESPACE.length());
            } else if (id.equals("tokimistoolshed:iron_clippers")
                    || id.equals("tokimistoolshed:wooden_clippers")
                    || id.equals("tokimistoolshed:copper_clippers")) {
                newId = "tokimistoolshed:clippers";
            }

            if (newId != null) {
                ((CallbackInfoReturnable<Object>) cir).setReturnValue(Optional.of(newId));
                TokimisToolshed.LOGGER.info("[TokimisToolshed] Migrated item {} -> {}", id, newId);
            }
        });
    }
}
*///?} else {
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.nbt.NbtCompound;

@Mixin(NbtCompound.class)
public abstract class ItemIdMigrationNbtMixin {
}
//?}
