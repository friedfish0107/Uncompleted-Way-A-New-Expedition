package com.friedfish.uncompleted_way_spellbook.api.magic_circle.strategy.entity_scanner;

import com.friedfish.uncompleted_way_spellbook.api.magic_circle.MagicCircleContext;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface IEntityScanner {
    List<Entity> scanEntities(MagicCircleContext ctx, List<Entity> entities);
    List<Entity> removeEntities(MagicCircleContext ctx, List<Entity> entities);
    boolean shouldScan(MagicCircleContext ctx,List<Entity> entities);
    boolean shouldRemove(MagicCircleContext ctx, List<Entity> entities);
}
