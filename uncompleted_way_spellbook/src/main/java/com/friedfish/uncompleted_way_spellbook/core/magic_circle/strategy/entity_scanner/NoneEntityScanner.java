package com.friedfish.uncompleted_way_spellbook.core.magic_circle.strategy.entity_scanner;

import com.friedfish.uncompleted_way_spellbook.api.magic_circle.MagicCircleContext;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.strategy.entity_scanner.IEntityScanner;
import net.minecraft.world.entity.Entity;

import java.util.List;

public class NoneEntityScanner implements IEntityScanner {

    @Override
    public List<Entity> scanEntities(MagicCircleContext ctx, List<Entity> entities) {
        return null;
    }

    @Override
    public List<Entity> removeEntities(MagicCircleContext ctx, List<Entity> entities) {
        return null;
    }

    @Override
    public boolean shouldScan(MagicCircleContext ctx, List<Entity> entities) {
        return false;
    }

    @Override
    public boolean shouldRemove(MagicCircleContext ctx, List<Entity> entities) {
        return false;
    }
}
