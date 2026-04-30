package com.friedfish.extend_iss.mixin;

import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.neoforged.fml.common.asm.enumextension.IExtensibleEnum;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SpellRarity.class, remap = false)
public abstract class SpellRarityMixin implements IExtensibleEnum {

}