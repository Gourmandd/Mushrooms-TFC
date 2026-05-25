package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCItems;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
            mushroomItem(MushroomsTFCItems.MUSHROOMS.get(mushroom), itemTexture(mushroom));
            mushroomBlockItem(MushroomsTFCBlocks.MUSHROOMS.get(mushroom));
        });
    }

    private String getItemModelString(ResourceLocation id){
        return id.getNamespace() + ":item/" + id.getPath();
    }

    private ResourceLocation getBlockModelLocation(ResourceLocation block){
        return ResourceLocation.fromNamespaceAndPath(block.getNamespace(), "block/" + block.getPath());
    }

    private void mushroomItem(DeferredHolder<Item, Item> item, ResourceLocation texture){
        this.getBuilder(getItemModelString(item.getId())).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", texture);
    }

    private void mushroomBlockItem(DeferredHolder<Block, Block> block){
        withExistingParent(getItemModelString(block.getId()), getBlockModelLocation(block.getId()));
    }

    private ResourceLocation itemTexture(Mushrooms mushroom){
        if (mushroom.isShelfMushroom()){
            return ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, "block/plant/" + mushroom.getSerializedName() + "_large_fruiting");
        } else {
            return ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, "block/plant/" + mushroom.getSerializedName() + "_fruiting");
        }
    }
}
