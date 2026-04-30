package com.friedfish.uncompleted_way_spellbook.core.magic_circle.strategy.entity_scanner;

import com.friedfish.uncompleted_way_spellbook.api.magic_circle.MagicCircleContext;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.strategy.entity_scanner.IEntityScanner;
import com.friedfish.uncompleted_way_spellbook.misc.LogFlags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntityScanner implements IEntityScanner {

    @Override
    public List<Entity> scanEntities(MagicCircleContext ctx, List<Entity> entities) {
        List<? extends Entity> PlayerList = ctx.getLevel().getEntities(EntityType.PLAYER, ctx.getBoundBox(), Entity::isAlive);
        LogFlags.onceLog(ctx.getBoundBox().toString());
        return new ArrayList<>(PlayerList);
    }

    @Override
    public List<Entity> removeEntities(MagicCircleContext ctx, List<Entity> entities) {
        entities.removeIf(e->
                Mth.abs((float) (e.getX()-ctx.getPos().x))>ctx.getRadius()||
                Mth.abs((float) (e.getZ()-ctx.getPos().z))>ctx.getRadius())
        ;
        return entities;
    }

    @Override
    public boolean shouldScan(MagicCircleContext ctx, List<Entity> entities) {
        return ctx.getTickCount()%10==1;
    }

    @Override
    public boolean shouldRemove(MagicCircleContext ctx, List<Entity> entities) {
        return ctx.getTickCount()%10==1;
    }
}
