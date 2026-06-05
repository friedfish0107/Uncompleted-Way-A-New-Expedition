package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import com.friedfish.uncompleted_way_spellbook.registry.SchoolRegistry;
import com.friedfish.uncompleted_way_spellbook.registry.SpellRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output, String locale) {
        super(output, UncompletedWaySpellbook.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup." + UncompletedWaySpellbook.MODID + ".uncompleted_way_spell_book_tab","未尽法术之书");
        this.add(ItemRegistry.DEBUG.get(),"测试物品");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.debugtype","§b切换状态 §a%s §b号");

        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type0","0. 空");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type1","1. 空");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type2","2. 空");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type3","3. 悬浮文字控制器测试");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type4","4. 伤害显示测试+物品装饰测试");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type5","5. 客户端聊天栏发送信息测试");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type6","6. 物品栏交换测试");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type7","7. 空");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type8","8. 空");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type9","9. 空");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type10","10. 空");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type11","11. 空");
        this.add("item."+UncompletedWaySpellbook.MODID+".debug.type12","12. 空");

        this.add("category."+UncompletedWaySpellbook.MODID+".filter","筛选");
        this.add("jei."+UncompletedWaySpellbook.MODID+".filter.chance","§d产出概率: §a%s");
        this.add("jei."+UncompletedWaySpellbook.MODID+".filter.count","§d产出数量: §a%s-%s");
        this.add("jei."+UncompletedWaySpellbook.MODID+".filter.shift","§7按下[Shift]键以查看修改后概率");

        this.add("category."+UncompletedWaySpellbook.MODID+".infuse_mana","注魔");

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

        this.add(ItemRegistry.MATTER.get().getDescriptionId(),"物质");

        this.add(ItemRegistry.QUICKLIME_DUST.get().getDescriptionId(),"白灼灰");
        this.add(ItemRegistry.QUICKLIME_DUST_PILE.get().getDescriptionId(),"小堆白灼灰");
        this.add(ItemRegistry.SLAKED_LIME_DUST.get().getDescriptionId(),"余热灰");
        this.add(ItemRegistry.SLAKED_LIME_DUST_PILE.get().getDescriptionId(),"小堆余热灰");
        this.add(ItemRegistry.CALCIUM_CARBONATE_DUST.get().getDescriptionId(),"陈化石粉");
        this.add(ItemRegistry.CALCIUM_CARBONATE_DUST_PILE.get().getDescriptionId(),"小堆陈化石粉");

        this.add(ItemRegistry.QUICKLIME_DUST.get().getDescriptionId()+".desc1","CaO");
        this.add(ItemRegistry.QUICKLIME_DUST_PILE.get().getDescriptionId()+".desc1","CaO");
        this.add(ItemRegistry.SLAKED_LIME_DUST.get().getDescriptionId()+".desc1","Ca(OH)₂");
        this.add(ItemRegistry.SLAKED_LIME_DUST_PILE.get().getDescriptionId()+".desc1","Ca(OH)₂");
        this.add(ItemRegistry.CALCIUM_CARBONATE_DUST.get().getDescriptionId()+".desc1","CaCO₃");
        this.add(ItemRegistry.CALCIUM_CARBONATE_DUST_PILE.get().getDescriptionId()+".desc1","CaCO₃");

        this.add("ui."+UncompletedWaySpellbook.MODID+".all_mana","消耗所有法力值");
        this.add("ui."+UncompletedWaySpellbook.MODID+".mana_per_effect","每消耗 %s 法力值");
        this.add("ui."+UncompletedWaySpellbook.MODID+".arcane_essence_count","获得奥术源质: %s");
        this.add("ui."+UncompletedWaySpellbook.MODID+".max_process","单次最多处理: %s");


        this.add(SpellRegistry.BLOOD_AREA.get().getComponentId(),"血红之域");
        this.add(SpellRegistry.CRYSTAL.get().getComponentId(),"结晶");
        this.add(SpellRegistry.INFUSE_MANA.get().getComponentId(),"注魔");
        this.add(SpellRegistry.DRAIN_MANA.get().getComponentId(),"汲魔");
        this.add(SpellRegistry.FILTER.get().getComponentId(),"筛选");
        this.add(SpellRegistry.OPEN_CRAFTING_TABLE.get().getComponentId(),"打开工作台");
        this.add(SpellRegistry.BLAST.get().getComponentId(),"高温煅烧");
        this.add(SpellRegistry.THROW_STONE.get().getComponentId(),"投掷石头");

        this.add("school."+UncompletedWaySpellbook.MODID+".craft","合成");
        this.add("school."+UncompletedWaySpellbook.MODID+".mine","矿物");

    }

}
