package com.friedfish.uwe.content.datagen;

import com.friedfish.uwe.content.datagen.ChineseLanguageProvider;
import com.friedfish.uwe.content.datagen.EnglishLanguageProvider;
import com.friedfish.uwe.UncompletedWayANewExpedition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = UncompletedWayANewExpedition.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void generate(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput output=event.getGenerator().getPackOutput();

        CompletableFuture<HolderLookup.Provider> lookupProvider=event.getLookupProvider();

        DatapackBuiltinEntriesProvider datapackProvider=new RegistryDataGenerator(output,lookupProvider);

        generator.addProvider(event.includeClient(),datapackProvider);

        generator.addProvider(event.includeClient(),new EnglishLanguageProvider(output,"en_us"));
        generator.addProvider(event.includeClient(),new ChineseLanguageProvider(output,"zh_cn"));

        generator.addProvider(event.includeServer(),new RecipeProvider(output,lookupProvider));

        generator.addProvider(event.includeServer(),new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(LootTableProviders.Entity::new, LootContextParamSets.ENTITY)),
                lookupProvider
        ));
    }
}
