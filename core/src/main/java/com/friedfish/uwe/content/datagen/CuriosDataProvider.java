package com.friedfish.uwe.content.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class CuriosDataProvider extends top.theillusivec4.curios.api.CuriosDataProvider {

    public CuriosDataProvider(String modId, PackOutput output,
                              ExistingFileHelper fileHelper,
                              CompletableFuture<HolderLookup.Provider> registries) {
        super(modId, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {

    }
}
