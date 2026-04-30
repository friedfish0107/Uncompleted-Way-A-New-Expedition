package com.friedfish.uncompleted_way_spellbook.core.magic_circle;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.IMagicCircle;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.MagicCircleContext;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.strategy.entity_scanner.IEntityScanner;
import com.friedfish.uncompleted_way_spellbook.misc.LogFlags;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractMagicCircle implements IMagicCircle {

    private MagicCircleContext context;

    public void setEntityScanner(IEntityScanner entityScanner) {
        this.entityScanner = entityScanner;
    }

    private IEntityScanner entityScanner;
    private List<Entity> entities;

    private String magicCircleId=null;

    public AbstractMagicCircle(MagicCircleContext context) {
        this.context = context;
        this.entityScanner = null;
        this.entities=new ArrayList<>();
    }
    //private IBlockScanner blockScanner;
    //...


    @Override
    public MagicCircleContext getContext() {
        return context;
    }

    public void tick(){
        baseTick(getContext());
    }


    @Override
    public void onEntityEnter(Entity entity) {

    }

    @Override
    public void onEntityExit(Entity entity) {

    }

    private void baseTick(MagicCircleContext ctx){
        if(ctx.getLevel().isClientSide)return;

        if(entityScanner!=null) {

            List<Entity>new_entities=new ArrayList<>();
            if(entityScanner.shouldScan(ctx, entities)){
                if(LogFlags.MAGIC_CIRCLE_DEBUG)
                    UncompletedWaySpellbook.LOGGER.debug("scanning");
                new_entities = entityScanner.scanEntities(ctx, entities);
                for (Entity entity : new_entities) {
                    if (!entities.contains(entity)) {
                        onEntityEnter(entity);
                        if (LogFlags.MAGIC_CIRCLE_DEBUG)
                            UncompletedWaySpellbook.LOGGER.debug("onEntityEnter");
                    }
                }
                entities = new_entities;
            }
            if(entityScanner.shouldRemove(ctx,new_entities)){
                if(LogFlags.MAGIC_CIRCLE_DEBUG)
                    UncompletedWaySpellbook.LOGGER.debug("removing");
                new_entities = entityScanner.removeEntities(ctx,new_entities);
                for (Entity entity : entities) {
                    if (!new_entities.contains(entity)) {
                        onEntityExit(entity);
                        if(LogFlags.MAGIC_CIRCLE_DEBUG)
                            UncompletedWaySpellbook.LOGGER.debug("onEntityExit");
                    }
                }
                entities = new_entities;
            }
            if(LogFlags.MAGIC_CIRCLE_DEBUG)
                UncompletedWaySpellbook.LOGGER.debug("entity{},new entity{}",entities.toString(),new_entities.toString());


        }
        //FIXME:分离旧实体列表与新实体列表，保证增加和删除都可以被发现
        ctx.addTickCount();
        if(ctx.isExpired()) {
            ctx.setRemoved(true);
        }
    }

    @Override
    public String getMagicCircleId(){
        if(magicCircleId==null){
            var resourceLocation = Objects.requireNonNull(getMagicCircleResource());
            magicCircleId = resourceLocation.getPath().intern();
        }
        return magicCircleId;
    }
}