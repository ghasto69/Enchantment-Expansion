package com.ghasto.enchantment_mastery.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class AquaphobiaCurseEnchantmentEffect implements EnchantmentEntityEffect {
    public static final MapCodec<AquaphobiaCurseEnchantmentEffect> CODEC = MapCodec.unit(AquaphobiaCurseEnchantmentEffect::new);

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if(user.isSubmergedInWater() && user.getAir() > 1) {
            user.setAir(user.getAir() - 2);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
