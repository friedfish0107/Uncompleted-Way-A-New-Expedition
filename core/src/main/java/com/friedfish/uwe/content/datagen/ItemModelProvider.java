package com.friedfish.uwe.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uwe.UncompletedWayANewExpedition;
import com.friedfish.uwe.registry.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UncompletedWayANewExpedition.MODID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        for(ResourceLocation res: ItemRegistry.getBasicItemModelResourceLocation()){
            //basicItem(res);
        }
    }
}
