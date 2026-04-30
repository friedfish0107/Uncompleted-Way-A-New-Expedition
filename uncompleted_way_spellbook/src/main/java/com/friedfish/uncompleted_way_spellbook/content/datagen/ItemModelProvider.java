package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UncompletedWaySpellbook.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // 1. 创建启动后的模型
        ModelFile debugEnabled = getBuilder("item/debug_enabled")
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", modLoc("item/debug_on"));

        // 2. 创建默认模型(加一个条件覆写)
        getBuilder("item/debug")
                .parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", modLoc("item/debug_off"))
                .override()
                .predicate(UncompletedWaySpellbook.id("enabled"), 1.0F)
                .model(debugEnabled)
                .end();

    }
}
