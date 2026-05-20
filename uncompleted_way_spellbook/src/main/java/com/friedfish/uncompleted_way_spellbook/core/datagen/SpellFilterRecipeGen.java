package com.friedfish.uncompleted_way_spellbook.core.datagen;

import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipe;
import com.friedfish.uncompleted_way_spellbook.core.recipe.ChanceBasedOutput;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpellFilterRecipeGen extends SimpleRecipeBuilder {
    private final BlockState blockState;
    private final NonNullList<ChanceBasedOutput> outputs;
    private final int level;

    // 私有构造函数，强制通过静态工厂创建
    private SpellFilterRecipeGen(BlockState blockState, int level, List<ChanceBasedOutput> outputs) {
        this.blockState = blockState;
        this.level = level;
        this.outputs = NonNullList.copyOf(outputs);
    }

    /** 创建一个新的 Builder 实例 */
    public static Builder create(BlockState blockState, int level) {
        return new Builder(blockState, level);
    }

    /** 便捷方法：无输出时创建并直接保存（可用于调试） */
    public static Builder create(BlockState blockState) {
        return new Builder(blockState, 0);
    }

    @Override
    public Item getResult() {
        return outputs.isEmpty() ? Items.AIR : outputs.getFirst().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        SpellFilterRecipe recipe = new SpellFilterRecipe(blockState, outputs, level);
        recipeOutput.accept(id.withPrefix("filter_"), recipe, advancement.build(id.withPrefix("recipes/")));
    }

    // ---- Builder ----
    public static class Builder {
        private final BlockState blockState;
        private final int level;
        private final List<ChanceBasedOutput> outputs = new ArrayList<>();
        private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        private String group;

        private Builder(BlockState blockState, int level) {
            this.blockState = blockState;
            this.level = level;
        }

        public Builder addOutput(Item item,int minCount,int maxCount,float chance){
            return addOutput(new ChanceBasedOutput(item,minCount,maxCount,chance));
        }

        public Builder addOutput(Item item,int minCount,int maxCount,float chance,float extraCountFactor, float extraChanceFactor){
            return addOutput(new ChanceBasedOutput(item,minCount,maxCount,chance,extraCountFactor,extraChanceFactor));
        }

        /** 添加一个输出 */
        public Builder addOutput(ChanceBasedOutput output) {
            this.outputs.add(output);
            return this;
        }

        /** 批量添加输出 */
        public Builder addOutputs(ChanceBasedOutput... outputs) {
            for (ChanceBasedOutput o : outputs) {
                this.outputs.add(o);
            }
            return this;
        }

        /** 直接添加一个无附加数据的物品（默认概率 1，数量 1） */
        public Builder addSimpleOutput(Item item) {
            return addOutput(new ChanceBasedOutput(item));
        }

        /** 设置配方解锁条件（会合并到最终配方） */
        public Builder unlockedBy(String name, Criterion<?> criterion) {
            this.criteria.put(name, criterion);
            return this;
        }

        /** 设置配方分组 */
        public Builder group(String group) {
            this.group = group;
            return this;
        }

        /** 构建并保存（自动生成 ID） */
        public void save(RecipeOutput output) {
            build().save(output,this.blockState.getBlockHolder().getRegisteredName());
        }

        /** 构建并保存（使用指定 ID） */
        public void save(RecipeOutput output, ResourceLocation id) {
            build().save(output, id);
        }

        /** 构建 SpellFilterRecipeGen 实例（可继续使用父类的 save 等） */
        private SpellFilterRecipeGen build() {
            SpellFilterRecipeGen gen = new SpellFilterRecipeGen(blockState, level, outputs);
            if (group != null) gen.group(group);
            criteria.forEach(gen::unlockedBy);
            return gen;
        }
    }
}