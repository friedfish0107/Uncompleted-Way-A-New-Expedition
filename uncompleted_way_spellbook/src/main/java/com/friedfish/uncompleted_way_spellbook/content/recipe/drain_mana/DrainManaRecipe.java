package com.friedfish.uncompleted_way_spellbook.content.recipe.drain_mana;

import com.friedfish.uncompleted_way_spellbook.registry.RecipeRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class DrainManaRecipe implements Recipe<SingleRecipeInput> {

    private final Ingredient input;

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getResult() {
        return result;
    }

    private final ItemStack result;
    private final int income;


    public DrainManaRecipe(Ingredient input, ItemStack result, Integer income) {
        this.input = input;
        this.result = result;
        this.income = income;
    }

    @Override
    public boolean matches(SingleRecipeInput recipeinput, Level level) {
        return input.test(recipeinput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.DRAIN_MANA_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.DRAIN_MANA.get();
    }

    public int getIncome() {
        return income;
    }
}
