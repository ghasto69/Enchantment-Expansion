package com.ghasto.enchantment_mastery.utils;

import com.ghasto.enchantment_mastery.EnchantmentMastery;
import com.ghasto.enchantment_mastery.registry.ModEnchantments;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class SimpleRegistry {
    private final String modID;

    public SimpleRegistry(String modID) {
        this.modID = modID;
    }

    /* Item Registry Helpers */
    public <T extends Item> T item(String name, Function<Item.Settings, T> factory) {
        T item = Registry.register(Registries.ITEM, asID(name), factory.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, asID(name)))));
        return item;
    }


    /* Block Registry Helpers */
    public <T extends Block, I extends BlockItem> T block(String name, Function<AbstractBlock.Settings, T> factory, BiFunction<Block, Item.Settings, I> itemFactory) {
        T block = Registry.register(Registries.BLOCK, asID(name), factory.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, asID(name)))));
        I item = Registry.register(Registries.ITEM, asID(name), itemFactory.apply(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, asID(name)))));
        return block;
    }

    public <T extends Block> T block(String name, Function<AbstractBlock.Settings, T> factory) {
        return Registry.register(Registries.BLOCK, asID(name), factory.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, asID(name)))));
    }

    /* Block Entity Registry Helpers */
    public <T extends BlockEntityType<?>> T blockEntityType(String name, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, asID(name), blockEntityType);
    }

    /* Item Group Registry Helpers */
    public RegistryKey<ItemGroup> itemGroup(String name, ItemGroup itemGroup) {
        RegistryKey<ItemGroup> key = RegistryKey.of(Registries.ITEM_GROUP.getKey(), asID(name));
        Registry.register(Registries.ITEM_GROUP, key, itemGroup);
        return key;
    }

    /* Enchantment Registry Helpers */
    public void enchantment(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }

    public MapCodec<? extends EnchantmentEntityEffect> enchantmentEffect(String name,
                                                                         MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, asID(name), codec);
    }

    public static RegistryEntry<Enchantment> getEnchantment(RegistryKey<Enchantment> enchantment, World world) {
        return world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(enchantment);
    }

    /* Data Components */
    public <T>ComponentType<T> enchantmentComponent(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, asID(name), builderOperator.apply(ComponentType.builder()).build());
    }



    /* Registry Helpers */
    public <T> RegistryKey<T> key(String name, RegistryKey<Registry<T>> registry) {
        return RegistryKey.of(registry, asID(name));
    }



    private Identifier asID(String id) {
        return Identifier.of(modID, id);
    }

}
