package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.entity.bloodAreaCircle.BloodAreaCircleEntity;
import com.friedfish.uncompleted_way_spellbook.content.entity.mobs.spell.SummonedMinecart;
import com.friedfish.uncompleted_way_spellbook.content.entity.mobs.spell.anvil_strike.AnvilStrikeProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
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
    public static final DeferredHolder<EntityType<?>, EntityType<AbstractMinecart>> SUMMONED_MINECART=ENTITY_TYPES.register("summoned_minecart",
            ()->EntityType.Builder.<AbstractMinecart>of(SummonedMinecart::new, MobCategory.MISC)
                    .sized(0.98f,0.7f)
                    .passengerAttachments(0.1875f)
                    .clientTrackingRange(8)
                    .build(UncompletedWaySpellbook.id("summoned_minecart").toString())
    );
    public static final DeferredHolder<EntityType<?>, EntityType<AnvilStrikeProjectile>> ANVIL_STRIKE_PROJECTILE=ENTITY_TYPES.register("anvil_strike",
            ()->EntityType.Builder.<AnvilStrikeProjectile>of(AnvilStrikeProjectile::new, MobCategory.MISC)
                    .sized(1f,1f)
                    .build(UncompletedWaySpellbook.id("anvil_strike").toString())
    );
}