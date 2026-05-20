package com.friedfish.uncompleted_way_spellbook.content.compat.jei.category;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.compat.jei.*;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipe;
import com.friedfish.uncompleted_way_spellbook.content.spell.recipe.FilterSpell;
import com.friedfish.uncompleted_way_spellbook.content.util.GuiGraphicsHelper;
import com.friedfish.uncompleted_way_spellbook.core.recipe.ChanceBasedOutput;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellFilterRecipeCategory implements IRecipeCategory<SpellFilterRecipe> {
    public static final RecipeType<SpellFilterRecipe> SPELL_FILTER_RECIPE_TYPE=RecipeType.create(UncompletedWaySpellbook.MODID,"filter",SpellFilterRecipe.class);
    private final IDrawable icon;
    private static final int WIDTH = 160;
    private static final int HEIGHT = 80;
    private static final int SLOT_WIDTH = 20;
    private static final int SLOT_HEIGHT = 20;
    private static final int ARRAY_OFFSET = -SLOT_WIDTH;
    private static final int SLOT_PER_LINE = 4;
    private static final int BASE_X = WIDTH/2+11+SLOT_PER_LINE/2*SLOT_WIDTH+ARRAY_OFFSET/2;
    private static final int BASE_Y = HEIGHT/2;

    public SpellFilterRecipeCategory(IGuiHelper guiHelper) {
        this.icon = new ScrollWithSpellIcon(new FilterSpell());
    }

    @Override
    public RecipeType<SpellFilterRecipe> getRecipeType() {
        return SPELL_FILTER_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("category."+UncompletedWaySpellbook.MODID+".filter");
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    /**
     * 根据槽位索引和总数计算该槽位的 X 坐标（支持每行自动居中）
     * @param slot  槽位索引（从 0 开始）
     * @param size  输出槽总数
     * @return      该槽位的 X 坐标
     */
    private int computeX(int slot, int size) {
        int line = slot / SLOT_PER_LINE;                    // 当前行号
        int col = slot % SLOT_PER_LINE;                     // 当前列号
        int rowCount = getRowItemCount(line, size);         // 当前行的实际槽位数
        int rowOffset = -rowCount * SLOT_WIDTH / 2;          // 当前行的水平居中偏移
        return BASE_X + col * SLOT_WIDTH + rowOffset;
    }

    /**
     * 根据槽位索引和总数计算该槽位的 Y 坐标（支持整体垂直居中）
     * @param slot  槽位索引（从 0 开始）
     * @param size  输出槽总数
     * @return      该槽位的 Y 坐标
     */
    private int computeY(int slot, int size) {
        int line = slot / SLOT_PER_LINE;                    // 当前行号
        int totalLines = (size + SLOT_PER_LINE - 1) / SLOT_PER_LINE; // 总行数（向上取整）
        int verticalOffset = -totalLines * SLOT_HEIGHT / 2;   // 整体垂直居中偏移
        return BASE_Y + line * SLOT_HEIGHT + verticalOffset;
    }

    /**
     * 获取指定行的实际槽位数量（最后一行可能不满）
     */
    private int getRowItemCount(int line, int totalSize) {
        int totalLines = (totalSize + SLOT_PER_LINE - 1) / SLOT_PER_LINE;
        if (line < totalLines - 1) {
            return SLOT_PER_LINE;
        } else {
            int lastRowCount = totalSize % SLOT_PER_LINE;
            return lastRowCount == 0 ? SLOT_PER_LINE : lastRowCount;
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpellFilterRecipe recipe, IFocusGroup focuses) {
        ItemStack input = recipe.getInput();
        Player player= Minecraft.getInstance().player;
        float chanceFactor=recipe.getChanceFactor(player);
        float countFactor=recipe.getCountFactor(player);
        List<ChanceBasedOutput> outputs = recipe.getOutputs();

        // 输入槽（固定位置）
        builder.addInputSlot(WIDTH/2/2-22,HEIGHT/2-8)
                .addItemStack(input)
                .setBackground(new JeiStyleBigSlotBackground(),0,0)
                .setSlotName("input");

        // 动态生成的输出槽（使用优化后的坐标计算）
        int n = recipe.getOutputLength();
        for (int i = 0; i < n; i++) {
            int x = computeX(i, n);
            int y = computeY(i, n);
            ChanceBasedOutput output=outputs.get(i);
            int minCount = output.getMinCount();
            int maxCount = output.getMaxCount();
            float chance = output.getChance();
            int modifiedMaxCount=output.getmodifiedMaxCount(countFactor);
            float modifiedChance=output.getmodifiedChance(chanceFactor);

            builder.addOutputSlot(x, y)
                    .setBackground(new ChanceSlotBackground(),0,0)
                    .setOverlay(new ChanceSlotOverlay(minCount, maxCount, chance),0,0)
                    .addItemStack(output.getItemStack(1))
                    .addRichTooltipCallback(new ChanceBasedResultTooltipCallback(minCount, maxCount, chance,modifiedMaxCount,modifiedChance));
        }
    }

    @Override
    public void draw(SpellFilterRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {

        GuiGraphicsHelper.renderArray(guiGraphics,WIDTH/2-11+ARRAY_OFFSET,HEIGHT/2-7);

        Lighting.setupForEntityInInventory();

        Minecraft minecraft = Minecraft.getInstance();
        ItemStack stack = recipe.getInput();
        PoseStack poseStack = guiGraphics.pose();
        BakedModel bakedmodel = minecraft.getItemRenderer().getModel(stack,null,null,0);

        poseStack.pushPose();

        poseStack.translate(WIDTH/2/2-16, HEIGHT/2, 150);
        poseStack.scale(40,-40,40);
        poseStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        //poseStack.mulPose(Axis.ZP.rotationDegrees(17f));

        //poseStack.mulPose(Axis.YP.rotationDegrees(22.5f));


        minecraft
                .getItemRenderer()
                .render(stack, ItemDisplayContext.GUI, false, poseStack, guiGraphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);

        poseStack.popPose();
    }
}
