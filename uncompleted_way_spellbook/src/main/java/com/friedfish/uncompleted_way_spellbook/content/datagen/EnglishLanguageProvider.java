package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(PackOutput output, String locale) {
        super(output, UncompletedWaySpellbook.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        this.add(ItemRegistry.DEBUG.get(),"Debug");
        this.add("item."+UncompletedWaySpellbook.MODID+".debugtype","§bChange to debug type: §a%s");

        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type0","0. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type1","1. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type2","2. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type3","3. floating text manager test");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type4","4. damage show test + item decorate test");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type5","5. client chat send message test");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type6","6. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type7","7. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type8","8. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type9","9. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type10","10. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type11","11. null");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type12","12. null");
        this.add("category."+UncompletedWaySpellbook.MODID+".filter","filter");
        this.add("jei."+UncompletedWaySpellbook.MODID+".filter.chance","§dOutput Chance: §a%s");
        this.add("jei."+UncompletedWaySpellbook.MODID+".filter.count","§Output Count: §a%1s-%2s");
    }
}
