package com.friedfish.uwe.registry;

import com.friedfish.uwe.UncompletedWayANewExpedition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS=DeferredRegister.create(Registries.ITEM, UncompletedWayANewExpedition.MODID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static final DeferredHolder<Item,Item> DEBUG=ITEMS.register("debug",()->new Item(new Item.Properties()));
}
