package com.friedfish.almost_one_attirbute.mob;

import com.friedfish.almost_one_attirbute.AlmostOneAttribute;
import com.friedfish.almost_one_attirbute.attribute.AttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = AlmostOneAttribute.MODID)
public class AddAttribute {
    @SubscribeEvent
    public static void createEntityAttribute(EntityAttributeModificationEvent event) {
        for(EntityType<? extends LivingEntity> entity:event.getTypes()) {
            event.add(
                    entity,
                    AttributeRegistry.DAMAGE_REDUCTION
            );
            event.add(
                    entity,
                    AttributeRegistry.DAMAGE_ADDITION
            );
        }
    }
}
