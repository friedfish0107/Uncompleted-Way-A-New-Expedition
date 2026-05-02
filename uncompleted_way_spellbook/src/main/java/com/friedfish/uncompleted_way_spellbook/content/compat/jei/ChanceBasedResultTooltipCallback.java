package com.friedfish.uncompleted_way_spellbook.content.compat.jei;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.util.NumberUtil;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class ChanceBasedResultTooltipCallback implements IRecipeSlotRichTooltipCallback {
    private final float chance;
    private final int minCount;
    private final int maxCont;

    public ChanceBasedResultTooltipCallback(int minCount, int maxCont,float chance) {
        this.chance = chance;
        this.minCount = minCount;
        this.maxCont = maxCont;
    }

    @Override
    public void onRichTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip) {

        tooltip.add(Component.translatable("jei."+ UncompletedWaySpellbook.MODID+".filter.chance", NumberUtil.getPercentString(chance)).withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("jei."+ UncompletedWaySpellbook.MODID+".filter.count",minCount,maxCont).withStyle(ChatFormatting.GREEN));
    }
}
