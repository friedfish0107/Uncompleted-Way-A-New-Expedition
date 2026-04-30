package com.friedfish.uncompleted_way_spellbook.api.magic_circle;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public interface IMagicCircle {
    MagicCircleContext getContext();
    void tick();
    void onEntityEnter(Entity entity);
    void onEntityExit(Entity entity);

    default ResourceLocation getTextureMainResource(){
        return ResourceLocation.fromNamespaceAndPath(getMagicCircleResource().getNamespace(),"textures/entity/magic_circle/"+getMagicCircleId());
    }

    ResourceLocation getMagicCircleResource();

    default String getMagicCircleId(){
        return getMagicCircleResource().getPath().intern();
    };
}
