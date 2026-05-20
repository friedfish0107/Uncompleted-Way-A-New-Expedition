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
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.debugtype", "§bChange to debug type: §a%s");

        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type0", "0. Empty");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type1", "1. Empty");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type2", "2. Empty");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type3", "3. Floating text controller test");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type4", "4. Damage display test + Item decoration test");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type5", "5. Client chat message sending test");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type6", "6. Inventory swap test");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type7", "7. Empty");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type8", "8. Empty");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type9", "9. Empty");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type10", "10. Empty");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type11", "11. Empty");
        this.add("item." + UncompletedWaySpellbook.MODID + ".debug.type12", "12. Empty");

        this.add("category." + UncompletedWaySpellbook.MODID + ".filter", "Filter");
        this.add("jei." + UncompletedWaySpellbook.MODID + ".filter.chance", "§dOutput Chance: §a%s");
        this.add("jei." + UncompletedWaySpellbook.MODID + ".filter.count", "§dOutput Count: §a%s-%s");
        this.add("jei." + UncompletedWaySpellbook.MODID + ".filter.shift", "§7Press [Shift] to view modified probability");

        this.add("category."+UncompletedWaySpellbook.MODID+".infuse_mana","Infuse Mana");

        this.add(ItemRegistry.STICK_STAFF.get().getDescriptionId(),"木棍法杖");
        this.add(ItemRegistry.STICK_STAFF.get().getDescriptionId()+".desc1","似乎是根有点长的木棍");
        this.add(ItemRegistry.STICK_STAFF.get().getDescriptionId()+".desc2","适合握在手里当法杖");
        this.add(ItemRegistry.STICK_STAFF.get().getDescriptionId()+".desc3","能微弱地提升法术强度");

        this.add(ItemRegistry.ZOMBIE_STAFF.get().getDescriptionId(),"僵尸法杖");
        this.add(ItemRegistry.ZOMBIE_STAFF.get().getDescriptionId()+".desc1","僵尸没有脑子");
        this.add(ItemRegistry.ZOMBIE_STAFF.get().getDescriptionId()+".desc2","但是它们的头颅可以用来吸引其他的亡灵");

        this.add(ItemRegistry.MAGIC_PAPER.get().getDescriptionId(),"魔法纸张");
        this.add(ItemRegistry.MAGIC_PAPER.get().getDescriptionId()+".desc1","往纸上撒点奥数源质就能保存法术");
        this.add(ItemRegistry.MAGIC_PAPER.get().getDescriptionId()+".desc2","这可能是唯一现传的手工法术书手艺");

        this.add(ItemRegistry.ENCHANT_SPELL_BOOK.get().getDescriptionId(),"附魔台书");
        this.add(ItemRegistry.ENCHANT_SPELL_BOOK.get().getDescriptionId()+".desc1","从附魔台上拆下来的书");

        this.add(ItemRegistry.OLD_SKULL_ZOMBIE.get().getDescriptionId(),"远古僵尸头");

        this.add("ui."+UncompletedWaySpellbook.MODID+".all_mana","消耗所有法力值");
        this.add("ui."+UncompletedWaySpellbook.MODID+".mana_per_effect","每消耗 %s 法力值");
        this.add("ui."+UncompletedWaySpellbook.MODID+".arcane_essence_count","获得奥术源质: %s");
        this.add("ui."+UncompletedWaySpellbook.MODID+".max_process","单次最多处理: %s");

        this.add("itemGroup." + UncompletedWaySpellbook.MODID + ".uncompleted_way_spell_book_tab", "Uncompleted Way Spellbook");
    }
}
