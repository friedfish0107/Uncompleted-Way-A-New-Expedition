package com.friedfish.uwe.content.common;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

@EventBusSubscriber
public class CommonSetup {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        //event
    }
}
