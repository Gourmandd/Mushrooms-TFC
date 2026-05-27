package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import com.gourmandd.mushrooms_tfc.util.MushroomsTFCTags;
import net.dries007.tfc.TerraFirmaCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class BuiltinPlacedFeatureTags extends TagsProvider<PlacedFeature> {

    private final ExistingFileHelper.IResourceType resourceType;

    public BuiltinPlacedFeatureTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(event.getGenerator().getPackOutput(), Registries.PLACED_FEATURE, lookup, MushroomsTFC.MODID, event.getExistingFileHelper());
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
    }

    private static final TagKey<PlacedFeature> TFC_LAND_PLANTS = TagKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(TerraFirmaCraft.MOD_ID, "feature/land_plants")
    );

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        Stream.of(Mushrooms.values()).forEach(mushroom ->  {
            this.tag(MushroomsTFCTags.PlacedFeatures.MUSHROOMS)
                    .add(BuiltinPlacedFeatures.MUSHROOM_PATCH_PLACED_FEATURES.get(mushroom));
        });

        this.tag(TFC_LAND_PLANTS)
                .addTag(MushroomsTFCTags.PlacedFeatures.MUSHROOMS)
                .replace(true);
    }
}
