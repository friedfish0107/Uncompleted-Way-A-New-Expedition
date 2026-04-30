package com.friedfish.uncompleted_way_spellbook.content.entity.bloodAreaCircle;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.core.util.MagicCircleRenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class BloodAreaCircleRender extends EntityRenderer<BloodAreaCircleEntity> {

    private static String path=null;

    public BloodAreaCircleRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BloodAreaCircleEntity entity) {
        return null;
    }

    public ResourceLocation getMainTextureLocation(BloodAreaCircleEntity entity){
        return UncompletedWaySpellbook.id("textures/entity/magic_circle/blood_area/");
    }

    public ResourceLocation getBorderTextureLocation(BloodAreaCircleEntity entity){
        return getMainTextureLocation(entity).withSuffix("border.png");
    }


    @Override
    public void render(BloodAreaCircleEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        this.renderNameTag(p_entity, Component.literal("test"),poseStack,bufferSource,packedLight,partialTick);

        Vec3 pos=p_entity.getPosition(partialTick);
        float radius=p_entity.getRadius();
        ResourceLocation borderTextureLocation = getBorderTextureLocation(p_entity);
        long gameTime=p_entity.level().getGameTime();


        MagicCircleRenderHelper.renderSquareBorder(pos,radius,borderTextureLocation,poseStack,bufferSource,packedLight,partialTick,gameTime);

        ResourceLocation iconTextureLocation= getIconTextureLocation(p_entity);

        float scale=6;
        MagicCircleRenderHelper.renderIcon(pos,scale,iconTextureLocation,poseStack,bufferSource,packedLight,this.entityRenderDispatcher.cameraOrientation());

    }

    private ResourceLocation getIconTextureLocation(BloodAreaCircleEntity entity) {
        return getMainTextureLocation(entity).withSuffix("blood_moon.png");
    }
}
