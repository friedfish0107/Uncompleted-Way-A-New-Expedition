package com.friedfish.uwe.content.datagen;

import com.friedfish.uwe.UncompletedWayANewExpedition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = UncompletedWayANewExpedition.MODID)
public class DataGenerators {
    @SubscribeEvent
    public void generate(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput output=event.getGenerator().getPackOutput();

        CompletableFuture<HolderLookup.Provider> lookupProvider=event.getLookupProvider();

        DatapackBuiltinEntriesProvider datapackProvider=new RegistryDataGenerator(output,lookupProvider);

        generator.addProvider(event.includeClient(),datapackProvider);

    }
}
