package com.friedfish.uncompleted_way_spellbook.content.entity.mobs.spell.anvil_strike;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;

public class AnvilStrikeRenderer extends EntityRenderer<AnvilStrikeProjectile> {
    public AnvilStrikeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(AnvilStrikeProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        BlockState blockState = Blocks.ANVIL.defaultBlockState();
        float scale = entity.getScale();

        poseStack.pushPose();
        poseStack.translate(-0.5*scale,0,-0.5*scale);
        poseStack.scale(scale,scale,scale);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(blockState, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.SOLID);
        poseStack.popPose();

    }

    @Override
    public ResourceLocation getTextureLocation(AnvilStrikeProjectile entity) {
        return UncompletedWaySpellbook.id("empty");
    }
}
