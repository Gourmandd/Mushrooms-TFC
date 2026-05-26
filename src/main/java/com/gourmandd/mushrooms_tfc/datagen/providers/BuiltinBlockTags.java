package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.util.MushroomsTFCTags;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class BuiltinBlockTags extends TagsProvider<Block> {

    private final ExistingFileHelper.IResourceType resourceType;

    public BuiltinBlockTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(event.getGenerator().getPackOutput(), Registries.BLOCK, lookup, MushroomsTFC.MODID, event.getExistingFileHelper());
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        String AFC = "afc";

        this.tag(MushroomsTFCTags.Blocks.MUSHROOMS_GROWS_ON)
                .addOptionalTag(TFCTags.Blocks.GRASS);

        this.tag(MushroomsTFCTags.Blocks.PORTOBELLO_GROWS_ON)
                .addOptionalTag(TFCTags.Blocks.GRASS)
                .addOptionalTag(TFCTags.Blocks.DUFF);

        this.tag(MushroomsTFCTags.Blocks.FLY_AGARIC_GROWS_ON)
                .add(TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.DOUGLAS_FIR).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.BIRCH).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.ASH).get(Wood.BlockType.LOG).key());

        this.tag(MushroomsTFCTags.Blocks.CHICKEN_OF_THE_WOODS_GROW_ON)
                .add(TFCBlocks.WOODS.get(Wood.OAK).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.HICKORY).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.MAPLE).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.ASPEN).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.CHESTNUT).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.WILLOW).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.PINE).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.WHITE_CEDAR).get(Wood.BlockType.LOG).key())
                .add(TFCBlocks.WOODS.get(Wood.SPRUCE).get(Wood.BlockType.LOG).key())
                .addOptional(ResourceLocation.fromNamespaceAndPath(AFC, "wood/log/beech"))
                .addOptional(ResourceLocation.fromNamespaceAndPath(AFC, "wood/log/eucalyptus"));
    }
}
