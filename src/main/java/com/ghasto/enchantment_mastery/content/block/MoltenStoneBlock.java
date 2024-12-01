package com.ghasto.enchantment_mastery.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MoltenStoneBlock extends Block {
    public static final IntProperty AGE = Properties.AGE_3; // Age property (0-3)
    private static final int MAX_AGE = 3;
    public static final MapCodec<MoltenStoneBlock> CODEC = createCodec(MoltenStoneBlock::new);

    public MoltenStoneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /**
     * Defines the final melted state of the block (Lava by default).
     */
    protected BlockState getMeltedState() {
        return Blocks.LAVA.getDefaultState();
    }

    /**
     * Handles entities stepping on the block.
     */
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            super.onSteppedOn(world, pos, state, entity);
            return;
        }

        // Skip damage if the entity is a player wearing Netherite boots
        if (entity instanceof PlayerEntity player && player.getInventory().getArmorStack(0).isOf(Items.NETHERITE_BOOTS)) {
            super.onSteppedOn(world, pos, state, entity);
            return;
        }

        // Apply damage
        livingEntity.serverDamage(world.getDamageSources().hotFloor(), 1.5F);
        super.onSteppedOn(world, pos, state, entity);
    }

    /**
     * Schedule an initial tick when the block is added to the world.
     */
    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            scheduleNextTick(state, world, pos);
        }
    }

    /**
     * Handles block aging and transformation into lava.
     */
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (increaseAge(state, world, pos)) {
            // If aging reaches the max, transform into the melted state
            melt(state, world, pos);
        } else {
            // Schedule another tick for further aging
            scheduleNextTick(state, world, pos);
        }
    }

    /**
     * Increases the block's age by 1. If the age reaches MAX_AGE, it will trigger melting.
     */
    private boolean increaseAge(BlockState state, World world, BlockPos pos) {
        int currentAge = state.get(AGE);
        if (currentAge < MAX_AGE) {
            world.setBlockState(pos, state.with(AGE, currentAge + 1), Block.NOTIFY_LISTENERS);
            return false;
        }
        return true; // Max age reached
    }

    protected void melt(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, getMeltedState(), Block.NOTIFY_ALL);
        world.updateNeighbors(pos, getMeltedState().getBlock());
    }

    /**
     * Schedules the next tick for the block.
     */
    private void scheduleNextTick(BlockState state, World world, BlockPos pos) {
        int delay = world.random.nextInt(30) + 20; // Random delay between 20-60 ticks
        world.scheduleBlockTick(pos, this, delay);
    }
}