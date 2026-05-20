package com.friedfish.uncompleted_way_spellbook.misc;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.IItemDecorator;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;

@EventBusSubscriber
public class ScrollDecorates {
    @SubscribeEvent
    public static void registryScrollItemDecorate(RegisterItemDecorationsEvent event){
        event.register(ItemRegistry.SCROLL.get(), new IItemDecorator() {
            @Override
            public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
                if(!Screen.hasShiftDown())return false;
                ISpellContainer container = ISpellContainer.get(stack);
                if(container==null)return false;
                ResourceLocation icon= container.getSpellAtIndex(0).getSpell().getSpellIconResource();
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(0,0,200);
                //guiGraphics.pose().scale(0.5F,0.5F,0.5F);
                guiGraphics.blit(icon,xOffset,yOffset+8,0,0,8,8,8,8);
                guiGraphics.pose().popPose();
                return true;
            }
        });
    }
}
