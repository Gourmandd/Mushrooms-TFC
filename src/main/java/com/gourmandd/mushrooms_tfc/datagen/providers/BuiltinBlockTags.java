package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.util.MushroomsTFCTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BuiltinBlockTags extends BlockTagsProvider {

    public BuiltinBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MushroomsTFC.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(MushroomsTFCTags.Blocks.MUSHROOMS_GROWS_ON).addTag(BlockTags.DIRT);
        this.tag(MushroomsTFCTags.Blocks.CHICKEN_OF_THE_WOODS_GROW_ON).addTag(BlockTags.LOGS);
    }
}
