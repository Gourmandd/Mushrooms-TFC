package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class BuiltinItemModels extends ItemModelProvider {

    public BuiltinItemModels(PackOutput output,  ExistingFileHelper existingFileHelper) {
        super(output, MushroomsTFC.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Stream.of(Mushrooms.values()).forEach(mushroom -> {
            simpleBlock(MushroomsTFCBlocks.MUSHROOMS.get(mushroom));
        });
    }

    private void simpleBlock(DeferredHolder<Block, ? extends Block> block){
        withExistingParent(getItemModelString(block.getId()), getBlockModelLocation(block.getId()));
    }

    private String getItemModelString(ResourceLocation block){
        return block.getNamespace() + ":item/" + block.getPath();
    }

    private ResourceLocation getBlockModelLocation(ResourceLocation block){
        return ResourceLocation.fromNamespaceAndPath(block.getNamespace(), "block/" + block.getPath());
    }
}
