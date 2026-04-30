package com.friedfish.uwe.registry;

import com.friedfish.uwe.UncompletedWayANewExpedition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class DimensionRegistry {
    public static final ResourceKey<Level> CLOUD_WORLD=ResourceKey.create(Registries.DIMENSION, UncompletedWayANewExpedition.id("cloud_world"));
}
