package com.gourmandd.mushrooms_tfc.block;

import com.gourmandd.mushrooms_tfc.util.RegistryMushroom;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class SymbioteMushroomBlock extends MushroomBlock {

    public SymbioteMushroomBlock(ExtendedProperties properties, Supplier<ClimateRange> climateRange, Supplier<? extends Item> productItem, Lifecycle[] lifecycle, RegistryMushroom type) {
        super(properties, climateRange, productItem, lifecycle, type);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {

        if (mayPlaceOn(level.getBlockState(pos), level, pos.below())){
            BlockPos[] positions = new BlockPos[] {
                    pos.west(),
                    pos.east(),
                    pos.north(),
                    pos.south(),
                    pos.below()
            };
            for (BlockPos position : positions){
                if (Helpers.isBlock(level.getBlockState(position), BlockTags.LOGS)){
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return Helpers.isBlock(level.getBlockState(pos), this.mushroomType.getSupportingBlockTag()) || Helpers.isBlock(level.getBlockState(pos), BlockTags.LOGS);
    }
}
