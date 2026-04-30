package com.friedfish.uncompleted_way_spellbook.content.effect;

import com.friedfish.uncompleted_way_spellbook.core.util.LivingEntityUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import static com.friedfish.uncompleted_way_spellbook.registry.MobEffectRegistry.BLOOD_AREA_LOSE_HEALTH_EFFECT;

public class BloodAreaEffect extends MobEffect {

    public static final double DAMAGE_REDUCTION=0.2;

    public BloodAreaEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    //@SubscribeEvent()

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier){
        float radio = LivingEntityUtil.getHealthRatio(livingEntity);
        int loseHealth = 10-Mth.ceil(radio*10)-1;
        int damageAdditionLevel = Mth.clamp(loseHealth,-1,7);
        if(damageAdditionLevel>=0){
            MobEffectInstance instance=new MobEffectInstance(BLOOD_AREA_LOSE_HEALTH_EFFECT,100,damageAdditionLevel);
            livingEntity.addEffect(instance);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration%20==0;
    }
}
