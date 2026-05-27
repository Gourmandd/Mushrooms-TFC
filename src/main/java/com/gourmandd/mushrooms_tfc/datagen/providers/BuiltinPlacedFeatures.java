package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.google.common.collect.ImmutableMap;
import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.block.MushroomBlock;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.world.blockpredicate.ReplaceablePredicate;
import net.dries007.tfc.world.placement.ClimatePlacement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BuiltinPlacedFeatures {

    public static final Map<Mushrooms, ResourceKey<PlacedFeature>> MUSHROOM_PLACED_FEATURES = Helpers.mapOf(Mushrooms.class, mushroom ->
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, "plant/" + mushroom.getSerializedName())
            )
    );

    public static final Map<Mushrooms, ResourceKey<PlacedFeature>> MUSHROOM_PATCH_PLACED_FEATURES = Helpers.mapOf(Mushrooms.class, mushroom ->
            ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, "plant/" + mushroom.getSerializedName() + "_patch")
            )
    );

    // using climate ranges to hold info, not intended purpose, I think.
    public static final Map<Mushrooms, ClimateRange> CLIMATES = ImmutableMap.<Mushrooms, ClimateRange>builder()
            .put(Mushrooms.FLY_AGARIC, new ClimateRange(100, 250, 0, -1.4f, 14f, 0))
            .put(Mushrooms.PORTOBELLO, new ClimateRange(110, 270, 0, -1.4f, 14f, 0))
            .put(Mushrooms.CHICKEN_OF_THE_WOODS, new ClimateRange(80, 330, 0, -1.4f, 14f, 0))
            .build();

    public static void bootstrap(BootstrapContext<PlacedFeature> ctx){

        HolderGetter<ConfiguredFeature<?, ?>> registry = ctx.lookup(Registries.CONFIGURED_FEATURE);

        Stream.of(Mushrooms.values()).forEach(mushroom -> {
            ctx.register(
                    MUSHROOM_PLACED_FEATURES.get(mushroom),
                    new PlacedFeature(
                            registry.getOrThrow(BuiltinConfiguredFeatures.MUSHROOM_CONFIGURED_FEATURES.get(mushroom)),
                            List.of(
                                    HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                                    BlockPredicateFilter.forPredicate(ReplaceablePredicate.INSTANCE),
                                    BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(
                                            MushroomsTFCBlocks.MUSHROOMS.get(mushroom).get().defaultBlockState().setValue(MushroomBlock.LIFECYCLE, Lifecycle.HEALTHY).setValue(MushroomBlock.STAGE, 0),
                                            new Vec3i(0,0,0)
                                    ))
                            )
                    )
            );

            ClimateRange climate = CLIMATES.get(mushroom);

            ctx.register(
                    MUSHROOM_PATCH_PLACED_FEATURES.get(mushroom),
                    new PlacedFeature(
                            registry.getOrThrow(BuiltinConfiguredFeatures.MUSHROOM_PATCH_CONFIGURED_FEATURES.get(mushroom)),
                            List.of(
                                    climateOf(
                                            climate.getMinTemperature(false),
                                            climate.getMaxTemperature(false),
                                            climate.getMinHydration(false),
                                            climate.getMaxHydration(false),
                                            mushroom.isFieldMushroom()
                                    ),
                                    InSquarePlacement.spread(),
                                    RarityFilter.onAverageOnceEvery(30)
                            )
                    )
            );
        });
    }

    private static ClimatePlacement climateOf(float minTemp, float maxTemp, int minGroundwater, int maxGroundwater, boolean isFieldMushroom){

        if (isFieldMushroom){
            return new ClimatePlacement(
                    minTemp,
                    maxTemp,
                    minGroundwater,
                    maxGroundwater,
                    -1, //default
                    1, //default
                    false, //default
                    0, //default
                    2, //lowered for field mushrooms so they don't generate commonly in forests.
                    new ArrayList<>(), //default
                    -64, //default
                    320, //default
                    false, //default
                    false //default
            );
        } else {
            return climateOf(minTemp, maxTemp, minGroundwater, maxGroundwater);
        }
    }

    private static ClimatePlacement climateOf(float minTemp, float maxTemp, int minGroundwater, int maxGroundwater){
        return new ClimatePlacement(
                minTemp,
                maxTemp,
                minGroundwater,
                maxGroundwater,
                -1, //default
                1, //default
                false, //default
                0, //default
                4, //default
                new ArrayList<>(), //default
                -64, //default
                320, //default
                false, //default
                false //default
        );
    }
}
