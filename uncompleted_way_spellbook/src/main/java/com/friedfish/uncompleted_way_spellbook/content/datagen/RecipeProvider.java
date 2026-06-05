package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.content.item.QuickLimeItem;
import com.friedfish.uncompleted_way_spellbook.content.util.ModCompatHelper;
import com.friedfish.uncompleted_way_spellbook.core.datagen.DrainManaRecipeGen;
import com.friedfish.uncompleted_way_spellbook.core.datagen.InfuseManaRecipeGen;
import com.friedfish.uncompleted_way_spellbook.core.datagen.SpellFilterRecipeGen;
import com.friedfish.uncompleted_way_spellbook.core.recipe.ChanceBasedOutput;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.MAGIC_PAPER.get())
                .requires(Items.PAPER)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_ESSENCE.get())
                .unlockedBy("has_arcane_essence",has(io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_ESSENCE.get()))
                .save(output);

        InfuseManaRecipeGen.create(
                Ingredient.of(TagKey.create(Registries.ITEM, ModCompatHelper.issID("arcane_ingot_base"))),
                io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_INGOT.get().getDefaultInstance(),
                6).save(output);

        DrainManaRecipeGen.create(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),Items.GOLDEN_APPLE.getDefaultInstance(),64).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ItemRegistry.STICK_STAFF.get())
                .pattern(" AS")
                .pattern(" S ")
                .pattern(" S ")
                .define('A', io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_ESSENCE.get())
                .define('S',Items.STICK)
                .unlockedBy("has_arcane_essence",has(io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_ESSENCE.get()))
                .save(output);

        SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(Items.CALCITE),
                RecipeCategory.MISC,
                ItemRegistry.QUICKLIME_DUST.get().getDefaultInstance().copyWithCount(4),
                1f,
                400
        )
                .unlockedBy("has_calcite",has(Items.CALCITE))
                .save(output,"quick_dust_from_blasting");

        SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(Items.STONE),
                RecipeCategory.MISC,
                ItemRegistry.QUICKLIME_DUST_PILE.get(),
                0.1f,
                100
        )
                .unlockedBy("has_stone",has(Items.STONE))
                .save(output,"quick_dust_pile_from_blasting");

        fourToOne(ItemRegistry.QUICKLIME_DUST_PILE.get(),ItemRegistry.QUICKLIME_DUST.get(),output);
        fourToOne(ItemRegistry.SLAKED_LIME_DUST_PILE.get(),ItemRegistry.SLAKED_LIME_DUST.get(),output);
        fourToOne(ItemRegistry.CALCIUM_CARBONATE_DUST_PILE.get(),ItemRegistry.CALCIUM_CARBONATE_DUST.get(),output);
        fourToOne(ItemRegistry.CALCIUM_CARBONATE_DUST.get(), Items.CALCITE,output);
    }
    private void fourToOne(ItemLike input,ItemLike output,RecipeOutput recipeOutput){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,output)
                .pattern("AA ")
                .pattern("AA ")
                .define('A', input)
                .unlockedBy("has_"+ input.asItem(),has(input))
                .save(recipeOutput);
    }
}
