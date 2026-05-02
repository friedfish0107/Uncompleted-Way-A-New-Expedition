package com.friedfish.uncompleted_way_spellbook.content.compat.jei;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ScrollWithSpellIcon implements IDrawable {

    private ItemStack scrollItemStack;
    private AbstractSpell spell;
    private ResourceLocation spellIcon;

    public ScrollWithSpellIcon(AbstractSpell spell) {
        this.spell = spell;
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
        if (spellIcon==null)
            spellIcon=spell.getSpellIconResource();
        if(scrollItemStack==null){
            scrollItemStack=new ItemStack(ItemRegistry.SCROLL);
            ISpellContainer.createScrollContainer(spell,1,scrollItemStack);
        }
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0,0,0);
        guiGraphics.renderFakeItem(scrollItemStack,xOffset,yOffset);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0,0,300);
        guiGraphics.blit(spellIcon,xOffset+8,yOffset+8,0,0,8,8,8,8);
        guiGraphics.pose().popPose();



    }
}
