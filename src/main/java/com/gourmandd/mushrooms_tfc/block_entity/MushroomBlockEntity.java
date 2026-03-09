package com.gourmandd.mushrooms_tfc.block_entity;

import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blockentities.TickingPlantBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MushroomBlockEntity extends BerryBushBlockEntity {


    protected MushroomBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MushroomBlockEntity(BlockPos pos, BlockState state) {
        this(TFCBlockEntities.BERRY_BUSH.get(), pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return TFCBlockEntities.BERRY_BUSH.get();
    }

    public static void reset(Level level, BlockPos pos) {
        level.getBlockEntity(pos, TFCBlockEntities.BERRY_BUSH.get()).ifPresent(TickCounterBlockEntity::resetCounter);
    }

    public static void resetPickedTick(Level level, BlockPos pos) {
        level.getBlockEntity(pos, TFCBlockEntities.BERRY_BUSH.get()).ifPresent(TickingPlantBlockEntity::resetLastPickedCounter);
    }
}
