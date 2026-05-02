package com.friedfish.uncompleted_way_spellbook.content.compat.jei;

import com.friedfish.uncompleted_way_spellbook.content.util.NumberUtil;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class ChanceSlotOverlay implements IDrawable {
    private final int minCount;
    private final int maxCount;
    private final float chance;

    public ChanceSlotOverlay(int minCount, int maxCount, float chance) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.chance = chance;
    }

    @Override
    public int getWidth() {
        return 18;
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        Font font= Minecraft.getInstance().font;
        guiGraphics.drawString(font, NumberUtil.getPercentString(chance),xOffset,yOffset-1,-1);

        String countString= (minCount==maxCount)?String.valueOf(minCount):(minCount +"-"+ maxCount);
        guiGraphics.drawString(font,countString,xOffset-countString.length()*6+17,yOffset+9,-1);
    }
}
