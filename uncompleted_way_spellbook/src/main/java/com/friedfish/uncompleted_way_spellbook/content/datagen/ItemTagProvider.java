package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider {
    private static final TagKey<Item> SPELL_BOOK_TAG=TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios","spellbook"));
    private static final TagKey<Item> HEAD_TAG=TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios","spellbook"));


    public ItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider,blockTags, UncompletedWaySpellbook.MODID,existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(SPELL_BOOK_TAG)
                .add(ItemRegistry.MAGIC_PAPER.get())
                .add(ItemRegistry.ENCHANT_SPELL_BOOK.get());
        tag(CuriosTags.HEAD)
                .add(ItemRegistry.OLD_SKULL_ZOMBIE.get());
    }
}
