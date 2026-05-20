package com.friedfish.uncompleted_way_spellbook.core.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChanceBasedOutput {
    public static final StreamCodec<RegistryFriendlyByteBuf, ChanceBasedOutput> STREAM_CODEC =
            StreamCodec.of(
                    (buf, output) -> {
                        ByteBufCodecs.registry(Registries.ITEM).encode(buf, output.item);
                        ByteBufCodecs.INT.encode(buf, output.minCount);
                        ByteBufCodecs.INT.encode(buf, output.maxCount);
                        DataComponentPatch.STREAM_CODEC.encode(buf, output.patch);
                        ByteBufCodecs.FLOAT.encode(buf, output.chance);
                        ByteBufCodecs.FLOAT.encode(buf, output.extraCountFactor);
                        ByteBufCodecs.FLOAT.encode(buf, output.extraChanceFactor);
                    },
                    buf -> {
                        Item item = ByteBufCodecs.registry(Registries.ITEM).decode(buf);
                        int minCount = ByteBufCodecs.INT.decode(buf);
                        int maxCount = ByteBufCodecs.INT.decode(buf);
                        DataComponentPatch patch = DataComponentPatch.STREAM_CODEC.decode(buf);
                        float chance = ByteBufCodecs.FLOAT.decode(buf);
                        float extraCountFactor = ByteBufCodecs.FLOAT.decode(buf);
                        float extraChanceFactor = ByteBufCodecs.FLOAT.decode(buf);
                        return new ChanceBasedOutput(item, minCount, maxCount, patch, chance, extraCountFactor, extraChanceFactor);
                    }
            );

    private static final Codec<Either<Item, ResourceLocation>> ITEM_CODEC = Codec.either(
            BuiltInRegistries.ITEM.byNameCodec(),
            ResourceLocation.CODEC
    );

    public static final Codec<ChanceBasedOutput> CODEC = RecordCodecBuilder.create(i -> i.group(
            ITEM_CODEC.fieldOf("id").forGetter(s -> {
                if (s.datagenOutput != null)
                    return Either.right(s.datagenOutput);
                return Either.left(s.item);
            }),
            ExtraCodecs.intRange(1, 99).optionalFieldOf("min_count", 1).forGetter(s -> s.minCount),
            ExtraCodecs.intRange(1, 99).optionalFieldOf("max_count", 1).forGetter(s -> s.maxCount),
            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(s -> s.patch),
            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("chance", 1F).forGetter(s -> s.chance),
            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("extra_count_factor", 1F).forGetter(s -> s.extraCountFactor),
            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("extra_chance_factor", 1F).forGetter(s -> s.extraChanceFactor)
    ).apply(i, (item, minCount,maxCount, components, chance,extraCountFactor,extraChanceFactor) -> item.map(
            stack -> new ChanceBasedOutput(stack, minCount,maxCount, components, chance,extraCountFactor,extraChanceFactor),
            compat -> new ChanceBasedOutput(compat, minCount,maxCount, chance,extraCountFactor,extraChanceFactor)
    )));

    public Item getItem() {
        return item;
    }

    public int getMinCount() {
        return minCount;
    }
    public int getMaxCount() {
        return maxCount;
    }

    public DataComponentPatch getPatch() {
        return patch;
    }

    public float getChance() {
        return chance;
    }

    public ItemStack getItemStack(){
        return this.getItemStack(minCount);
    }

    public ItemStack getItemStack(int count){
        ItemStack result=new ItemStack(item,count);
        if(!patch.isEmpty())
            result.applyComponents(patch);
        return result;
    }

    public ItemStack rollItemStack(RandomSource randomSource,float chanceFactor,float extraCount){
        return randomSource.nextFloat()<getmodifiedChance(chanceFactor)?
                getItemStack(randomSource.nextIntBetweenInclusive(minCount,getmodifiedMaxCount(extraCount))):
                ItemStack.EMPTY;

    }

    public ItemStack rollItemStack(RandomSource randomSource){
        return rollItemStack(randomSource,1F,0);
    }

    private final Item item;
    private final int minCount;
    private final int maxCount;
    private final DataComponentPatch patch;
    private final float chance;
    private final float extraCountFactor;
    private final float extraChanceFactor;

    private ResourceLocation datagenOutput;

    public ChanceBasedOutput(Item item){
        this(item,1,1,DataComponentPatch.EMPTY,1,1,1);
    }

    public ChanceBasedOutput(Item item, int minCount,int maxCount, DataComponentPatch patch, float chance,float extraCountFactor,float extraChanceFactor) {
        this.item = item;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.patch = patch;
        this.chance = chance;
        this.extraCountFactor=extraCountFactor;
        this.extraChanceFactor=extraChanceFactor;
    }


    public ChanceBasedOutput(ResourceLocation item, int minCount, int maxCount, DataComponentPatch patch, float chance, float extraCountFactor, float extraChanceFactor) {
        this.item = Items.AIR;
        this.datagenOutput=item;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.patch = patch;
        this.chance = chance;
        this.extraCountFactor = extraCountFactor;
        this.extraChanceFactor = extraChanceFactor;
    }

    public ChanceBasedOutput(ResourceLocation item, Integer minCount,Integer maxCount, Float chance,Float extraCountFactor, Float extraChanceFactor) {
        this(item,minCount,maxCount, DataComponentPatch.EMPTY,chance,extraCountFactor,extraChanceFactor);
    }

    public ChanceBasedOutput(Item item, Integer minCount,Integer maxCount, Float chance) {
        this(item,minCount,maxCount, DataComponentPatch.EMPTY,chance,1,1);
    }
    public ChanceBasedOutput(Item item, int minCount, int maxCount, float chance, float extraCountFactor, float extraChanceFactor) {
        this(item,minCount,maxCount, DataComponentPatch.EMPTY,chance,extraCountFactor,extraChanceFactor);
    }

    public float getmodifiedChance(float chanceFactor) {
        return chance+chance*extraChanceFactor*chanceFactor;
    }

    public int getmodifiedMaxCount(float countFactor) {
        return (int)(maxCount+(float)maxCount*countFactor*extraCountFactor);
    }
}
