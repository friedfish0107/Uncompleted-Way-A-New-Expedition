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
    public static final StreamCodec<RegistryFriendlyByteBuf,ChanceBasedOutput> STREAM_CODEC=StreamCodec.composite(
            ByteBufCodecs.registry(Registries.ITEM),i->i.item,
            ByteBufCodecs.INT,i->i.minCount,
            ByteBufCodecs.INT,i->i.maxCount,
            DataComponentPatch.STREAM_CODEC,i->i.patch,
            ByteBufCodecs.FLOAT,i->i.chance,
            ChanceBasedOutput::new
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
            ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("chance", 1F).forGetter(s -> s.chance)
    ).apply(i, (item, minCount,maxCount, components, chance) -> item.map(
            stack -> new ChanceBasedOutput(stack, minCount,maxCount, components, chance),
            compat -> new ChanceBasedOutput(compat, minCount,maxCount, chance)
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

    public ItemStack rollItemStack(RandomSource randomSource,float chanceFactor,int extraCount){
        return randomSource.nextFloat()<chance*chanceFactor?
                getItemStack(randomSource.nextIntBetweenInclusive(minCount,maxCount+extraCount)):
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

    private ResourceLocation datagenOutput;

    public ChanceBasedOutput(Item item){
        this(item,1,1,DataComponentPatch.EMPTY,1);
    }

    public ChanceBasedOutput(Item item, int minCount,int maxCount, DataComponentPatch patch, float chance) {
        this.item = item;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.patch = patch;
        this.chance = chance;
    }


    public ChanceBasedOutput(ResourceLocation item, int minCount,int maxCount, DataComponentPatch patch, float chance) {
        this.item = Items.AIR;
        this.datagenOutput=item;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.patch = patch;
        this.chance = chance;
    }

    public ChanceBasedOutput(ResourceLocation item, Integer minCount,Integer maxCount, Float chance) {
        this(item,minCount,maxCount, DataComponentPatch.EMPTY,chance);
    }

    public ChanceBasedOutput(Item item, Integer minCount,Integer maxCount, Float chance) {
        this(item,minCount,maxCount, DataComponentPatch.EMPTY,chance);
    }

}
