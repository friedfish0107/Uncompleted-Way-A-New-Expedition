package com.friedfish.uncompleted_way_spellbook.content.compat.jei;

import com.friedfish.uncompleted_way_spellbook.content.util.GuiGraphicsHelper;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;

public class JeiStyleBigSlotBackground implements IDrawable {
    @Override
    public int getWidth() {
        return 26;
    }

    @Override
    public int getHeight() {
        return 26;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        GuiGraphicsHelper.renderJeiStyleBigSlot(guiGraphics,xOffset-5,yOffset-5);
    }
}
