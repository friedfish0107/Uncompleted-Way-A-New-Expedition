package com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class InfuseManaRecipeSerializer implements RecipeSerializer<InfuseManaRecipe> {

    public static final MapCodec<InfuseManaRecipe> CODEC= RecordCodecBuilder.mapCodec(inst->inst.group(
            Ingredient.CODEC.fieldOf("input").forGetter(InfuseManaRecipe::getInput),
            ItemStack.CODEC.fieldOf("result").forGetter(InfuseManaRecipe::getResult),
            Codec.INT.fieldOf("cost").forGetter(InfuseManaRecipe::getCost)

    ).apply(inst,InfuseManaRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf,InfuseManaRecipe> STREAM_CODEC=
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,InfuseManaRecipe::getInput,
                    ItemStack.STREAM_CODEC,InfuseManaRecipe::getResult,
                    ByteBufCodecs.INT,InfuseManaRecipe::getCost,
                    InfuseManaRecipe::new
            );

    @Override
    public MapCodec<InfuseManaRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, InfuseManaRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
