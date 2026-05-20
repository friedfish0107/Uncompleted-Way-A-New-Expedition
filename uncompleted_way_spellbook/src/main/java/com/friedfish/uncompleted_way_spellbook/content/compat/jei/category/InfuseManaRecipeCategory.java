package com.friedfish.uncompleted_way_spellbook.content.compat.jei.category;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.compat.jei.ScrollWithSpellIcon;
import com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana.InfuseManaRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipe;
import com.friedfish.uncompleted_way_spellbook.content.spell.recipe.FilterSpell;
import com.friedfish.uncompleted_way_spellbook.content.spell.recipe.InfuseManaSpell;
import com.friedfish.uncompleted_way_spellbook.content.util.GuiGraphicsHelper;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfuseManaRecipeCategory implements IRecipeCategory<InfuseManaRecipe> {
    public static final RecipeType<InfuseManaRecipe> INFUSE_MANA_RECIPE_TYPE=RecipeType.create(UncompletedWaySpellbook.MODID,"infuse_mana",InfuseManaRecipe.class);
    private final IDrawable icon;
    private static final int WIDTH = 112;
    private static final int HEIGHT = 72;

    public InfuseManaRecipeCategory(IGuiHelper guiHelper) {
        this.icon = new ScrollWithSpellIcon(new InfuseManaSpell());
    }

    @Override
    public RecipeType<InfuseManaRecipe> getRecipeType() {
        return INFUSE_MANA_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("category."+UncompletedWaySpellbook.MODID+".infuse_mana");
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfuseManaRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(WIDTH/2/2-8-8,HEIGHT/2-8)
                .addItemStacks(List.of(recipe.getInput().getItems()))
                .setOutputSlotBackground();
        builder.addOutputSlot(3*WIDTH/2/2-8+8,HEIGHT/2-8)
                .addItemStack(recipe.getResult())
                .setOutputSlotBackground();
        builder.addInputSlot(WIDTH/2-8,HEIGHT/2-8-10-16)
                .addItemStack(new ItemStack(ItemRegistry.ARCANE_ESSENCE,recipe.getCost()))
                .setStandardSlotBackground();
    }

    @Override
    public void draw(InfuseManaRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        GuiGraphicsHelper.renderArray(guiGraphics,WIDTH/2-11,HEIGHT/2-7);
        //GuiGraphicsHelper.renderArrayAdditionalInput(guiGraphics,WIDTH/2-11,HEIGHT/2-7);
    }
}
