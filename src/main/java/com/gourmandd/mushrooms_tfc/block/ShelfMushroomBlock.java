package com.gourmandd.mushrooms_tfc.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

public class ShelfMushroomBlock extends MushroomBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH, Block.box(3.5F, 3.0F, 11.0F, 12.5F, 13.0F, 16.0F),
                    Direction.SOUTH, Block.box(3.5F, 3.0F, 0.0F, 12.5F, 13.0F, 5.0F),
                    Direction.WEST, Block.box(11.0F, 3.0F, 2.5F, 16.0F, 13.0F, 12.5F),
                    Direction.EAST, Block.box(0.0F, 3.0F, 2.5F, 5.0F, 13.0F, 12.5F))
    );
    
    public ShelfMushroomBlock(ExtendedProperties properties, Supplier<ClimateRange> climateRange, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, RegistryMushroom mushroomType) {
        super(properties, climateRange, productItem, lifecycle, mushroomType);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSurvive(level, pos, state.getValue(FACING));
    }

    public static boolean canSurvive(LevelReader level, BlockPos pos, Direction facing) {
        BlockPos blockPos = pos.relative(facing.getOpposite());
        BlockState blockState = level.getBlockState(blockPos);
        return blockState.isFaceSturdy(level, blockPos, facing) && Helpers.isBlock(level.getBlockState(blockPos), BlockTags.LOGS);
    }

    @Override
    protected boolean moveToNearbyBlock(BlockState state, Level level, BlockPos pos){

        for (int i = 1; i <= 5; i++){
            int oneInThree = (int) Math.floor(Math.random() * 3);
            int oneInFour = (int) Math.floor(Math.random() * 4);

            BlockPos newPos = getNewXPos(oneInFour, getNewYPos(oneInThree, pos));

            if (canSurvive(state, level, newPos) && level.getBlockState(newPos).canBeReplaced() && newPos != pos){
                level.destroyBlock(pos, false);
                level.setBlockAndUpdate(newPos, this.stateAfterPicking(state));
                MushroomBlockEntity.resetPickedTick(level, newPos);
                return true;
            }
        }

        return false;
    }


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
        builder.add(FACING, LIFECYCLE, STAGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(LIFECYCLE).active() ? AABBS.get(state.getValue(FACING)) : DORMANT_PLANT;
    }

    protected BlockState updateShape(BlockState state, Direction facing,  BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : state;
    }
}
