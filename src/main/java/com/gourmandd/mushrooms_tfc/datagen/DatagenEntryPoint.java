package com.gourmandd.mushrooms_tfc.datagen;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.datagen.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatagenEntryPoint {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        final PackOutput output = event.getGenerator().getPackOutput();

        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, BuiltinConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, BuiltinPlacedFeatures::bootstrap);

        DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), registrySetBuilder, Set.of(MushroomsTFC.MODID));
        event.getGenerator().addProvider(event.includeServer(), datapackProvider);

        CompletableFuture<HolderLookup.Provider> lookup = datapackProvider.getRegistryProvider();

        add(event, new BuiltinBlockStates(output, event.getExistingFileHelper()));
        add(event, new BuiltinItemModels(output, event.getExistingFileHelper()));
        add(event, new BuiltinBlockTags(event, lookup));
        add(event, new BuiltinPlacedFeatureTags(event, lookup));
        add(event, new BuiltinClimateRanges(output, lookup));
        add(event, new BuiltinLanguageProvider(output));
        add(event, new BuiltinFoodData(output, lookup));
    }

    private static <T extends DataProvider> T add(GatherDataEvent event, T provider)
    {
        return event.getGenerator().addProvider(true, provider);
    }
}
