package com.friedfish.uwe.registry;

import com.friedfish.uwe.UncompletedWayANewExpedition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class DimensionRegistry {
    public static final ResourceLocation CLOUD_WORLD=UncompletedWayANewExpedition.id("cloud_world");
    public static final ResourceKey<Level> CLOUD_WORLD_KEY=ResourceKey.create(Registries.DIMENSION, CLOUD_WORLD);
    public static final ResourceLocation CLOUD_WORLD_RENDERER=UncompletedWayANewExpedition.id("cloud_world_renderer");
}
