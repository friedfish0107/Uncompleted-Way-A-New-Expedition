package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ComponentRegistry {
    private static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, UncompletedWaySpellbook.MODID);

    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }

    private static <T>DeferredHolder<DataComponentType<?>,DataComponentType<T>>register(String name, UnaryOperator<DataComponentType.Builder<T>> pBuilder){
        return COMPONENTS.register(name,()-> pBuilder.apply(DataComponentType.builder()).build());
    }

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Boolean>> ENABLED=register("enabled",pBuilder->pBuilder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Integer>> DEBUG_TYPE=register("debug_type",pBuilder->pBuilder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
}
