package com.friedfish.uwe.content.datagen;

import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class LootTableProviders {
    static class Entity extends EntityLootSubProvider{
        List<EntityType<?>> knownEntityTypes = new ArrayList<>();

        public Entity(HolderLookup.Provider pRegistries) {
            super(FeatureFlags.REGISTRY.allFlags(), pRegistries);
        }

        @Override
        public void generate() {
            add(EntityType.ZOMBIE,
                    LootTable.lootTable()
                            .withPool(
                                    LootPool.lootPool().add(LootItem.lootTableItem(Items.EMERALD))
                            )
            );
            //this.add();
        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return knownEntityTypes.stream();
        }

        @Override
        protected void add(EntityType<?> entityType, LootTable.Builder builder) {
            knownEntityTypes.add(entityType);
            super.add(entityType, builder);
        }
    }
}
