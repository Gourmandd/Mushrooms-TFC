package com.gourmandd.mushrooms_tfc.block_entity;

import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlockEntities;
import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
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
        this(MushroomsTFCBlockEntities.MUSHROOMS.get(), pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return MushroomsTFCBlockEntities.MUSHROOMS.get();
    }

    public static void reset(Level level, BlockPos pos) {
        level.getBlockEntity(pos, MushroomsTFCBlockEntities.MUSHROOMS.get()).ifPresent(TickCounterBlockEntity::resetCounter);
    }

    public static void resetPickedTick(Level level, BlockPos pos) {
        level.getBlockEntity(pos, MushroomsTFCBlockEntities.MUSHROOMS.get()).ifPresent(TickingPlantBlockEntity::resetLastPickedCounter);
    }
}
