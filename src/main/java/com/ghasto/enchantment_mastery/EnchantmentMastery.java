package com.ghasto.enchantment_mastery;

import com.ghasto.enchantment_mastery.enchantment.handler.ButterfingersCurseEnchantmentHandler;
import com.ghasto.enchantment_mastery.enchantment.handler.StoneMendingEnchantmentHandler;
import com.ghasto.enchantment_mastery.registry.ModBlocks;
import com.ghasto.enchantment_mastery.registry.ModComponentTypes;
import com.ghasto.enchantment_mastery.registry.ModEnchantmentEffects;
import com.ghasto.enchantment_mastery.registry.ModEnchantments;
import com.ghasto.enchantment_mastery.datagen.ModRegistryDataGenerator;
import com.ghasto.enchantment_mastery.utils.SimpleRegistry;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantmentMastery implements ModInitializer, DataGeneratorEntrypoint {
	public static final String MOD_ID = "enchantment_mastery";
	public static final SimpleRegistry REGISTRY = new SimpleRegistry(MOD_ID);
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		/* Init Message */
        LOGGER.info("Enchantment Mastery has been initialized on {}", FabricLoader.getInstance().isModLoaded("connector") ? "Neoforge" : "Fabric / Quilt");

		/* Register mod content */
		ModBlocks.register();
		ModComponentTypes.register();
		ModEnchantmentEffects.register();

		StoneMendingEnchantmentHandler.register();
		ButterfingersCurseEnchantmentHandler.register();
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModRegistryDataGenerator::new);
	}



	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, ModEnchantments::bootstrap);
	}
}