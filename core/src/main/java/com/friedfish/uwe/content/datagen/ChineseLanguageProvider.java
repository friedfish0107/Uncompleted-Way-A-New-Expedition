package com.friedfish.uwe.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import com.friedfish.uwe.UncompletedWayANewExpedition;
import com.friedfish.uwe.content.client.ponder.UWEPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.BiConsumer;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output, String locale) {
        super(output, UncompletedWayANewExpedition.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("uwe.ponder.get_inscription.header","获取法术抄写台");
    }

}
