package com.friedfish.uwe.content.client.ponder;

import com.friedfish.uwe.UncompletedWayANewExpedition;
import com.friedfish.uwe.content.client.ponder.scenes.InscriptionTableScenes;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

public class UWEPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return UncompletedWayANewExpedition.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(ItemRegistry.INSCRIPTION_TABLE_BLOCK_ITEM.getId())
                .addStoryBoard("inscription_table/get", InscriptionTableScenes::getInscriptionTable);
    }
}
