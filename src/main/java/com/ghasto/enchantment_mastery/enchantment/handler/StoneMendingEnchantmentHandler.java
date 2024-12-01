package com.ghasto.enchantment_mastery.enchantment.handler;

import com.ghasto.enchantment_mastery.registry.ModEnchantments;
import com.ghasto.enchantment_mastery.utils.SimpleRegistry;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Hand;

import java.util.Random;

public class StoneMendingEnchantmentHandler {
    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if (blockState.isOf(Blocks.STONE)) {
                ItemStack stack = playerEntity.getStackInHand(Hand.MAIN_HAND);
                int level = EnchantmentHelper.getLevel(SimpleRegistry.getEnchantment(ModEnchantments.STONE_MENDING, world), stack);
                if (level > 0) {
                    Random random = new Random();
                    float chance = 0.25f;
                    chance += (0.25f * level);
                    if (random.nextDouble() < chance && (stack.getDamage() - 1) >= 0)
                        stack.setDamage(stack.getDamage() - 1);
                }
            }
        });
    }
}
