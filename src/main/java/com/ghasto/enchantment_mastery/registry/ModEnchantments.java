package com.ghasto.enchantment_mastery.registry;

import com.ghasto.enchantment_mastery.EnchantmentMastery;
import com.ghasto.enchantment_mastery.datagen.ModTags;
import com.ghasto.enchantment_mastery.enchantment.AquaphobiaCurseEnchantmentEffect;
import com.ghasto.enchantment_mastery.enchantment.IceAspectEnchantmentEffect;
import com.ghasto.enchantment_mastery.enchantment.TrueShotEnchantmentEffect;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.AttributeEnchantmentEffect;
import net.minecraft.enchantment.effect.DamageImmunityEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.enchantment.effect.entity.ReplaceDiskEnchantmentEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.apache.logging.log4j.core.config.AwaitCompletionReliabilityStrategy;

import java.util.Optional;

import static com.ghasto.enchantment_mastery.EnchantmentMastery.REGISTRY;

public class ModEnchantments {
    /* Armor Enchantments */
    public static final RegistryKey<Enchantment> BLAZE_WALKER = REGISTRY.key("blaze_walker", RegistryKeys.ENCHANTMENT);
    public static final RegistryKey<Enchantment> HIGH_HEELS = REGISTRY.key("high_heels", RegistryKeys.ENCHANTMENT);

    /* Pickaxe Enchantments */
    public static final RegistryKey<Enchantment> STONE_MENDING = REGISTRY.key("stone_mending", RegistryKeys.ENCHANTMENT);

    /* Sword Enchantments */
    public static final RegistryKey<Enchantment> SWIFT_ATTACK = REGISTRY.key("swift_attack", RegistryKeys.ENCHANTMENT);
    public static final RegistryKey<Enchantment> ICE_ASPECT = REGISTRY.key("ice_aspect", RegistryKeys.ENCHANTMENT);

    /* Bow Enchantments */
    public static final RegistryKey<Enchantment> TRUE_SHOT = REGISTRY.key("true_shot", RegistryKeys.ENCHANTMENT);

    /* Tool Enchantments */
    public static final RegistryKey<Enchantment> FOSSIL_FORTUNE = REGISTRY.key("fossil_fortune", RegistryKeys.ENCHANTMENT);

