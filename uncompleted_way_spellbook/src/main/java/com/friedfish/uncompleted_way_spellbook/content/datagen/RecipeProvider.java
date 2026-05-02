package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.core.datagen.SpellFilterRecipeGen;
import com.friedfish.uncompleted_way_spellbook.core.recipe.ChanceBasedOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {
    public RecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        SpellFilterRecipeGen.create(Blocks.DIRT.defaultBlockState(),0)
                .addOutput(Items.RAW_IRON,1,2,0.4f)
                .addOutput(Items.DIAMOND,1,1,0.02f)
                .save(output);

        SpellFilterRecipeGen.create(Blocks.GRAVEL.defaultBlockState(),0)
                .addOutput(Items.RAW_IRON,1,2,0.4f)
                .addOutput(Items.RAW_COPPER,1,5,0.6f)
                .addOutput(Items.RAW_GOLD,1,1,0.2f)
                .addOutput(Items.DIAMOND,1,1,0.02f)
                .addOutput(Items.EMERALD,1,1,0.01f)
                .addOutput(Items.AMETHYST_SHARD,2,5,0.01f)
                .addOutput(Items.LAPIS_LAZULI,3,9,0.05f)
                .save(output);
    }
}
