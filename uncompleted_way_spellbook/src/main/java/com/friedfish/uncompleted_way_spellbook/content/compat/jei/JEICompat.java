package com.friedfish.uncompleted_way_spellbook.content.compat.jei;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.compat.jei.category.DrainManaRecipeCategory;
import com.friedfish.uncompleted_way_spellbook.content.compat.jei.category.InfuseManaRecipeCategory;
import com.friedfish.uncompleted_way_spellbook.content.compat.jei.category.SpellFilterRecipeCategory;
import com.friedfish.uncompleted_way_spellbook.content.recipe.drain_mana.DrainManaRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana.InfuseManaRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipe;
import com.friedfish.uncompleted_way_spellbook.registry.RecipeRegistry;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
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

        registration.addRecipeCategories(new InfuseManaRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new DrainManaRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager=Minecraft.getInstance().getConnection().getRecipeManager();
        List<SpellFilterRecipe> filterRecipes=manager.getAllRecipesFor(RecipeRegistry.FILTER.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(SpellFilterRecipeCategory.SPELL_FILTER_RECIPE_TYPE,filterRecipes);

        List<InfuseManaRecipe> infuseManaRecipes=manager.getAllRecipesFor(RecipeRegistry.INFUSE_MANA.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(InfuseManaRecipeCategory.INFUSE_MANA_RECIPE_TYPE,infuseManaRecipes);

        List<DrainManaRecipe> drainManaRecipes=manager.getAllRecipesFor(RecipeRegistry.DRAIN_MANA.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(DrainManaRecipeCategory.DRAIN_MANA_RECIPE_TYPE,drainManaRecipes);
    }

}
