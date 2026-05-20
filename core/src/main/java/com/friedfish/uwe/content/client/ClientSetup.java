package com.friedfish.uwe.content.client;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.item.debug_item.DebugItem;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import com.friedfish.uwe.UncompletedWayANewExpedition;
import com.friedfish.uwe.content.client.ponder.UWEPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = UncompletedWayANewExpedition.MODID)
public class ClientSetup {
    @SubscribeEvent
    public static void ClientSetupEvent(FMLClientSetupEvent event){
        PonderIndex.addPlugin(new UWEPonderPlugin());
    }
}