    /* Curses */
    public static final RegistryKey<Enchantment> CURSE_OF_BUTTERFINGERS = REGISTRY.key("curse_of_butterfingers", RegistryKeys.ENCHANTMENT);
    public static final RegistryKey<Enchantment> AQUAPHOBIA = REGISTRY.key("aquaphobia", RegistryKeys.ENCHANTMENT);

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        REGISTRY.enchantment(
                registerable,
                BLAZE_WALKER,
                Enchantment.builder(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE), // Generic foot armor tag
                                        1, // Minimum enchantment level
                                        2, // Maximum enchantment level
                                        Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                                        Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                                        4, // Rarity weight
                                        new AttributeModifierSlot[]{AttributeModifierSlot.FEET} // Applicable slot
                                )
                        )
                        .exclusiveSet(
                                enchantments.getOrThrow(EnchantmentTags.BOOTS_EXCLUSIVE_SET) // Makes it mutually exclusive with other boot enchantments
                        )
                        .addEffect(
                                EnchantmentEffectComponentTypes.DAMAGE_IMMUNITY,
                                DamageImmunityEnchantmentEffect.INSTANCE,
                                DamageSourcePropertiesLootCondition.builder(
                                        DamageSourcePredicate.Builder.create()
                                                .tag(TagPredicate.expected(DamageTypeTags.BURN_FROM_STEPPING))
                                                .tag(TagPredicate.unexpected(DamageTypeTags.BYPASSES_INVULNERABILITY))
                                )
                        )
                        .addEffect(
                                EnchantmentEffectComponentTypes.LOCATION_CHANGED,
                                new ReplaceDiskEnchantmentEffect(
                                        new EnchantmentLevelBasedValue.Clamped(
                                                EnchantmentLevelBasedValue.linear(2.0F, 1.0F), // Radius: 2.0 + 2.0 per level
                                                0.0F,
                                                16.0F
                                        ),
                                        EnchantmentLevelBasedValue.constant(1.0F), // Height: always 1.0
                                        new Vec3i(0, -1, 0), // Offset below player
                                        Optional.of(
                                                BlockPredicate.allOf(
                                                        new BlockPredicate[]{
                                                                BlockPredicate.matchingBlockTag(new Vec3i(0, 1, 0), BlockTags.AIR),
                                                                BlockPredicate.matchingBlocks(new Block[]{Blocks.LAVA}),
                                                                BlockPredicate.matchingFluids(new Fluid[]{Fluids.LAVA}),
                                                                BlockPredicate.unobstructed()
                                                        }
                                                )
                                        ),
                                        BlockStateProvider.of(ModBlocks.MOLTEN_STONE), // Replacing with molten stone
                                        Optional.of(GameEvent.BLOCK_PLACE) // Trigger game event
                                ),
                                EntityPropertiesLootCondition.builder(
                                        LootContext.EntityTarget.THIS,
                                        net.minecraft.predicate.entity.EntityPredicate.Builder.create()
                                                .flags(net.minecraft.predicate.entity.EntityFlagsPredicate.Builder.create().onGround(true)) // Only works on-ground
                                )
                        )
        );

        REGISTRY.enchantment(registerable, STONE_MENDING, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.PICKAXES),
                1, // Minimum enchantment level
                2, // Maximum enchantment level
                Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                4, // Rarity weight
                new AttributeModifierSlot[]{AttributeModifierSlot.MAINHAND} // Applicable slot
        )));

        REGISTRY.enchantment(registerable, SWIFT_ATTACK, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                1, // Minimum enchantment level
                2, // Maximum enchantment level
                Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                4, // Rarity weight
                new AttributeModifierSlot[]{AttributeModifierSlot.MAINHAND} // Applicable slot
        )).addEffect(EnchantmentEffectComponentTypes.ATTRIBUTES, new AttributeEnchantmentEffect(Identifier.of(EnchantmentMastery.MOD_ID, "swift_attack"), EntityAttributes.GENERIC_ATTACK_SPEED, new EnchantmentLevelBasedValue.LevelsSquared(1F), EntityAttributeModifier.Operation.ADD_VALUE)));

        REGISTRY.enchantment(registerable, HIGH_HEELS, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                2, // Minimum enchantment level
                1, // Maximum enchantment level
                Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                4, // Rarity weight
                new AttributeModifierSlot[]{AttributeModifierSlot.FEET} // Applicable slot
        )).addEffect(
                EnchantmentEffectComponentTypes.ATTRIBUTES,
                new AttributeEnchantmentEffect(
                        Identifier.of(EnchantmentMastery.MOD_ID, "high_heels"), // Identifier for the effect
                        EntityAttributes.GENERIC_STEP_HEIGHT, // Targeted attribute (step height)
                        EnchantmentLevelBasedValue.constant(0.4f), // Fixed value of 1f for the attribute,
                        EntityAttributeModifier.Operation.ADD_VALUE
                )
        ));

        REGISTRY.enchantment(registerable, TRUE_SHOT, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                2, // Minimum enchantment level
                1, // Maximum enchantment level
                Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                4, // Rarity weight
                new AttributeModifierSlot[]{AttributeModifierSlot.MAINHAND} // Applicable slot
        )).addEffect(
                EnchantmentEffectComponentTypes.PROJECTILE_SPAWNED,
                new TrueShotEnchantmentEffect()
        ));

        REGISTRY.enchantment(registerable, ICE_ASPECT, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                2,
                2, // Maximum enchantment level
                Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                4, // Rarity weight
                new AttributeModifierSlot[]{AttributeModifierSlot.MAINHAND} // Applicable slot
        )).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.ATTACKER,
                EnchantmentEffectTarget.VICTIM,
                new IceAspectEnchantmentEffect()
        ));

        REGISTRY.enchantment(registerable, CURSE_OF_BUTTERFINGERS, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.MINING_ENCHANTABLE),
                2,
                1, // Maximum enchantment level
                Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                4, // Rarity weight
                new AttributeModifierSlot[]{AttributeModifierSlot.MAINHAND} // Applicable slot
        )));

        REGISTRY.enchantment(registerable, AQUAPHOBIA, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                2,
                1, // Maximum enchantment level
                Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                4, // Rarity weight
                new AttributeModifierSlot[]{AttributeModifierSlot.ARMOR} // Applicable slot
        )).addEffect(EnchantmentEffectComponentTypes.TICK, new AquaphobiaCurseEnchantmentEffect())
        );

        REGISTRY.enchantment(registerable, FOSSIL_FORTUNE, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ModTags.BRUSH_ENCHANTABLE),
                2,
                2, // Maximum enchantment level
                Enchantment.leveledCost(10, 10), // Min cost: 10, increase per level: 10
                Enchantment.leveledCost(25, 10), // Max cost: 25, increase per level: 10
                4, // Rarity weight
                new AttributeModifierSlot[]{AttributeModifierSlot.MAINHAND} // Applicable slot
        )).addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.ATTACKER,
                EnchantmentEffectTarget.VICTIM,
                new IceAspectEnchantmentEffect()
        ));
    }
}
