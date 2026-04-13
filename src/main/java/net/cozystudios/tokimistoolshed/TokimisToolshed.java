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

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.item.ItemStack;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
//? if >=1.21.2 {
/*import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
*///?}
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
			return ActionResult.PASS;
		});

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
			if (!player.isCreative()) return;
			ItemStack stack = player.getMainHandStack();
			if (stack.getItem() instanceof HammerItem || stack.getItem() instanceof ExcavatorItem || stack.getItem() instanceof ScytheItem || stack.getItem() instanceof LumberAxeItem || stack.getItem() instanceof ClippersItem) {
				stack.getItem().postMine(stack, world, state, pos, player);
			}
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> LeafDecayScheduler.onServerTick());

		ModItems.registerItems();
		CopperBucketBehaviors.register();

		//? if <1.21.9 {
		if (FabricLoader.getInstance().isModLoaded("copperagebackport")) {
			CopperCompat.register();
			LOGGER.info("Copper Age Backport detected! Registering copper excavator and hammer.");
		}
		//?} else {
		/*CopperCompat.register();
		LOGGER.info("Registering copper excavator and hammer.");
		*///?}

		if (FabricLoader.getInstance().isModLoaded("obsidianequipmentrework")) {
			ObsidianCompat.register();
			LOGGER.info("Obsidian Equipment Reworked detected! Registering obsidian excavator and hammer.");
		}
	}

	public static final ItemGroup ITEM_GROUP = Registry.register(
			Registries.ITEM_GROUP,
			//? if <1.21 {
			new Identifier(MOD_ID, "tab"),
			//?} else {
			/*Identifier.of(MOD_ID, "tab"),
			*///?}
			FabricItemGroup.builder()
					.icon(() -> new ItemStack(ModItems.IRON_EXCAVATOR))
					.displayName(Text.translatable("itemGroup.tokimistoolshed.tab"))
					.entries((displayContext, entries) -> {
						entries.add(ModItems.WOODEN_EXCAVATOR);
						entries.add(ModItems.STONE_EXCAVATOR);
						//? if <1.21.9 {
						if (CopperCompat.COPPER_EXCAVATOR != null) {
							entries.add(CopperCompat.COPPER_EXCAVATOR);
						}
						//?} else {
						/*entries.add(CopperCompat.COPPER_EXCAVATOR);
						*///?}
						entries.add(ModItems.IRON_EXCAVATOR);
						entries.add(ModItems.GOLDEN_EXCAVATOR);
						entries.add(ModItems.DIAMOND_EXCAVATOR);
						if (ObsidianCompat.OBSIDIAN_EXCAVATOR != null) {
							entries.add(ObsidianCompat.OBSIDIAN_EXCAVATOR);
						}
						entries.add(ModItems.NETHERITE_EXCAVATOR);
						entries.add(ModItems.WOODEN_HAMMER);
						entries.add(ModItems.STONE_HAMMER);
						//? if <1.21.9 {
						if (CopperCompat.COPPER_HAMMER != null) {
							entries.add(CopperCompat.COPPER_HAMMER);
						}
						//?} else {
						/*entries.add(CopperCompat.COPPER_HAMMER);
						*///?}
						entries.add(ModItems.IRON_HAMMER);
						entries.add(ModItems.GOLDEN_HAMMER);
						entries.add(ModItems.DIAMOND_HAMMER);
						if (ObsidianCompat.OBSIDIAN_HAMMER != null) {
							entries.add(ObsidianCompat.OBSIDIAN_HAMMER);
						}
						entries.add(ModItems.NETHERITE_HAMMER);
						entries.add(ModItems.WOODEN_SCYTHE);
						entries.add(ModItems.STONE_SCYTHE);
						//? if <1.21.9 {
						if (CopperCompat.COPPER_SCYTHE != null) {
							entries.add(CopperCompat.COPPER_SCYTHE);
						}
						//?} else {
						/*entries.add(CopperCompat.COPPER_SCYTHE);
						*///?}
						entries.add(ModItems.IRON_SCYTHE);
						entries.add(ModItems.GOLDEN_SCYTHE);
						entries.add(ModItems.DIAMOND_SCYTHE);
						if (ObsidianCompat.OBSIDIAN_SCYTHE != null) {
							entries.add(ObsidianCompat.OBSIDIAN_SCYTHE);
						}
						entries.add(ModItems.NETHERITE_SCYTHE);
						entries.add(ModItems.WOODEN_LUMBER_AXE);
						entries.add(ModItems.STONE_LUMBER_AXE);
						//? if <1.21.9 {
						if (CopperCompat.COPPER_LUMBER_AXE != null) {
							entries.add(CopperCompat.COPPER_LUMBER_AXE);
						}
						//?} else {
						/*entries.add(CopperCompat.COPPER_LUMBER_AXE);
						*///?}
						entries.add(ModItems.IRON_LUMBER_AXE);
						entries.add(ModItems.GOLDEN_LUMBER_AXE);
						entries.add(ModItems.DIAMOND_LUMBER_AXE);
						if (ObsidianCompat.OBSIDIAN_LUMBER_AXE != null) {
							entries.add(ObsidianCompat.OBSIDIAN_LUMBER_AXE);
						}
						entries.add(ModItems.NETHERITE_LUMBER_AXE);
						entries.add(ModItems.CLIPPERS);
						entries.add(ModItems.COPPER_BUCKET);
						entries.add(ModItems.COPPER_WATER_BUCKET);
						entries.add(ModItems.COPPER_LAVA_BUCKET);
						entries.add(ModItems.COPPER_POWDER_SNOW_BUCKET);
						entries.add(ModItems.COPPER_MILK_BUCKET);
						entries.add(ModItems.TROWEL);
						entries.add(ModItems.ABACUS);
					})
					.build()
	);
}
