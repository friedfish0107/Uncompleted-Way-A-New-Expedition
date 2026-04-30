package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = UncompletedWaySpellbook.MODID)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        var generator=event.getGenerator();
        var output=generator.getPackOutput();
        var lookupProvider=event.getLookupProvider();
        var existingFileHelper=event.getExistingFileHelper();

        generator.addProvider(event.includeClient(),new EnglishLanguageProvider(output,"en_us"));
        generator.addProvider(event.includeClient(),new ChineseLanguageProvider(output,"zh_cn"));

        generator.addProvider(event.includeClient(),new ItemModelProvider(output,existingFileHelper));
    }
}
