package com.friedfish.uncompleted_way_spellbook.core.datagen;

import com.friedfish.uncompleted_way_spellbook.content.recipe.drain_mana.DrainManaRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana.InfuseManaRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class DrainManaRecipeGen extends SimpleRecipeBuilder{
    private Ingredient input;
    private ItemStack result;
    private int income;

    public DrainManaRecipeGen(Ingredient input, ItemStack result, int income) {
        this.input = input;
        this.result = result;
        this.income = income;
    }
    public static DrainManaRecipeGen create(Ingredient input, ItemStack result, int income){
        return new DrainManaRecipeGen(input,result,income);
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

        DrainManaRecipe recipe = new DrainManaRecipe(input, result, income);
        recipeOutput.accept(id.withPrefix("drain_"), recipe, advancement.build(id.withPrefix("recipes/")));
    }

}
