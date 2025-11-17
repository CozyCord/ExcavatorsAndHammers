package net.cozystudios.excavatorsandhammers;

import net.cozystudios.excavatorsandhammers.registry.ModItems;
import net.cozystudios.excavatorsandhammers.util.LastBreakData;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcavatorsAndHammers implements ModInitializer {
	public static final String MOD_ID = "excavatorsandhammers";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("ExcavatorsAndHammers Initialized!");

            AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
                LastBreakData.setFace(player, direction);
                return ActionResult.PASS;
            });

        ModItems.registerItems();
	}

    public static final ItemGroup ITEM_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(MOD_ID, "tab"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.IRON_EXCAVATOR))
                    .displayName(Text.translatable("itemGroup.excavatorsandhammers.tab"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.WOODEN_EXCAVATOR);
                        entries.add(ModItems.STONE_EXCAVATOR);
                        entries.add(ModItems.IRON_EXCAVATOR);
                        entries.add(ModItems.GOLDEN_EXCAVATOR);
                        entries.add(ModItems.DIAMOND_EXCAVATOR);
                        entries.add(ModItems.NETHERITE_EXCAVATOR);
                        entries.add(ModItems.WOODEN_HAMMER);
                        entries.add(ModItems.STONE_HAMMER);
                        entries.add(ModItems.IRON_HAMMER);
                        entries.add(ModItems.GOLDEN_HAMMER);
                        entries.add(ModItems.DIAMOND_HAMMER);
                        entries.add(ModItems.NETHERITE_HAMMER);
                    })
                    .build()
    );
}