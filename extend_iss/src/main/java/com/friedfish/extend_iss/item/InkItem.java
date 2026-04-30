package com.friedfish.extend_iss.item;

import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class InkItem extends Item {
    private final Holder<Fluid> fluid;

    public InkItem(/*SpellRarity rarity,*/ Holder<Fluid> fluid) {
        super(ItemPropertiesHelper.material());
        //this.rarity = rarity;
        this.fluid = fluid;
    }
    public Holder<Fluid> fluid() {
        return fluid;
    }
}
