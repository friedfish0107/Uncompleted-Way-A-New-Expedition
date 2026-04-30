package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.almost_one_attirbute.attribute.AttributeRegistry;
import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.effect.BloodAreaEffect;
import com.friedfish.uncompleted_way_spellbook.content.effect.BloodAreaLostHealthEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MobEffectRegistry {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS=DeferredRegister.create(Registries.MOB_EFFECT, UncompletedWaySpellbook.MODID);
    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }

    public static final DeferredHolder<MobEffect,MobEffect>BLOOD_AREA_EFFECT=MOB_EFFECTS.register("blood_area",
            ()->new BloodAreaEffect(MobEffectCategory.NEUTRAL,0xFF0000)
                    .addAttributeModifier(AttributeRegistry.DAMAGE_REDUCTION,UncompletedWaySpellbook.id("blood_area"),BloodAreaEffect.DAMAGE_REDUCTION, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final DeferredHolder<MobEffect,MobEffect>BLOOD_AREA_LOSE_HEALTH_EFFECT=MOB_EFFECTS.register("blood_area_lost_health",
            ()->new BloodAreaLostHealthEffect(MobEffectCategory.BENEFICIAL,0xFF0000)
                    .addAttributeModifier(AttributeRegistry.DAMAGE_ADDITION,UncompletedWaySpellbook.id("blood_area_lost_health"),BloodAreaLostHealthEffect.DAMAGE_ADDITION_PER_LEVEL, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );

}
