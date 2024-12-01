package com.ghasto.enchantment_mastery.registry;

import com.ghasto.enchantment_mastery.EnchantmentMastery;
import com.ghasto.enchantment_mastery.content.block.MoltenStoneBlock;
import com.ghasto.enchantment_mastery.utils.SimpleRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.function.Supplier;

import static com.ghasto.enchantment_mastery.EnchantmentMastery.REGISTRY;

public class ModBlocks {
    public static final MoltenStoneBlock MOLTEN_STONE = REGISTRY.block("molten_stone", MoltenStoneBlock::new);

    public static void register() {}

}
