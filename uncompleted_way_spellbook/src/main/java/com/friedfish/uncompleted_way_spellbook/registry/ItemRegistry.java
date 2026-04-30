package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.item.debug_item.DebugItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS=DeferredRegister.create(Registries.ITEM, UncompletedWaySpellbook.MODID);
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static final DeferredHolder<Item,Item> DEBUG=ITEMS.register("debug",()->new DebugItem(
            new Item.Properties().stacksTo(1)
                    .component(ComponentRegistry.ENABLED.get(),false)
                    .component(ComponentRegistry.DEBUG_TYPE.get(), 0)));
}