package net.cozystudios.tokimistoolshed.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;

public class CopperBucketBehaviors {

    public static void register() {
        registerCauldronBehaviors();
        registerDispenserBehaviors();
    }

    private static void registerCauldronBehaviors() {
        CauldronInteractions.EMPTY.put(ModItems.COPPER_WATER_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY));

        CauldronInteractions.EMPTY.put(ModItems.COPPER_LAVA_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.LAVA_CAULDRON.defaultBlockState(),
                        SoundEvents.BUCKET_EMPTY_LAVA));

        CauldronInteractions.EMPTY.put(ModItems.COPPER_POWDER_SNOW_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY_POWDER_SNOW));

        CauldronInteractions.WATER.put(ModItems.COPPER_BUCKET,
                (state, world, pos, player, hand, stack) -> {
                    int level = state.getValue(LayeredCauldronBlock.LEVEL);
                    if (level == 3) {
                        return emptyCauldron(world, pos, player, hand, stack,
                                ModItems.COPPER_WATER_BUCKET, SoundEvents.BUCKET_FILL);
                    }
                    return InteractionResult.PASS;
                });

        CauldronInteractions.LAVA.put(ModItems.COPPER_BUCKET,
                (state, world, pos, player, hand, stack) ->
                        emptyCauldron(world, pos, player, hand, stack,
                                ModItems.COPPER_LAVA_BUCKET, SoundEvents.BUCKET_FILL_LAVA));

        CauldronInteractions.POWDER_SNOW.put(ModItems.COPPER_BUCKET,
                (state, world, pos, player, hand, stack) -> {
                    int level = state.getValue(LayeredCauldronBlock.LEVEL);
                    if (level == 3) {
                        return emptyCauldron(world, pos, player, hand, stack,
                                ModItems.COPPER_POWDER_SNOW_BUCKET, SoundEvents.BUCKET_FILL_POWDER_SNOW);
                    }
                    return InteractionResult.PASS;
                });

        CauldronInteractions.WATER.put(ModItems.COPPER_WATER_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY));

        CauldronInteractions.WATER.put(ModItems.COPPER_LAVA_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.LAVA_CAULDRON.defaultBlockState(),
                        SoundEvents.BUCKET_EMPTY_LAVA));

        CauldronInteractions.POWDER_SNOW.put(ModItems.COPPER_POWDER_SNOW_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY_POWDER_SNOW));

        CauldronInteractions.POWDER_SNOW.put(ModItems.COPPER_LAVA_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.LAVA_CAULDRON.defaultBlockState(),
                        SoundEvents.BUCKET_EMPTY_LAVA));

        CauldronInteractions.LAVA.put(ModItems.COPPER_WATER_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY));

        CauldronInteractions.LAVA.put(ModItems.COPPER_POWDER_SNOW_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY_POWDER_SNOW));
    }

    private static InteractionResult fillCauldron(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack,
                                             BlockState cauldronState, SoundEvent sound) {
        if (!isWorldClient(world)) {
            player.setItemInHand(hand, damageBucketAndSwap(stack, player, world, ModItems.COPPER_BUCKET));
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            world.setBlockAndUpdate(pos, cauldronState);
            world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult emptyCauldron(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack,
                                              Item filledBucket, SoundEvent sound) {
        if (!isWorldClient(world)) {
            ItemStack filledStack = new ItemStack(filledBucket);
            filledStack.setDamageValue(stack.getDamageValue());
            player.setItemInHand(hand, filledStack);
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            world.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
            world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return InteractionResult.SUCCESS;
    }

    private static boolean isWorldClient(Level world) {
        return world.isClientSide();
    }

    private static ItemStack damageBucketAndSwap(ItemStack original, Player player, Level world, Item targetBucket) {
        if (player.getAbilities().instabuild) {
            return original;
        }
        int newDamage = original.getDamageValue() + 1;
        if (newDamage >= original.getMaxDamage()) {
            if (!isWorldClient(world)) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            return ItemStack.EMPTY;
        }
        ItemStack result = new ItemStack(targetBucket);
        result.setDamageValue(newDamage);
        return result;
    }

    private static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(ModItems.COPPER_WATER_BUCKET, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource pointer, ItemStack stack) {
                ServerLevel world = pointer.level();
                BlockPos pos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
                BlockState targetState = world.getBlockState(pos);
                if (targetState.canBeReplaced() || targetState.is(Blocks.WATER)) {
                    world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
                    world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return dispenseDamagedBucket(stack);
                }
                return super.execute(pointer, stack);
            }
        });

        DispenserBlock.registerBehavior(ModItems.COPPER_LAVA_BUCKET, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource pointer, ItemStack stack) {
                ServerLevel world = pointer.level();
                BlockPos pos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
                BlockState targetState = world.getBlockState(pos);
                if (targetState.canBeReplaced() || targetState.is(Blocks.LAVA)) {
                    world.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
                    world.playSound(null, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return dispenseDamagedBucket(stack);
                }
                return super.execute(pointer, stack);
            }
        });

        DispenserBlock.registerBehavior(ModItems.COPPER_POWDER_SNOW_BUCKET, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource pointer, ItemStack stack) {
                ServerLevel world = pointer.level();
                BlockPos pos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
                BlockState targetState = world.getBlockState(pos);
                if (targetState.canBeReplaced()) {
                    world.setBlockAndUpdate(pos, Blocks.POWDER_SNOW.defaultBlockState());
                    world.playSound(null, pos, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return dispenseDamagedBucket(stack);
                }
                return super.execute(pointer, stack);
            }
        });

        DispenserBlock.registerBehavior(ModItems.COPPER_BUCKET, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource pointer, ItemStack stack) {
                ServerLevel world = pointer.level();
                BlockPos pos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
                BlockState targetState = world.getBlockState(pos);

                Item filledBucket = null;
                SoundEvent sound = null;
                if (targetState.getFluidState().is(Fluids.WATER) && targetState.getFluidState().isSource()) {
                    filledBucket = ModItems.COPPER_WATER_BUCKET;
                    sound = SoundEvents.BUCKET_FILL;
                } else if (targetState.getFluidState().is(Fluids.LAVA) && targetState.getFluidState().isSource()) {
                    filledBucket = ModItems.COPPER_LAVA_BUCKET;
                    sound = SoundEvents.BUCKET_FILL_LAVA;
                } else if (targetState.is(Blocks.POWDER_SNOW)) {
                    filledBucket = ModItems.COPPER_POWDER_SNOW_BUCKET;
                    sound = SoundEvents.BUCKET_FILL_POWDER_SNOW;
                }

                if (filledBucket != null) {
                    world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
                    ItemStack filledStack = new ItemStack(filledBucket);
                    filledStack.setDamageValue(stack.getDamageValue());
                    return filledStack;
                }

                return super.execute(pointer, stack);
            }
        });
    }

    private static ItemStack dispenseDamagedBucket(ItemStack original) {
        int newDamage = original.getDamageValue() + 1;
        if (newDamage >= original.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        ItemStack result = new ItemStack(ModItems.COPPER_BUCKET);
        result.setDamageValue(newDamage);
        return result;
    }
}
