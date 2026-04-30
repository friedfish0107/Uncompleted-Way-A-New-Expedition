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
        this.add("item.uncompleted_way_spellbook.debug.debugtype","§bChange to debug type: §a%s");

        this.add("item.uncompleted_way_spellbook.debug.type0","0. null");
        this.add("item.uncompleted_way_spellbook.debug.type1","1. null");
        this.add("item.uncompleted_way_spellbook.debug.type2","2. null");
        this.add("item.uncompleted_way_spellbook.debug.type3","3. floating text manager test");
        this.add("item.uncompleted_way_spellbook.debug.type4","4. damage show test + item decorate test");
        this.add("item.uncompleted_way_spellbook.debug.type5","5. client chat send message test");
        this.add("item.uncompleted_way_spellbook.debug.type6","6. null");
        this.add("item.uncompleted_way_spellbook.debug.type7","7. null");
        this.add("item.uncompleted_way_spellbook.debug.type8","8. null");
        this.add("item.uncompleted_way_spellbook.debug.type9","9. null");
        this.add("item.uncompleted_way_spellbook.debug.type10","10. null");
        this.add("item.uncompleted_way_spellbook.debug.type11","11. null");
        this.add("item.uncompleted_way_spellbook.debug.type12","12. null");
    }
}
