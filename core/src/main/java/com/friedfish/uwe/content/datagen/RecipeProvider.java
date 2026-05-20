package com.friedfish.uwe.content.datagen;

import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider{
    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput p_recipeOutput, HolderLookup.Provider holderLookup) {
        //ShapedRecipeBuilder
        ;
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.BOOK_STACK_BLOCK_ITEM.get())
                .requires(Items.BOOK)
                .unlockedBy("has_book",has(Items.BOOK))
                .save(p_recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BOOK)
                .requires(ItemRegistry.BOOK_STACK_BLOCK_ITEM.get())
                .unlockedBy("has_book",has(Items.BOOK))
                .save(p_recipeOutput);
    }
}
