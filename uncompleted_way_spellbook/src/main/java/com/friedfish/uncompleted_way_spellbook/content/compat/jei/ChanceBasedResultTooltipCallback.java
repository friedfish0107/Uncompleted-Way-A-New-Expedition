package com.friedfish.uncompleted_way_spellbook.content.compat.jei;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.util.NumberUtil;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class ChanceBasedResultTooltipCallback implements IRecipeSlotRichTooltipCallback {
    private final float chance;
    private final int minCount;
    private final int maxCont;
    private final int modifiedMaxCount;
    private final float modifiedChance;

    public ChanceBasedResultTooltipCallback(int minCount, int maxCont,float chance,int modifiedMaxCount,float modifiedChance) {
        this.chance = chance;
        this.minCount = minCount;
        this.maxCont = maxCont;
        this.modifiedMaxCount=modifiedMaxCount;
        this.modifiedChance=modifiedChance;
    }

    @Override
    public void onRichTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("jei."+ UncompletedWaySpellbook.MODID+".filter.chance", NumberUtil.getPercentString(modifiedChance)).withStyle(ChatFormatting.GREEN));
            tooltip.add(Component.translatable("jei."+ UncompletedWaySpellbook.MODID+".filter.count",minCount,modifiedMaxCount).withStyle(ChatFormatting.GREEN));
        }
        else {
            tooltip.add(Component.translatable("jei."+ UncompletedWaySpellbook.MODID+".filter.chance", NumberUtil.getPercentString(chance)).withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.translatable("jei."+ UncompletedWaySpellbook.MODID+".filter.count",minCount,maxCont).withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.translatable("jei."+UncompletedWaySpellbook.MODID+".filter.shift"));
        }

    }
}
