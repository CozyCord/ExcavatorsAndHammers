package net.cozystudios.tokimistoolshed.registry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
//? if >=1.21 && <1.21.2 {
/*import net.minecraft.util.ItemActionResult;
*///?}
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

public class CopperBucketBehaviors {

    public static void register() {
        registerCauldronBehaviors();
        registerDispenserBehaviors();
    }

    //? if <1.21 {
    private static Map<Item, CauldronBehavior> cauldronMap(Map<Item, CauldronBehavior> map) { return map; }
    //?} else {
    /*private static Map<Item, CauldronBehavior> cauldronMap(CauldronBehavior.CauldronBehaviorMap map) { return map.map(); }
    *///?}

    private static void registerCauldronBehaviors() {
        cauldronMap(CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR).put(ModItems.COPPER_WATER_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
                        SoundEvents.ITEM_BUCKET_EMPTY));

        cauldronMap(CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR).put(ModItems.COPPER_LAVA_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.LAVA_CAULDRON.getDefaultState(),
                        SoundEvents.ITEM_BUCKET_EMPTY_LAVA));

        cauldronMap(CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR).put(ModItems.COPPER_POWDER_SNOW_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
                        SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW));

        cauldronMap(CauldronBehavior.WATER_CAULDRON_BEHAVIOR).put(ModItems.COPPER_BUCKET,
                (state, world, pos, player, hand, stack) -> {
                    int level = state.get(LeveledCauldronBlock.LEVEL);
                    if (level == 3) {
                        return emptyCauldron(world, pos, player, hand, stack,
                                ModItems.COPPER_WATER_BUCKET, SoundEvents.ITEM_BUCKET_FILL);
                    }
                    //? if >=1.21 && <1.21.2 {
                    /*return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                    *///?} else {
                    return ActionResult.PASS;
                    //?}
                });

        cauldronMap(CauldronBehavior.LAVA_CAULDRON_BEHAVIOR).put(ModItems.COPPER_BUCKET,
                (state, world, pos, player, hand, stack) ->
                        emptyCauldron(world, pos, player, hand, stack,
                                ModItems.COPPER_LAVA_BUCKET, SoundEvents.ITEM_BUCKET_FILL_LAVA));

        cauldronMap(CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR).put(ModItems.COPPER_BUCKET,
                (state, world, pos, player, hand, stack) -> {
                    int level = state.get(LeveledCauldronBlock.LEVEL);
                    if (level == 3) {
                        return emptyCauldron(world, pos, player, hand, stack,
                                ModItems.COPPER_POWDER_SNOW_BUCKET, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW);
                    }
                    //? if >=1.21 && <1.21.2 {
                    /*return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                    *///?} else {
                    return ActionResult.PASS;
                    //?}
                });

        cauldronMap(CauldronBehavior.WATER_CAULDRON_BEHAVIOR).put(ModItems.COPPER_WATER_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
                        SoundEvents.ITEM_BUCKET_EMPTY));

        cauldronMap(CauldronBehavior.WATER_CAULDRON_BEHAVIOR).put(ModItems.COPPER_LAVA_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.LAVA_CAULDRON.getDefaultState(),
                        SoundEvents.ITEM_BUCKET_EMPTY_LAVA));

        cauldronMap(CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR).put(ModItems.COPPER_POWDER_SNOW_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
                        SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW));

        cauldronMap(CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR).put(ModItems.COPPER_LAVA_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.LAVA_CAULDRON.getDefaultState(),
                        SoundEvents.ITEM_BUCKET_EMPTY_LAVA));

        cauldronMap(CauldronBehavior.LAVA_CAULDRON_BEHAVIOR).put(ModItems.COPPER_WATER_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.WATER_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
                        SoundEvents.ITEM_BUCKET_EMPTY));

        cauldronMap(CauldronBehavior.LAVA_CAULDRON_BEHAVIOR).put(ModItems.COPPER_POWDER_SNOW_BUCKET,
                (state, world, pos, player, hand, stack) -> fillCauldron(world, pos, player, hand, stack,
                        Blocks.POWDER_SNOW_CAULDRON.getDefaultState().with(LeveledCauldronBlock.LEVEL, 3),
                        SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW));
    }

    //? if <1.21 {
    private static ActionResult fillCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack,
                                             BlockState cauldronState, SoundEvent sound) {
        if (!world.isClient) {
            player.setStackInHand(hand, damageBucketAndSwap(stack, player, world, ModItems.COPPER_BUCKET));
            player.incrementStat(Stats.FILL_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            world.setBlockState(pos, cauldronState);
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return ActionResult.success(world.isClient);
    }

    private static ActionResult emptyCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack,
                                              Item filledBucket, SoundEvent sound) {
        if (!world.isClient) {
            ItemStack filledStack = new ItemStack(filledBucket);
            filledStack.setDamage(stack.getDamage());
            player.setStackInHand(hand, filledStack);
            player.incrementStat(Stats.USE_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return ActionResult.success(world.isClient);
    }
    //?} elif <1.21.2 {
    /*private static ItemActionResult fillCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack,
                                             BlockState cauldronState, SoundEvent sound) {
        if (!world.isClient) {
            player.setStackInHand(hand, damageBucketAndSwap(stack, player, world, ModItems.COPPER_BUCKET));
            player.incrementStat(Stats.FILL_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            world.setBlockState(pos, cauldronState);
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return ItemActionResult.success(world.isClient);
    }

    private static ItemActionResult emptyCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack,
                                              Item filledBucket, SoundEvent sound) {
        if (!world.isClient) {
            ItemStack filledStack = new ItemStack(filledBucket);
            filledStack.setDamage(stack.getDamage());
            player.setStackInHand(hand, filledStack);
            player.incrementStat(Stats.USE_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return ItemActionResult.success(world.isClient);
    }
    *///?} else {
    /*private static ActionResult fillCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack,
                                             BlockState cauldronState, SoundEvent sound) {
        if (!isWorldClient(world)) {
            player.setStackInHand(hand, damageBucketAndSwap(stack, player, world, ModItems.COPPER_BUCKET));
            player.incrementStat(Stats.FILL_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            world.setBlockState(pos, cauldronState);
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return ActionResult.SUCCESS;
    }

    private static ActionResult emptyCauldron(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack,
                                              Item filledBucket, SoundEvent sound) {
        if (!isWorldClient(world)) {
            ItemStack filledStack = new ItemStack(filledBucket);
            filledStack.setDamage(stack.getDamage());
            player.setStackInHand(hand, filledStack);
            player.incrementStat(Stats.USE_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        }
        return ActionResult.SUCCESS;
    }
    *///?}

    private static boolean isWorldClient(World world) {
        //? if <1.21.11 {
        return world.isClient;
        //?} else {
        /*return world.isClient();
        *///?}
    }

    private static ItemStack damageBucketAndSwap(ItemStack original, PlayerEntity player, World world, Item targetBucket) {
        if (player.getAbilities().creativeMode) {
            return original;
        }
        int newDamage = original.getDamage() + 1;
        if (newDamage >= original.getMaxDamage()) {
            if (!isWorldClient(world)) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return ItemStack.EMPTY;
        }
        ItemStack result = new ItemStack(targetBucket);
        result.setDamage(newDamage);
        return result;
    }

    private static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(ModItems.COPPER_WATER_BUCKET, new ItemDispenserBehavior() {
            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                //? if <1.21 {
                ServerWorld world = pointer.getWorld();
                BlockPos pos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                //?} else {
                /*ServerWorld world = pointer.world();
                BlockPos pos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                *///?}
                BlockState targetState = world.getBlockState(pos);
                if (targetState.isReplaceable() || targetState.isOf(Blocks.WATER)) {
                    world.setBlockState(pos, Blocks.WATER.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return dispenseDamagedBucket(stack);
                }
                return super.dispenseSilently(pointer, stack);
            }
        });

        DispenserBlock.registerBehavior(ModItems.COPPER_LAVA_BUCKET, new ItemDispenserBehavior() {
            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                //? if <1.21 {
                ServerWorld world = pointer.getWorld();
                BlockPos pos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                //?} else {
                /*ServerWorld world = pointer.world();
                BlockPos pos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                *///?}
                BlockState targetState = world.getBlockState(pos);
                if (targetState.isReplaceable() || targetState.isOf(Blocks.LAVA)) {
                    world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return dispenseDamagedBucket(stack);
                }
                return super.dispenseSilently(pointer, stack);
            }
        });

        DispenserBlock.registerBehavior(ModItems.COPPER_POWDER_SNOW_BUCKET, new ItemDispenserBehavior() {
            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                //? if <1.21 {
                ServerWorld world = pointer.getWorld();
                BlockPos pos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                //?} else {
                /*ServerWorld world = pointer.world();
                BlockPos pos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                *///?}
                BlockState targetState = world.getBlockState(pos);
                if (targetState.isReplaceable()) {
                    world.setBlockState(pos, Blocks.POWDER_SNOW.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return dispenseDamagedBucket(stack);
                }
                return super.dispenseSilently(pointer, stack);
            }
        });

        DispenserBlock.registerBehavior(ModItems.COPPER_BUCKET, new ItemDispenserBehavior() {
            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                //? if <1.21 {
                ServerWorld world = pointer.getWorld();
                BlockPos pos = pointer.getPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                //?} else {
                /*ServerWorld world = pointer.world();
                BlockPos pos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
                *///?}
                BlockState targetState = world.getBlockState(pos);

                Item filledBucket = null;
                SoundEvent sound = null;
                if (targetState.getFluidState().isOf(Fluids.WATER) && targetState.getFluidState().isStill()) {
                    filledBucket = ModItems.COPPER_WATER_BUCKET;
                    sound = SoundEvents.ITEM_BUCKET_FILL;
                } else if (targetState.getFluidState().isOf(Fluids.LAVA) && targetState.getFluidState().isStill()) {
                    filledBucket = ModItems.COPPER_LAVA_BUCKET;
                    sound = SoundEvents.ITEM_BUCKET_FILL_LAVA;
                } else if (targetState.isOf(Blocks.POWDER_SNOW)) {
                    filledBucket = ModItems.COPPER_POWDER_SNOW_BUCKET;
                    sound = SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW;
                }

                if (filledBucket != null) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    ItemStack filledStack = new ItemStack(filledBucket);
                    filledStack.setDamage(stack.getDamage());
                    return filledStack;
                }

                return super.dispenseSilently(pointer, stack);
            }
        });
    }

    private static ItemStack dispenseDamagedBucket(ItemStack original) {
        int newDamage = original.getDamage() + 1;
        if (newDamage >= original.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        ItemStack result = new ItemStack(ModItems.COPPER_BUCKET);
        result.setDamage(newDamage);
        return result;
    }
}
