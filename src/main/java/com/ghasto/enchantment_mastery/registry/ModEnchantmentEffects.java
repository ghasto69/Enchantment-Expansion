package com.ghasto.enchantment_mastery.registry;

import com.ghasto.enchantment_mastery.enchantment.AquaphobiaCurseEnchantmentEffect;
import com.ghasto.enchantment_mastery.enchantment.IceAspectEnchantmentEffect;
import com.ghasto.enchantment_mastery.enchantment.TrueShotEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;

import static com.ghasto.enchantment_mastery.EnchantmentMastery.REGISTRY;

public class ModEnchantmentEffects {
    public static final MapCodec<? extends EnchantmentEntityEffect> TRUE_SHOT_EFFECT = REGISTRY.enchantmentEffect("true_shot_effect", TrueShotEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> ICE_ASPECT_EFFECT = REGISTRY.enchantmentEffect("ice_aspect_effect", IceAspectEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> AQUAPHOBIA_EFFECT = REGISTRY.enchantmentEffect("aquaphobia_effect", AquaphobiaCurseEnchantmentEffect.CODEC);
    public static void register() {}
}
