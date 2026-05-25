package com.gourmandd.mushrooms_tfc.block;

import com.gourmandd.mushrooms_tfc.block_entity.MushroomBlockEntity;
import com.gourmandd.mushrooms_tfc.util.RegistryMushroom;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ShelfMushroomBlock extends MushroomBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ShelfMushroomBlock(ExtendedProperties properties, Supplier<ClimateRange> climateRange, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, RegistryMushroom mushroomType) {
        super(properties, climateRange, productItem, lifecycle, mushroomType);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSurvive(level, pos, state.getValue(FACING));
    }

    public static boolean canSurvive(LevelReader level, BlockPos pos, Direction facing) {
        BlockPos blockpos = pos.relative(facing.getOpposite());
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.isFaceSturdy(level, blockpos, facing) && Helpers.isBlock(level.getBlockState(blockpos), BlockTags.LOGS);
    }

    @Override
    protected void moveToNearbyBlock(BlockState state, Level level, BlockPos pos){

        // This might need comments to guide through what is meant to be happening.
        // first roll a 1/3 chance to see if its new y-level will be: (0: pos -1, 1: pos, 2: pos + 1)
        // second roll a 1/5 chance to see if its new x-level will be: (0: + 1 south, 1: + 1 west, 2: + 1 east, 3: + 1 north, 4: the same)
        for (int i = 1; i <= 5; i++){
            int oneInThree = (int) Math.floor(Math.random() * 3);
            int oneInFour = (int) Math.floor(Math.random() * 4);

            BlockPos newPos = getNewXPos(oneInFour, getNewYPos(oneInThree, pos));

            if (this.mayPlaceOn(state, level, newPos.below()) && level.getBlockState(newPos).canBeReplaced()){
                level.destroyBlock(pos, false);
                level.setBlockAndUpdate(newPos, this.stateAfterPicking(state));
                MushroomBlockEntity.resetPickedTick(level, newPos);
                break;
            }
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for(Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (blockstate.canSurvive(levelreader, blockpos)) {
                    return blockstate;
                }
            }
        }

        return null;
    }

    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(LIFECYCLE, STAGE);
    }
}
