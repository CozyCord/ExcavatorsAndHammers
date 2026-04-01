package net.cozystudios.tokimistoolshed.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
//? if >=1.21 {
/*import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipType;
*///?}
//? if >=1.21.5 {
/*import net.minecraft.component.type.TooltipDisplayComponent;
import java.util.function.Consumer;
import net.minecraft.text.Text;
*///?}

public class ScytheItem extends HoeItem {
    private static boolean isScything = false;

    //? if <1.21 {
    public ScytheItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, (int) attackDamage, attackSpeed, settings);
    }
    //?} elif <1.21.2 {
    /*public ScytheItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, settings.attributeModifiers(HoeItem.createAttributeModifiers(material, (int) attackDamage, attackSpeed)));
    }
    *///?} else {
    /*public ScytheItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    *///?}

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() == null || context.getPlayer().isSneaking()) {
            return super.useOnBlock(context);
        }

        ActionResult centerResult = super.useOnBlock(context);
        if (!centerResult.isAccepted()) {
            return centerResult;
        }

        BlockPos center = context.getBlockPos();
        World world = context.getWorld();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) continue;

                if (context.getStack().isEmpty()) return centerResult;

                BlockPos targetPos = center.add(dx, 0, dz);
                BlockHitResult hit = new BlockHitResult(
                        new Vec3d(targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5),
                        Direction.UP,
                        targetPos,
                        false
                );
                ItemUsageContext targetContext = new ItemUsageContext(
                        world, context.getPlayer(), context.getHand(),
                        context.getStack(), hit
                );
                super.useOnBlock(targetContext);
            }
        }

        return centerResult;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        boolean result = super.postMine(stack, world, state, pos, miner);

        //? if <1.21.11 {
        if (world.isClient || isScything) return result;
        //?} else {
        /*if (world.isClient() || isScything) return result;
        *///?}
        if (!(miner instanceof ServerPlayerEntity serverPlayer)) return result;
        boolean isCreative = serverPlayer.interactionManager.getGameMode().isCreative();
        boolean isSurvival = serverPlayer.interactionManager.getGameMode().isSurvivalLike();
        if (!state.isIn(BlockTags.HOE_MINEABLE) && !(state.getBlock() instanceof PlantBlock)) return result;
        if (serverPlayer.isSneaking()) return result;
        if (!isCreative && !isSurvival) return result;

        try {
            isScything = true;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dz == 0) continue;

                    BlockPos targetPos = pos.add(dx, 0, dz);
                    BlockState targetState = world.getBlockState(targetPos);

                    if (targetState.isAir()) continue;
                    if (!targetState.isIn(BlockTags.HOE_MINEABLE) && !(targetState.getBlock() instanceof PlantBlock)) continue;
                    if (targetState.getHardness(world, targetPos) < 0) continue;

                    serverPlayer.interactionManager.tryBreakBlock(targetPos);
                }
            }
        } finally {
            isScything = false;
        }

        return result;
    }

    //? if <1.21 {
    @Override
    public void appendTooltip(ItemStack stack, World world, java.util.List<net.minecraft.text.Text> tooltip, net.minecraft.client.item.TooltipContext context) {
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Tills a \u00a7a3\u00d73 \u00a77area"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Cuts vegetation in a \u00a7a3\u00d73 \u00a77area"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Hold \u00a7eShift \u00a77for single block"));
        super.appendTooltip(stack, world, tooltip, context);
    }
    //?} elif <1.21.5 {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, java.util.List<net.minecraft.text.Text> tooltip, TooltipType type) {
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Tills a \u00a7a3\u00d73 \u00a77area"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Cuts vegetation in a \u00a7a3\u00d73 \u00a77area"));
        tooltip.add(net.minecraft.text.Text.literal("\u00a77Hold \u00a7eShift \u00a77for single block"));
        super.appendTooltip(stack, context, tooltip, type);
    }
    *///?} else {
    /*@Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.literal("\u00a77Tills a \u00a7a3\u00d73 \u00a77area"));
        textConsumer.accept(Text.literal("\u00a77Cuts vegetation in a \u00a7a3\u00d73 \u00a77area"));
        textConsumer.accept(Text.literal("\u00a77Hold \u00a7eShift \u00a77for single block"));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
    *///?}
}
