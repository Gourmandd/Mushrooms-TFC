package com.gourmandd.mushrooms_tfc.util;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.data.DataManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class ClimateRanges {

    //public static final Map<CoreFruitTrees, DataManager.Reference<ClimateRange>> FRUIT_TREES = Helpers.mapOf(CoreFruitTrees.class, tree -> register("plant/" + tree.name() + "_tree"));

    public static final DataManager.Reference<ClimateRange> TEST_MUSHROOM = register("test_mushroom");

    private static DataManager.Reference<ClimateRange> register(String name)
    {
        return ClimateRange.MANAGER.getReference(ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, name.toLowerCase(Locale.ROOT)));
    }
}
