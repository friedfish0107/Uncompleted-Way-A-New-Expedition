package com.friedfish.uwe.modification.recipe.special;

import io.redspace.ironsspellbooks.block.inscription_table.InscriptionTableBlock;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class InscriptionTableWorldCraft {
    @SubscribeEvent
    public static void onPlayerMakeInscriptionTable(PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide().isClient()) return;
        if (!event.getItemStack().is(ItemRegistry.ARCANE_ESSENCE)) return;
        if (!event.getLevel().getBlockState(event.getPos()).is(BlockRegistry.BOOK_STACK)) return;

        Level level = event.getLevel();
        BlockPos clickedPos = event.getPos();
        BlockPos basePos = clickedPos.below();
        Player player=event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack itemStack=player.getItemInHand(hand);

        // 检查下方必须是木板
        if (!level.getBlockState(basePos).is(BlockTags.PLANKS)) return;

        // 定义方向映射：相邻方向、对应的放置朝向、左右区块的归属
        Direction[] directions = {Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH};
        Direction[] facings = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (int i = 0; i < directions.length; i++) {
            Direction adjacentDir = directions[i];
            Direction facing = facings[i];
            BlockPos adjacentPos = basePos.relative(adjacentDir);

            // 检查相邻位置是否有木板
            if (level.getBlockState(adjacentPos).is(BlockTags.PLANKS)) {
                // 获取铭文台默认状态
                BlockState tableState = BlockRegistry.INSCRIPTION_TABLE_BLOCK.get().defaultBlockState();

                // 设置左侧（相邻木板）和右侧（下方方块）
                level.setBlock(adjacentPos, tableState
                        .setValue(BlockStateProperties.CHEST_TYPE, ChestType.LEFT)
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, facing), 3);
                level.setBlock(basePos, tableState
                        .setValue(BlockStateProperties.CHEST_TYPE, ChestType.RIGHT)
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, facing), 3);

                // 移除原来的书堆
                level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);
                player.swing(hand,true);
                itemStack.consume(1,player);
                break; // 只处理第一个有效的相邻方向
            }
        }
    }
}
