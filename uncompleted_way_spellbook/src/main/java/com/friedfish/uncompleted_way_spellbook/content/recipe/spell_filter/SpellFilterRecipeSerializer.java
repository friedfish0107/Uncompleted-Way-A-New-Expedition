package com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter;

import com.friedfish.uncompleted_way_spellbook.core.recipe.ChanceBasedOutput;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SpellFilterRecipeSerializer implements RecipeSerializer<SpellFilterRecipe> {

    public static final MapCodec<SpellFilterRecipe> CODEC= RecordCodecBuilder.mapCodec(inst->inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(SpellFilterRecipe::getInputState),
            ChanceBasedOutput.CODEC.listOf().fieldOf("outputs").forGetter(SpellFilterRecipe::getOutputs),
            Codec.INT.fieldOf("level").forGetter(SpellFilterRecipe::getLevel)

    ).apply(inst,SpellFilterRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf,SpellFilterRecipe> STREAM_CODEC=
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY),SpellFilterRecipe::getInputState,
                    ChanceBasedOutput.STREAM_CODEC.apply(ByteBufCodecs.list()),SpellFilterRecipe::getOutputs,
                    ByteBufCodecs.INT,SpellFilterRecipe::getLevel,
                    SpellFilterRecipe::new
            );

    @Override
    public MapCodec<SpellFilterRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SpellFilterRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
