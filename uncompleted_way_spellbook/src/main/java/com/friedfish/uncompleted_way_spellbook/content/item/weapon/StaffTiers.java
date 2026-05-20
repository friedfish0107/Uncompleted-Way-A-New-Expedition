package com.friedfish.uncompleted_way_spellbook.content.item.weapon;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.StaffTier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class StaffTiers {
    public static final StaffTier STICK=new StaffTier(2,-3,
            new AttributeContainer(AttributeRegistry.SPELL_POWER, .05,AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
    public static final StaffTier ZOMBIE_STAFF=new StaffTier(3,-3,
            new AttributeContainer(AttributeRegistry.SPELL_POWER, .12,AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    );
}
