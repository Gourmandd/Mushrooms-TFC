package com.gourmandd.mushrooms_tfc.block;

import com.gourmandd.mushrooms_tfc.block_entity.MushroomBlockEntity;
import com.gourmandd.mushrooms_tfc.util.RegistryMushroom;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.plant.fruit.StationaryBerryBushBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.function.Supplier;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

public abstract class MushroomBlock extends StationaryBerryBushBlock {

    public final RegistryMushroom mushroomType;

    protected static final VoxelShape FULL_PLANT = box(2, 0, 2, 14, 8, 14);

    //protected static final VoxelShape DORMANT_PLANT = box(0, 0, 0, 0, 0, 0);
    protected static final VoxelShape DORMANT_PLANT = box(2, 0, 2, 14, 8, 14);

    public MushroomBlock(ExtendedProperties properties, Supplier<ClimateRange> climateRange, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, RegistryMushroom mushroomType) {
        super(properties, productItem, lifecycle, climateRange);
        this.mushroomType = mushroomType;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (state.getValue(LIFECYCLE) == FRUITING && stack.is(TFCTags.Items.TOOLS_KNIFE)) {
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.PLAYERS, 1.0F, level.getRandom().nextFloat() + 0.7F + 0.3F);
            if (!level.isClientSide()) {
                if (moveToNearbyBlock(state, level, pos)){
                    ItemHandlerHelper.giveItemToPlayer(player, this.getProductItem(level.random));
                } else {
                    // it failed to move
                    ItemHandlerHelper.giveItemToPlayer(player, this.getProductItem(level.random));
                    level.setBlockAndUpdate(pos, this.stateAfterPicking(state));
                    MushroomBlockEntity.resetPickedTick(level, pos);
                }
            }

            return ItemInteractionResult.CONSUME;
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(LIFECYCLE).active() ? FULL_PLANT : DORMANT_PLANT;
    }

    @Override
    protected BlockState getDeadState(BlockState state)
    {
        //TODO: Change
        return TFCBlocks.DEAD_BERRY_BUSH.get().defaultBlockState().setValue(STAGE, state.getValue(STAGE));
    }

    @Override
    public BlockState stateAfterPicking(BlockState state) {
        return state.setValue(LIFECYCLE, DORMANT);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return Helpers.isBlock(level.getBlockState(pos), this.mushroomType.getSupportingBlockTag());
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        return this.mayPlaceOn(level.getBlockState(belowPos), level, belowPos);
    }

    protected boolean moveToNearbyBlock(BlockState state, Level level, BlockPos pos){

        // This might need comments to guide through what is meant to be happening.
        // first roll a 1/3 chance to see if its new y-level will be: (0: pos -1, 1: pos, 2: pos + 1)
        // second roll a 1/5 chance to see if its new x-level will be: (0: + 1 south, 1: + 1 west, 2: + 1 east, 3: + 1 north, 4: the same)

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

    protected BlockPos getNewYPos(int number, BlockPos pos){

        switch (number){
            case 0 -> {
                return pos.below();
            }
            case 2 -> {
                return pos.above();
            }
            default -> {
                return pos;
            }
        }
    }

    protected BlockPos getNewXPos(int number, BlockPos pos){
        switch (number){
            case 0 -> {
                return pos.south();
            }
            case 1 -> {
                return pos.west();
            }
            case 2 -> {
                return pos.east();
            }
            case 3 -> {
                return pos.north();
            }
            default -> {
                return pos;
            }
        }
    }
}
