package com.ghasto.enchantment_mastery.enchantment.handler;

import com.ghasto.enchantment_mastery.registry.ModEnchantments;
import com.ghasto.enchantment_mastery.utils.SimpleRegistry;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;

import java.util.Random;

public class ButterfingersCurseEnchantmentHandler {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(!world.isClient) {
                ItemStack stack = playerEntity.getStackInHand(Hand.MAIN_HAND);
                int level = EnchantmentHelper.getLevel(SimpleRegistry.getEnchantment(ModEnchantments.CURSE_OF_BUTTERFINGERS, world), stack);
                Random random = new Random();
                float chance = 0.15f;
                if (random.nextDouble() < chance) {
                    playerEntity.dropItem(stack.copy(), false);
                    stack.decrement(1);
                }
            }
        });
    }
}
