package com.friedfish.uncompleted_way_spellbook.content.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BloodAreaLostHealthEffect extends MobEffect{
    public static final double DAMAGE_ADDITION_PER_LEVEL=0.05;

    public BloodAreaLostHealthEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
}
