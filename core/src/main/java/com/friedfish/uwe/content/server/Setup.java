package com.friedfish.uwe.content.server;

import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

import java.io.File;
import java.util.random.RandomGenerator;

@EventBusSubscriber
public class Setup {
    @SubscribeEvent
    public static void onServerSetup(RecipesUpdatedEvent event){
        RecipeManager recipeManager = event.getRecipeManager();
        //recipeManager.injectContext();
    }
}
