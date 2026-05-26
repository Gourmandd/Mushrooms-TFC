package com.gourmandd.mushrooms_tfc.block;

import com.gourmandd.mushrooms_tfc.util.RegistryMushroom;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FieldMushroomBlock extends MushroomBlock {

    public FieldMushroomBlock(ExtendedProperties properties, Supplier<ClimateRange> climateRange, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, RegistryMushroom type) {
        super(properties, climateRange, productItem, lifecycle, type);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {

        if (!level.canSeeSky(pos)){
            return false;
        }

        return super.canSurvive(state, level, pos);
    }
}
