package com.friedfish.uncompleted_way_spellbook.content.compat.jei;

import com.friedfish.uncompleted_way_spellbook.content.util.GuiGraphicsHelper;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;

public class ChanceSlotBackground implements IDrawable {
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
        GuiGraphicsHelper.renderChanceSlot(guiGraphics,xOffset-1,yOffset-1);
    }
}
