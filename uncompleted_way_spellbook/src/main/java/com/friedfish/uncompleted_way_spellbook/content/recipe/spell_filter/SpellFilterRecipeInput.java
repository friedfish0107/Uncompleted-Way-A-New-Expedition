package com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;

public record SpellFilterRecipeInput(BlockState blockState,int level) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if (index != 0) throw new IllegalArgumentException("No item for index " + index);
        return this.blockState.getBlock().asItem().getDefaultInstance();
    }

    @Override
    public int size() {
        return 1;
    }
}
