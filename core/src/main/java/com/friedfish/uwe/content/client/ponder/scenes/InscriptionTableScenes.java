package com.friedfish.uwe.content.client.ponder.scenes;

import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.ParrotElement;
import net.createmod.ponder.api.element.ParrotPose;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.createmod.ponder.api.scene.WorldInstructions;
import net.createmod.ponder.foundation.PonderScene;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;

public class InscriptionTableScenes {
    public static void getInscriptionTable(SceneBuilder builder, SceneBuildingUtil util) {
        SceneBuilder scene = builder.getScene().builder();
        WorldInstructions world=scene.world();

        scene.title("get_inscription","获取法术抄写台");

        scene.configureBasePlate(0, 1, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);

        world.setBlocks(util.select().fromTo(2, 1, 2,2, 1, 1), Blocks.OAK_PLANKS.defaultBlockState(),false);

        world.setBlock(new BlockPos(2,2,2), BlockRegistry.BOOK_STACK.get().defaultBlockState(),false);



        world.showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.idle(1);
        world.showSection(util.select().position(2, 1, 1), Direction.DOWN);
        scene.idle(5);
        world.showSection(util.select().position(2, 2, 2), Direction.DOWN);

        scene.addKeyframe();
        scene.idle(30);

        scene.overlay().showControls(util.vector().of(2.5, 3, 2.5), Pointing.DOWN,20) // time,pos,showdirection
                .rightClick()
                .withItem(ItemRegistry.ARCANE_ESSENCE.get().getDefaultInstance())
                .whileSneaking();

        scene.idle(5);
        scene.overlay().showOutline(PonderPalette.WHITE, new Object(), util.select().position(2, 2, 2), 20);
        scene.idle(20);

        // world.hideSection(util.select().position(2, 2, 2), Facing.WEST);
        // world.hideSection(util.select().fromTo(2, 1, 2, 2, 1, 1), Facing.NONE);

        // scene.idle(15);
        world.modifyBlocks(util.select().position(2, 2, 2), blockState->Blocks.AIR.defaultBlockState(),true);

        // 获取抄写台方块，并设置 facing 和 type 属性（根据实际属性名称调整）
        BlockState inscriptionTableRight = BlockRegistry.INSCRIPTION_TABLE_BLOCK.get().defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
                .setValue(BlockStateProperties.CHEST_TYPE, ChestType.RIGHT);
        world.modifyBlocks(util.select().position(2, 1, 2), blockState->inscriptionTableRight,true);

        BlockState inscriptionTableLeft = BlockRegistry.INSCRIPTION_TABLE_BLOCK.get().defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
                .setValue(BlockStateProperties.CHEST_TYPE, ChestType.LEFT);
        world.modifyBlocks(util.select().position(2, 1, 1), blockState->inscriptionTableLeft,true);
        // world.showSection(util.select().fromTo(2, 1, 2, 2, 1, 1), Facing.WEST);
        scene.addKeyframe();
        scene.idle(30);
    }

}
