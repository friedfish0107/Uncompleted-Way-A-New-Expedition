package com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter;

import com.friedfish.uncompleted_way_spellbook.core.recipe.ChanceBasedOutput;
import com.friedfish.uncompleted_way_spellbook.registry.RecipeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class SpellFilterRecipe implements Recipe<SpellFilterRecipeInput> {


    private final BlockState blockState;
    private final NonNullList<ChanceBasedOutput> outputs;
    private final int level;

    public NonNullList<ChanceBasedOutput> getOutputs() {
        return NonNullList.copyOf(outputs);
    }

    public int getOutputLength(){return outputs.size();}

    public SpellFilterRecipe(BlockState blockState, List<ChanceBasedOutput> outputs, Integer level) {
        this(blockState,NonNullList.copyOf(outputs),level);
    }

    public SpellFilterRecipe(BlockState blockState, NonNullList<ChanceBasedOutput> outputs, Integer level) {
        this.blockState = blockState;
        this.outputs = outputs;
        this.level = level;
    }

    @Override
    public boolean matches(SpellFilterRecipeInput input, Level level) {
        return input.blockState()==blockState &&
                input.level()>=this.level;
    }

    @Override
    public ItemStack assemble(SpellFilterRecipeInput input, HolderLookup.Provider registries) {
        return outputs.getFirst().getItemStack();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return outputs.getFirst().getItemStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.FILTER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.FILTER.get();
    }

    public BlockState getInputState() {
        return blockState;
    }

    public Integer getLevel() {
        return level;
    }

    public ItemStack getInput(){
        return blockState.getBlock().asItem().getDefaultInstance();
    }

    public List<ItemStack> getResults(RandomSource randomSource, float chanceFactor, float extraCount){
        List<ItemStack> result=new ArrayList<>();
        outputs.forEach(e->result.add(e.rollItemStack(randomSource,chanceFactor,extraCount)));
        return result;
    }

    public List<ItemStack> getResults(RandomSource randomSource){
        return getResults(randomSource,1,0);
    }

    public float getChanceFactor(Player player) {
        return player.getLuck();
    }

    public float getCountFactor(Player player) {
        return player.getLuck();
    }
}
