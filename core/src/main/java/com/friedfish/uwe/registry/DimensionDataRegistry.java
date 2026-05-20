package com.friedfish.uwe.registry;

import com.friedfish.uwe.UncompletedWayANewExpedition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;

import java.util.List;
import java.util.OptionalLong;

public class DimensionDataRegistry {
    public static final ResourceKey<DimensionType> CLOUD_WORLD_TYPE=ResourceKey.create(Registries.DIMENSION_TYPE, UncompletedWayANewExpedition.id("cloud_world_type"));
    public static final ResourceKey<NoiseGeneratorSettings> CLOUD_WORLD_NOISE=ResourceKey.create(Registries.NOISE_SETTINGS, UncompletedWayANewExpedition.id("cloud_world_type"));
    public static final ResourceKey<LevelStem> CLOUD_WORLD_LEVEL_STEM=ResourceKey.create(Registries.LEVEL_STEM, DimensionRegistry.CLOUD_WORLD);


    public static void bootstrapType(BootstrapContext<DimensionType> context){
        context.register(CLOUD_WORLD_TYPE,new DimensionType(
                OptionalLong.of(1000L),
                true,
                false,
                false,
                true,
                1,
                true,
                false,
                0,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                DimensionRegistry.CLOUD_WORLD_RENDERER,
                0.01f,
                new DimensionType.MonsterSettings(false,false, UniformInt.of(0,7),7)
        ));
    }
    public static void bootstrapNoise(BootstrapContext<NoiseGeneratorSettings> context){
        context.register(CLOUD_WORLD_NOISE,new NoiseGeneratorSettings(
                new NoiseSettings(
                        0,
                        256,
                        2,
                        2
                ),
                Blocks.AIR.defaultBlockState(),
                Blocks.AIR.defaultBlockState(),
                new NoiseRouter(
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero(),
                        DensityFunctions.zero()
                ),
                SurfaceRules.state(Blocks.AIR.defaultBlockState()),
                List.of(),
                0,
                false,
                false,
                false,
                false
        ));
    }
    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        Holder.Reference<Biome> biome = context.lookup(Registries.BIOME).getOrThrow(Biomes.PLAINS);
        Holder<DimensionType> dimensionType=context.lookup(Registries.DIMENSION_TYPE).getOrThrow(CLOUD_WORLD_TYPE);
        Holder<NoiseGeneratorSettings> noiseGeneratorSettings=context.lookup(Registries.NOISE_SETTINGS).getOrThrow(CLOUD_WORLD_NOISE);
        context.register(CLOUD_WORLD_LEVEL_STEM,new LevelStem(
                dimensionType,
                new NoiseBasedChunkGenerator(new FixedBiomeSource(biome), noiseGeneratorSettings)
        ));
    }
}
