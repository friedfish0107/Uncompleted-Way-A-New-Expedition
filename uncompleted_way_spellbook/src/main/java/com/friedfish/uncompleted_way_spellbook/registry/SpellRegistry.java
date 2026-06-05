package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.spell.blood.BloodAreaSpell;
import com.friedfish.uncompleted_way_spellbook.content.spell.functional.BlastSpell;
import com.friedfish.uncompleted_way_spellbook.content.spell.functional.OpenCraftingTableSpell;
import com.friedfish.uncompleted_way_spellbook.content.spell.mine.SummonMinecartSpell;
import com.friedfish.uncompleted_way_spellbook.content.spell.mine.ThrowStoneSpell;
import com.friedfish.uncompleted_way_spellbook.content.spell.recipe.*;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SpellRegistry {
    private static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY, UncompletedWaySpellbook.MODID);

    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }

    private static Supplier<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static final Supplier<AbstractSpell> BLOOD_AREA = registerSpell(new BloodAreaSpell());
    public static final Supplier<AbstractSpell> FILTER = registerSpell(new FilterSpell());
    public static final Supplier<AbstractSpell> CRYSTAL = registerSpell(new CrystalSpell());
    public static final Supplier<AbstractSpell> INFUSE_MANA = registerSpell(new InfuseManaSpell());
    public static final Supplier<AbstractSpell> DRAIN_MANA = registerSpell(new DrainManaSpell());
    public static final Supplier<AbstractSpell> OPEN_CRAFTING_TABLE = registerSpell(new OpenCraftingTableSpell());
    public static final Supplier<AbstractSpell> BLAST = registerSpell(new BlastSpell());
    public static final Supplier<AbstractSpell> THROW_STONE = registerSpell(new ThrowStoneSpell());
    public static final Supplier<AbstractSpell> SUMMON_MINECART = registerSpell(new SummonMinecartSpell());
}
