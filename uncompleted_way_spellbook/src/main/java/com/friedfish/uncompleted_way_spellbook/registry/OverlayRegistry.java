package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import com.friedfish.uncompleted_way_spellbook.content.gui.overlay.ItemAnimationOverlay;

@EventBusSubscriber(modid = UncompletedWaySpellbook.MODID)
public class OverlayRegistry {

    @SubscribeEvent
    public static void RegistryOverlay(RegisterGuiLayersEvent event){
        event.registerAboveAll(UncompletedWaySpellbook.id("item_animation"), ItemAnimationOverlay.instance);
    }
}
