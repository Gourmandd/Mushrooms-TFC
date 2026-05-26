package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.util.ClimateRanges;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.data.DataManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public class BuiltinClimateRanges extends DataManagerProvider<ClimateRange> {

    public BuiltinClimateRanges( PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(ClimateRange.MANAGER, output, lookup);
    }

    @Override
    protected void addData(HolderLookup.Provider provider) {
        add(ClimateRanges.MUSHROOMS, Mushrooms.PORTOBELLO, b -> b.hydration(0, 100).temperature(3, 12.4f));
        add(ClimateRanges.MUSHROOMS, Mushrooms.FLY_AGARIC, b -> b.hydration(30, 100).temperature(-3, 13.6f));
        add(ClimateRanges.MUSHROOMS, Mushrooms.CHICKEN_OF_THE_WOODS, b -> b.hydration(0, 100).temperature(-6, 15.2f));
    }

    private <T> void add(Map<T, DataManager.Reference<ClimateRange>> map, T value, UnaryOperator<ClimateRange.Builder> builder)
    {
        add(map.get(value), builder.apply(new ClimateRange.Builder()).build());
    }
}
