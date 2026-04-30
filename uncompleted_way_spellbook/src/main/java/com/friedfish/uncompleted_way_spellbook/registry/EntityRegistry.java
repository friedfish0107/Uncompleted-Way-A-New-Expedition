package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.entity.bloodAreaCircle.BloodAreaCircleEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES=DeferredRegister.create(Registries.ENTITY_TYPE, UncompletedWaySpellbook.MODID);
    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

    public static final DeferredHolder<EntityType<?>, EntityType<BloodAreaCircleEntity>> BLOOD_AREA_CIRCLE_ENTITY=ENTITY_TYPES.register("blood_area_circle",
            ()->EntityType.Builder.<BloodAreaCircleEntity>of(BloodAreaCircleEntity::new, MobCategory.MISC)
                    .sized(32.0f,16.0f)
                    .build(UncompletedWaySpellbook.id("blood_area_circle").toString())
    );
}