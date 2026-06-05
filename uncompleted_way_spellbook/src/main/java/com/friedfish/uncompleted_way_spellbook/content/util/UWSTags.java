package com.friedfish.uncompleted_way_spellbook.content.util;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UWSTags {

    public static final TagKey<Item> RECIPE_FOCUS = ItemTags.create(UncompletedWaySpellbook.id("recipe_focus"));
    public static final TagKey<Item> MINE_FOCUS = ItemTags.create(UncompletedWaySpellbook.id("recipe_focus"));
}
