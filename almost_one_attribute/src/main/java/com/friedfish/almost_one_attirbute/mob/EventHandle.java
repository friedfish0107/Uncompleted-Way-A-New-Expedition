package com.friedfish.almost_one_attirbute.mob;

import com.friedfish.almost_one_attirbute.AlmostOneAttribute;
import com.friedfish.almost_one_attirbute.attribute.AttributeRegistry;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = AlmostOneAttribute.MODID)
public class EventHandle {
    @SubscribeEvent
    public static void handleLivingHurtEvent(LivingIncomingDamageEvent event){
        LivingEntity entity =event.getEntity();
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity le?le:null;

        if(entity.level().isClientSide)return;

        if(entity.isDeadOrDying())return;
        if(attacker==null)return;

        double damageReduction=entity.getAttributeValue(AttributeRegistry.DAMAGE_REDUCTION);
        double damageAddition=attacker.getAttributeValue(AttributeRegistry.DAMAGE_ADDITION);

        double damageFactor=damageAddition-damageReduction;
        damageFactor=Mth.clamp(damageFactor,0.3,2048);

        float originalDamage=event.getOriginalAmount();
        float newDamage=(float) damageFactor*originalDamage;

        event.setAmount(newDamage);
    }
}
