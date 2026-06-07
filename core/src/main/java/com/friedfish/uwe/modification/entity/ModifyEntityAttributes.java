package com.friedfish.uwe.modification.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import java.util.List;

@EventBusSubscriber
public class ModifyEntityAttributes {
    @SubscribeEvent
    public static void onModifyAttribute(EntityAttributeModificationEvent event){
        final EntityType<? extends LivingEntity>[] ZOMBIE_LIKE= new EntityType[]{EntityType.ZOMBIE,EntityType.HUSK,EntityType.DROWNED};
        for (EntityType<? extends LivingEntity> entityType : ZOMBIE_LIKE) {
            event.add(entityType, Attributes.MAX_HEALTH,50);
            event.add(entityType, Attributes.ATTACK_DAMAGE,12);
            event.add(entityType, Attributes.ARMOR,8);
            event.add(entityType, Attributes.KNOCKBACK_RESISTANCE,0.5);
        }
        event.add(EntityType.SPIDER, Attributes.MOVEMENT_SPEED,1);//0.33scale
        event.add(EntityType.SPIDER, Attributes.ATTACK_DAMAGE,4);
        event.add(EntityType.SPIDER, Attributes.MAX_HEALTH,8);

        event.add(EntityType.CREEPER, Attributes.MAX_HEALTH,40);
        event.add(EntityType.CREEPER, Attributes.KNOCKBACK_RESISTANCE,0.9);

        event.add(EntityType.SKELETON, Attributes.MAX_HEALTH,100);
    }
}
