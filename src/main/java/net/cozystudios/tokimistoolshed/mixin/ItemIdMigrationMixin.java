package net.cozystudios.tokimistoolshed.mixin;

import net.cozystudios.tokimistoolshed.TokimisToolshed;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
//? if >=1.21 && <1.21.5 {
/*import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import java.util.Optional;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 1.20.1: fromNbt(NbtCompound) -> ItemStack
// 1.21-1.21.4: fromNbt(WrapperLookup, NbtElement) -> Optional<ItemStack>
// 1.21.5+: fromNbt removed, NbtCompound API changed — handled by ItemIdMigrationNbtMixin
@Mixin(ItemStack.class)
public abstract class ItemIdMigrationMixin {

    @Unique
    private static final String OLD_NAMESPACE = "excavatorsandhammers:";
    @Unique
    private static final String NEW_NAMESPACE = "tokimistoolshed:";

    //? if <1.21 {
    @Inject(method = "fromNbt", at = @At("HEAD"))
    private static void tokimistoolshed$remapOldItems(NbtCompound nbt, CallbackInfoReturnable<ItemStack> cir) {
        tokimistoolshed$remapId(nbt);
    }
    //?} elif <1.21.5 {
    /*@Inject(method = "fromNbt", at = @At("HEAD"))
    private static void tokimistoolshed$remapOldItems(RegistryWrapper.WrapperLookup lookup, NbtElement nbt, CallbackInfoReturnable<Optional<ItemStack>> cir) {
        if (nbt instanceof NbtCompound compound) {
            tokimistoolshed$remapId(compound);
        }
    }
    *///?}

    //? if <1.21.5 {
    @Unique
    private static void tokimistoolshed$remapId(NbtCompound nbt) {
        if (!nbt.contains("id", 8)) return;

        String id = nbt.getString("id");
        if (!id.startsWith(OLD_NAMESPACE)) return;

        String newId = NEW_NAMESPACE + id.substring(OLD_NAMESPACE.length());
        nbt.putString("id", newId);

        TokimisToolshed.LOGGER.info("[TokimisToolshed] Migrated item {} -> {}", id, newId);
    }
    //?}
}
