package com.friedfish.uncompleted_way_spellbook.content.compat.jei;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.compat.jei.category.SpellFilterRecipeCategory;
import com.friedfish.uncompleted_way_spellbook.content.recipe.SpellFilterRecipe;
import com.friedfish.uncompleted_way_spellbook.registry.RecipeRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return UncompletedWaySpellbook.id("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        registration.addRecipeCategories(new SpellFilterRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager=Minecraft.getInstance().getConnection().getRecipeManager();
        List<SpellFilterRecipe> recipes=manager.getAllRecipesFor(RecipeRegistry.FILTER.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(SpellFilterRecipeCategory.SPELL_FILTER_RECIPE_TYPE,recipes);
    }

}
