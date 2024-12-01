package com.ghasto.enchantment_mastery.registry;

import com.ghasto.enchantment_mastery.EnchantmentMastery;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.loot.context.LootContextTypes;

import java.util.List;

import static com.ghasto.enchantment_mastery.EnchantmentMastery.REGISTRY;

public class ModComponentTypes {
    public static final ComponentType<List<EnchantmentEffectEntry<EnchantmentEntityEffect>>> BREAK_BLOCK =EnchantmentMastery.REGISTRY.enchantmentComponent("break_block", (builder) -> {
        return builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentEntityEffect.CODEC, LootContextTypes.HIT_BLOCK).listOf());
    });
    public static void register() {}
}
