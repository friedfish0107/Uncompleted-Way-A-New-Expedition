package com.friedfish.uncompleted_way_spellbook.content.util;

import net.minecraft.resources.ResourceLocation;

public class ModCompatHelper {
    public static final String ISS="irons_spellbooks";
    public static ResourceLocation issID(String path){
        return ResourceLocation.fromNamespaceAndPath(ISS,path);
    }
}
