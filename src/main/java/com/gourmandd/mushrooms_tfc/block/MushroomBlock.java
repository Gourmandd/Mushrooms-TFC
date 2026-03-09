package com.gourmandd.mushrooms_tfc.block;

import com.gourmandd.mushrooms_tfc.block_entity.MushroomBlockEntity;
import com.gourmandd.mushrooms_tfc.util.ClimateRanges;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.plant.fruit.StationaryBerryBushBlock;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.function.Supplier;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;
import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.HEALTHY;

public class MushroomBlock extends StationaryBerryBushBlock {

    private static final VoxelShape FULL_PLANT = box(2, 0, 2, 14, 8, 14);

    private static final VoxelShape DORMANT_PLANT = box(0, 0, 0, 0, 0, 0);

    public MushroomBlock(ExtendedProperties properties, Supplier<ClimateRange> climateRange, Supplier<? extends Item> productItem, Lifecycle[] lifecycle) {
        super(properties, productItem, lifecycle, climateRange);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(LIFECYCLE) == FRUITING && player.getMainHandItem().is(TFCTags.Items.TOOLS_KNIFE)) {
            level.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.PLAYERS, 1.0F, level.getRandom().nextFloat() + 0.7F + 0.3F);
            if (!level.isClientSide()) {
                ItemHandlerHelper.giveItemToPlayer(player, this.getProductItem(level.random));
            }

            if (!moveToNearbyBlock(state, level, pos)){
                level.setBlockAndUpdate(pos, this.stateAfterPicking(state));
                MushroomBlockEntity.resetPickedTick(level, pos);
                //else, don't do anything and keep it in its original position.
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
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
        return TFCBlocks.DEAD_BERRY_BUSH.get().defaultBlockState().setValue(STAGE, state.getValue(STAGE));
    }

    @Override
    public BlockState stateAfterPicking(BlockState state) {
        return state.setValue(LIFECYCLE, DORMANT);
    }

    public static Block createNewBlock(Supplier<? extends Item> productItem){
        return new MushroomBlock(ExtendedProperties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.GRASS), ClimateRanges.TEST_MUSHROOM, productItem, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY});
    }

    private boolean moveToNearbyBlock(BlockState state, Level level, BlockPos pos){

        // TODO: find out why this its being moved twice?
        Iterable<BlockPos> positions = BlockPos.betweenClosed(pos.below().east().south(), pos.above().west().north());

        for (BlockPos newPos : positions){
            if (Math.random() > 0.88 && canSurvive(state, level, newPos) && level.getBlockState(newPos).isAir()){
                level.destroyBlock(pos, false);
                level.setBlock(newPos, this.stateAfterPicking(state), 3);
                return true;
            }
        }

        return false;
    }

}
