package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output, String locale) {
        super(output, UncompletedWaySpellbook.MODID, locale);
    }

    @Override
    protected void addTranslations() {
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

    }

}
