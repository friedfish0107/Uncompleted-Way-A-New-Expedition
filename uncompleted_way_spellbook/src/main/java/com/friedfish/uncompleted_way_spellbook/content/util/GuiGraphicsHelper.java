package com.friedfish.uncompleted_way_spellbook.content.util;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class GuiGraphicsHelper {
    private static final ResourceLocation TEXTURE= UncompletedWaySpellbook.id("textures/gui/jei.png");
    private static final int SIZE=64;

    public static void renderSlot(GuiGraphics guiGraphics, int x, int y){
        guiGraphics.blit(TEXTURE,x,y,0,0,18,18,SIZE,SIZE);
    }
    public static void renderChanceSlot(GuiGraphics guiGraphics, int x, int y){
        guiGraphics.blit(TEXTURE,x,y,18,0,18,18,SIZE,SIZE);
    }
    /**
     *渲染箭头，确定左上角坐标
     * 箭头大小:22*15
     */
    public static void renderArray(GuiGraphics guiGraphics, int x, int y){
        guiGraphics.blit(TEXTURE,x,y,0,18,22,15,SIZE,SIZE);
    }

    /**
     *渲染箭头的添加点，为对齐使用箭头坐标即可
     * 箭头大小:3*10
     */
    public static void renderArrayAdditionalInput(GuiGraphics guiGraphics, int x, int y){
        guiGraphics.blit(TEXTURE,x+5,y-4,22,18,3,10,SIZE,SIZE);
    }

    /**
     *渲染jei风格边框，确定左上角坐标
     * 边框大小:26*26
     */
    public static void renderJeiStyleBigSlot(GuiGraphics guiGraphics, int x, int y){
        guiGraphics.blit(TEXTURE,x,y,0,33,26,26,SIZE,SIZE);
        //guiGraphics.fill();
    }

}
