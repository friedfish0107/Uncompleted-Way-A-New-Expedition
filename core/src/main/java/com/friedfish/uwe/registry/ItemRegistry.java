package com.friedfish.uwe.registry;

import com.friedfish.uncompleted_way_spellbook.registry.CreativeTabRegistry;
import com.friedfish.uwe.UncompletedWayANewExpedition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EventBusSubscriber
public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS=DeferredRegister.create(Registries.ITEM, UncompletedWayANewExpedition.MODID);
    private static final List<ResourceLocation> basicItemModelResourceLocation=new ArrayList<>();

    public static List<ResourceLocation> getBasicItemModelResourceLocation(){
        return basicItemModelResourceLocation;
    }
    public static Collection<DeferredHolder<Item, ? extends Item>> getAllUWEItem(){
        return ITEMS.getEntries();
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static final DeferredHolder<Item,Item> DEBUG=ITEMS.register("debug",()->new Item(new Item.Properties()));

    @SubscribeEvent
    public static void onRegItemCreTab(BuildCreativeModeTabContentsEvent event){
        if(event.getTab()== CreativeTabRegistry.EQUIPMENT_TAB.get()){
            getAllUWEItem().forEach(item->event.accept(item.get()));
        }
    }
}
