package net.cozystudios.tokimistoolshed;

import net.cozystudios.tokimistoolshed.compat.CopperCompat;
import net.cozystudios.tokimistoolshed.compat.ObsidianCompat;
import net.cozystudios.tokimistoolshed.item.ExcavatorItem;
import net.cozystudios.tokimistoolshed.item.HammerItem;
import net.cozystudios.tokimistoolshed.item.ClippersItem;
import net.cozystudios.tokimistoolshed.item.LumberAxeItem;
import net.cozystudios.tokimistoolshed.item.ScytheItem;
import net.cozystudios.tokimistoolshed.registry.CopperBucketBehaviors;
import net.cozystudios.tokimistoolshed.registry.ModItems;
import net.cozystudios.tokimistoolshed.util.LastBreakData;
import net.cozystudios.tokimistoolshed.util.LeafDecayScheduler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokimisToolshed implements ModInitializer {
	public static final String MOD_ID = "tokimistoolshed";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("TokimisToolshed Initialized!");

		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			LastBreakData.setFace(player, direction);
			return InteractionResult.PASS;
		});

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if (!player.isCreative()) return;
			ItemStack stack = player.getMainHandItem();
			if (stack.getItem() instanceof HammerItem || stack.getItem() instanceof ExcavatorItem || stack.getItem() instanceof ScytheItem || stack.getItem() instanceof LumberAxeItem || stack.getItem() instanceof ClippersItem) {
				stack.getItem().mineBlock(stack, world, state, pos, player);
			}
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> LeafDecayScheduler.onServerTick());

		ModItems.registerItems();
		CopperBucketBehaviors.register();

		CopperCompat.register();
		LOGGER.info("Registering copper excavator and hammer.");

		if (FabricLoader.getInstance().isModLoaded("obsidianequipmentrework")) {
			ObsidianCompat.register();
			LOGGER.info("Obsidian Equipment Reworked detected! Registering obsidian excavator and hammer.");
		}
	}

	public static final CreativeModeTab ITEM_GROUP = Registry.register(
			BuiltInRegistries.CREATIVE_MODE_TAB,
			Identifier.fromNamespaceAndPath(MOD_ID, "tab"),
			FabricCreativeModeTab.builder()
					.icon(() -> new ItemStack(ModItems.IRON_EXCAVATOR))
					.title(Component.translatable("itemGroup.tokimistoolshed.tab"))
					.displayItems((displayContext, entries) -> {
						entries.accept(ModItems.WOODEN_EXCAVATOR);
						entries.accept(ModItems.STONE_EXCAVATOR);
						entries.accept(CopperCompat.COPPER_EXCAVATOR);
						entries.accept(ModItems.IRON_EXCAVATOR);
						entries.accept(ModItems.GOLDEN_EXCAVATOR);
						entries.accept(ModItems.DIAMOND_EXCAVATOR);
						if (ObsidianCompat.OBSIDIAN_EXCAVATOR != null) {
							entries.accept(ObsidianCompat.OBSIDIAN_EXCAVATOR);
						}
						entries.accept(ModItems.NETHERITE_EXCAVATOR);
						entries.accept(ModItems.WOODEN_HAMMER);
						entries.accept(ModItems.STONE_HAMMER);
						entries.accept(CopperCompat.COPPER_HAMMER);
						entries.accept(ModItems.IRON_HAMMER);
						entries.accept(ModItems.GOLDEN_HAMMER);
						entries.accept(ModItems.DIAMOND_HAMMER);
						if (ObsidianCompat.OBSIDIAN_HAMMER != null) {
							entries.accept(ObsidianCompat.OBSIDIAN_HAMMER);
						}
						entries.accept(ModItems.NETHERITE_HAMMER);
						entries.accept(ModItems.WOODEN_SCYTHE);
						entries.accept(ModItems.STONE_SCYTHE);
						entries.accept(CopperCompat.COPPER_SCYTHE);
						entries.accept(ModItems.IRON_SCYTHE);
						entries.accept(ModItems.GOLDEN_SCYTHE);
						entries.accept(ModItems.DIAMOND_SCYTHE);
						if (ObsidianCompat.OBSIDIAN_SCYTHE != null) {
							entries.accept(ObsidianCompat.OBSIDIAN_SCYTHE);
						}
						entries.accept(ModItems.NETHERITE_SCYTHE);
						entries.accept(ModItems.WOODEN_LUMBER_AXE);
						entries.accept(ModItems.STONE_LUMBER_AXE);
						entries.accept(CopperCompat.COPPER_LUMBER_AXE);
						entries.accept(ModItems.IRON_LUMBER_AXE);
						entries.accept(ModItems.GOLDEN_LUMBER_AXE);
						entries.accept(ModItems.DIAMOND_LUMBER_AXE);
						if (ObsidianCompat.OBSIDIAN_LUMBER_AXE != null) {
							entries.accept(ObsidianCompat.OBSIDIAN_LUMBER_AXE);
						}
						entries.accept(ModItems.NETHERITE_LUMBER_AXE);
						entries.accept(ModItems.CLIPPERS);
						entries.accept(ModItems.COPPER_BUCKET);
						entries.accept(ModItems.COPPER_WATER_BUCKET);
						entries.accept(ModItems.COPPER_LAVA_BUCKET);
						entries.accept(ModItems.COPPER_POWDER_SNOW_BUCKET);
						entries.accept(ModItems.COPPER_MILK_BUCKET);
						entries.accept(ModItems.TROWEL);
						entries.accept(ModItems.ABACUS);
					})
					.build()
	);
}
