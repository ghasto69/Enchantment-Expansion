package com.ghasto.enchantment_mastery.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class IceAspectEnchantmentEffect implements EnchantmentEntityEffect {
    public static final MapCodec<IceAspectEnchantmentEffect> CODEC = MapCodec.unit(IceAspectEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        ((LivingEntity)user).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30 * level, 2 * level));
        ((LivingEntity)user).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20 * level, level));

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
