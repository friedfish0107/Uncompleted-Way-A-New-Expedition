package com.friedfish.uncompleted_way_spellbook.content.recipe.drain_mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class DrainManaRecipeSerializer implements RecipeSerializer<DrainManaRecipe> {

    public static final MapCodec<DrainManaRecipe> CODEC= RecordCodecBuilder.mapCodec(inst->inst.group(
            Ingredient.CODEC.fieldOf("input").forGetter(DrainManaRecipe::getInput),
            ItemStack.CODEC.fieldOf("result").forGetter(DrainManaRecipe::getResult),
            Codec.INT.fieldOf("income").forGetter(DrainManaRecipe::getIncome)

    ).apply(inst, DrainManaRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DrainManaRecipe> STREAM_CODEC=
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, DrainManaRecipe::getInput,
                    ItemStack.STREAM_CODEC, DrainManaRecipe::getResult,
                    ByteBufCodecs.INT, DrainManaRecipe::getIncome,
                    DrainManaRecipe::new
            );

    @Override
    public MapCodec<DrainManaRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, DrainManaRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
