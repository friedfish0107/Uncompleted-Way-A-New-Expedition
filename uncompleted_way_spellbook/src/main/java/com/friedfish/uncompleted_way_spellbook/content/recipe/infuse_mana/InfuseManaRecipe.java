package com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana;

import com.friedfish.uncompleted_way_spellbook.registry.RecipeRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class InfuseManaRecipe implements Recipe<SingleRecipeInput> {

    private final Ingredient input;

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getResult() {
        return result;
    }

    private final ItemStack result;
    private final int cost;


    public InfuseManaRecipe(Ingredient input, ItemStack result, Integer cost) {
        this.input = input;
        this.result = result;
        this.cost = cost;
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
        return RecipeRegistry.INFUSE_MANA_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.INFUSE_MANA.get();
    }

    public boolean canCraft(ItemStack itemStack, Player player) {
        return input.test(itemStack)&&getArcaneEssence(player)>=cost;
    }

    public int getArcaneEssence(Player player){
        return player.getInventory().countItem(ItemRegistry.ARCANE_ESSENCE.get());
    }

    public int getCost() {
        return cost;
    }
}
