package com.friedfish.uncompleted_way_spellbook.content.magic_circle;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.MagicCircleContext;
import com.friedfish.uncompleted_way_spellbook.core.magic_circle.AbstractMagicCircle;
import com.friedfish.uncompleted_way_spellbook.core.magic_circle.strategy.entity_scanner.PlayerEntityScanner;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class BloodArea extends AbstractMagicCircle {
    private static final ResourceLocation magicCircleID= UncompletedWaySpellbook.id("blood_area");
    private static final double RADIUS=16;
    private static final double HEIGHT=16;

    public BloodArea(MagicCircleContext ctx) {
        super(ctx);
        this.getContext().setRadius(RADIUS);
        this.getContext().setHeight(HEIGHT);
        setEntityScanner(new PlayerEntityScanner());
    }

    @Override
    public void onEntityEnter(Entity entity) {
        super.onEntityEnter(entity);
        if(entity instanceof Player player) {
            player.displayClientMessage(Component.literal("进入法阵范围"), true);
        }
    }

    @Override
    public ResourceLocation getMagicCircleResource() {
        return magicCircleID;
    }
}
