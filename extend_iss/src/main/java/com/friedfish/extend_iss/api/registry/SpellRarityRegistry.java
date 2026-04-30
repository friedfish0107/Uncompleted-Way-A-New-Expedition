package com.friedfish.extend_iss.api.registry;

import com.friedfish.extend_iss.ExtendISS;
import com.friedfish.extend_iss.api.spells.RegisterableSpellRarity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = ExtendISS.MODID)
public class SpellRarityRegistry {
    public static final ResourceKey<Registry<RegisterableSpellRarity>> SPELL_RARITY_KEY=ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ExtendISS.MODID,"spell_rarity"));
    public static final Registry<RegisterableSpellRarity>SPELL_RARITY_REGISTRY=
            new RegistryBuilder<>(SPELL_RARITY_KEY)
                    .maxId(256)
                    .sync(true)
                    .create();
    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event){
        event.register(SPELL_RARITY_REGISTRY);
    }
}
