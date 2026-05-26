package com.gourmandd.mushrooms_tfc.util;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.data.DataManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;
import java.util.Map;

public class ClimateRanges {

    public static final Map<Mushrooms, DataManager.Reference<ClimateRange>> MUSHROOMS = Helpers.mapOf(Mushrooms.class, mushroom -> register("plant/" + mushroom.name()));

    private static DataManager.Reference<ClimateRange> register(String name)
    {
        return ClimateRange.MANAGER.getReference(ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, name.toLowerCase(Locale.ROOT)));
    }
}
