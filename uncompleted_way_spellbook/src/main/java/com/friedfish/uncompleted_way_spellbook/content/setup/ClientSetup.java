package com.friedfish.uncompleted_way_spellbook.content.setup;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.item.debug_item.DebugItem;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = UncompletedWaySpellbook.MODID)
public class ClientSetup {
    @SubscribeEvent
    public static void ClientSetupEvent(FMLClientSetupEvent event){
        event.enqueueWork(()->
                ItemProperties.register(
                        ItemRegistry.DEBUG.get(),
                        UncompletedWaySpellbook.id("enabled"),
                        (itemStack,clientLevel,livingEntity,seed)-> DebugItem.getEnabled(itemStack)?1.0F:0.0F
                ));

    }
}
