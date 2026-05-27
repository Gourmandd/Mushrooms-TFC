package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.block.MushroomBlock;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Map;
import java.util.stream.Stream;

public class BuiltinConfiguredFeatures  {

    public static final Map<Mushrooms, ResourceKey<ConfiguredFeature<?, ?>>> MUSHROOM_CONFIGURED_FEATURES = Helpers.mapOf(Mushrooms.class, mushroom ->
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, "plant/" + mushroom.getSerializedName())
            )
    );

    public static final Map<Mushrooms, ResourceKey<ConfiguredFeature<?, ?>>> MUSHROOM_PATCH_CONFIGURED_FEATURES = Helpers.mapOf(Mushrooms.class, mushroom ->
            ResourceKey.create(
                    Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, "plant/" + mushroom.getSerializedName() + "_patch")
            )
    );

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> ctx){

        HolderGetter<PlacedFeature> registry = ctx.lookup(Registries.PLACED_FEATURE);

        Stream.of(Mushrooms.values()).forEach(mushroom -> {
            ctx.register(
                MUSHROOM_PATCH_CONFIGURED_FEATURES.get(mushroom),
                new ConfiguredFeature<>(
                    Feature.RANDOM_PATCH, new RandomPatchConfiguration(
                    16,
                    7,
                    3,
                    registry.getOrThrow(BuiltinPlacedFeatures.MUSHROOM_PLACED_FEATURES.get(mushroom))
                    )
                )
            );

            ctx.register(
                MUSHROOM_CONFIGURED_FEATURES.get(mushroom),
                new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(
                        BlockStateProvider.simple(
                                MushroomsTFCBlocks.MUSHROOMS.get(mushroom).get().defaultBlockState().setValue(MushroomBlock.LIFECYCLE, Lifecycle.FRUITING).setValue(MushroomBlock.STAGE, 0)
                        )
                    )
                )
            );
        });
    }
}
