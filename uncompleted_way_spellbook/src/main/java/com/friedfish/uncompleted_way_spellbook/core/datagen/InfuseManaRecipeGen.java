package com.friedfish.uncompleted_way_spellbook.core.datagen;

import com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana.InfuseManaRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class InfuseManaRecipeGen extends SimpleRecipeBuilder{
    private Ingredient input;
    private ItemStack result;
    private int cost;

    public InfuseManaRecipeGen(Ingredient input, ItemStack result, int cost) {
        this.input = input;
        this.result = result;
        this.cost = cost;
    }
    public static InfuseManaRecipeGen create(Ingredient input, ItemStack result, int cost){
        return new InfuseManaRecipeGen(input,result,cost);
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        InfuseManaRecipe recipe = new InfuseManaRecipe(input, result, cost);
        recipeOutput.accept(id.withPrefix("infuse_"), recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
