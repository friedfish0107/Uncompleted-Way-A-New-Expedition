package com.friedfish.uncompleted_way_spellbook.core.util;

import net.minecraft.world.entity.LivingEntity;

public class LivingEntityUtil {
    public static float getHealthRatio(LivingEntity entity){
        float health = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        return health/maxHealth;
    }
}
