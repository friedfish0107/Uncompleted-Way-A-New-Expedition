package com.friedfish.uwe.consts;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTagConsts {
    private static final TagKey<Item> SPELL_BOOK_TAG=TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios","spellbook"));
    private static final TagKey<Item> HEAD_TAG=TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios","spellbook"));
}
