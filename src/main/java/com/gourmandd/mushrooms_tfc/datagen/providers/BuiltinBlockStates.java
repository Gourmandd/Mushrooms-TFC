package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.block.MushroomBlock;
import com.gourmandd.mushrooms_tfc.block.ShelfMushroomBlock;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class BuiltinBlockStates extends BlockStateProvider {

    final static boolean debugDormant = true;

    public BuiltinBlockStates(PackOutput output,  ExistingFileHelper exFileHelper) {
        super(output, MushroomsTFC.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Stream.of(Mushrooms.values()).forEach(mushroom -> {
            if (mushroom.isShelfMushroom()){
                addShelfMushroom(MushroomsTFCBlocks.MUSHROOMS.get(mushroom), mushroom.getSerializedName());
            } else {
                addMushroom(MushroomsTFCBlocks.MUSHROOMS.get(mushroom), mushroom.getSerializedName());
            }
        });
    }

    private void addMushroom(DeferredHolder<Block, Block> block, String name){

        ResourceLocation texture1 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_healthy");
        ResourceLocation texture2 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_flowering");
        ResourceLocation texture3 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_fruiting");

        ModelFile modelHealthy = createModel(getBlockModelString(block.getId()) + "_healthy", "minecraft:cross").texture("cross", texture1).texture("particle", texture1);
        ModelFile modelFlowering = createModel(getBlockModelString(block.getId()) + "_flowering", "minecraft:cross").texture("cross", texture2).texture("particle", texture2);
        ModelFile modelFruiting = createModel(getBlockModelString(block.getId()) + "_fruiting", "minecraft:cross").texture("cross", texture3).texture("particle", texture3);
        ModelFile modelDormant = createModel(getBlockModelString(block.getId()) + "_dormant", getDormantModel());

        // for the block's item model.
        createModel(getBlockModelString(block.getId()), "minecraft:cross").texture("cross", texture3).texture("particle", texture3);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(MushroomBlock.LIFECYCLE, Lifecycle.DORMANT).modelForState().modelFile(modelDormant).rotationY(0).addModel()
                .partialState().with(MushroomBlock.LIFECYCLE, Lifecycle.HEALTHY).modelForState().modelFile(modelHealthy).rotationY(0).addModel()
                .partialState().with(MushroomBlock.LIFECYCLE, Lifecycle.FLOWERING).modelForState().modelFile(modelFlowering).rotationY(0).addModel()
                .partialState().with(MushroomBlock.LIFECYCLE, Lifecycle.FRUITING).modelForState().modelFile(modelFruiting).rotationY(0).addModel();
    }

    private void addShelfMushroom(DeferredHolder<Block, Block> block, String name){

        ResourceLocation large_texture1 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_large_healthy");
        ResourceLocation large_texture2 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_large_flowering");
        ResourceLocation large_texture3 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_large_fruiting");
        ResourceLocation small_texture1 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_small_healthy");
        ResourceLocation small_texture2 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_small_flowering");
        ResourceLocation small_texture3 = ResourceLocation.parse("mushrooms_tfc:block/plant/" + name + "_small_fruiting");

        ModelFile modelHealthy = createModel(getBlockModelString(block.getId()) + "_healthy", "mushrooms_tfc:block/shelf_mushroom")
                .texture("particle", large_texture1)
                .texture("1", large_texture1)
                .texture("2", small_texture1);
        ModelFile modelFlowering = createModel(getBlockModelString(block.getId()) + "_flowering", "mushrooms_tfc:block/shelf_mushroom")
                .texture("particle", large_texture1)
                .texture("1", large_texture2)
                .texture("2", small_texture2);
        ModelFile modelFruiting = createModel(getBlockModelString(block.getId()) + "_fruiting", "mushrooms_tfc:block/shelf_mushroom")
                .texture("particle", large_texture1)
                .texture("1", large_texture3)
                .texture("2", small_texture3);
        ModelFile modelDormant = createModel(getBlockModelString(block.getId()) + "_dormant", getDormantModel());

        // for the block's item model.
        createModel(getBlockModelString(block.getId()), "mushrooms_tfc:block/shelf_mushroom").texture("particle", large_texture3);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(ShelfMushroomBlock.FACING, Direction.NORTH).with(MushroomBlock.LIFECYCLE, Lifecycle.DORMANT).modelForState().modelFile(modelDormant).rotationY(0).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.EAST).with(MushroomBlock.LIFECYCLE, Lifecycle.DORMANT).modelForState().modelFile(modelDormant).rotationY(90).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.SOUTH).with(MushroomBlock.LIFECYCLE, Lifecycle.DORMANT).modelForState().modelFile(modelDormant).rotationY(180).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.WEST).with(MushroomBlock.LIFECYCLE, Lifecycle.DORMANT).modelForState().modelFile(modelDormant).rotationY(270).addModel()

                .partialState().with(ShelfMushroomBlock.FACING, Direction.NORTH).with(MushroomBlock.LIFECYCLE, Lifecycle.HEALTHY).modelForState().modelFile(modelHealthy).rotationY(0).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.EAST).with(MushroomBlock.LIFECYCLE, Lifecycle.HEALTHY).modelForState().modelFile(modelHealthy).rotationY(90).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.SOUTH).with(MushroomBlock.LIFECYCLE, Lifecycle.HEALTHY).modelForState().modelFile(modelHealthy).rotationY(180).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.WEST).with(MushroomBlock.LIFECYCLE, Lifecycle.HEALTHY).modelForState().modelFile(modelHealthy).rotationY(270).addModel()

                .partialState().with(ShelfMushroomBlock.FACING, Direction.NORTH).with(MushroomBlock.LIFECYCLE, Lifecycle.FLOWERING).modelForState().modelFile(modelFlowering).rotationY(0).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.EAST).with(MushroomBlock.LIFECYCLE, Lifecycle.FLOWERING).modelForState().modelFile(modelFlowering).rotationY(90).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.SOUTH).with(MushroomBlock.LIFECYCLE, Lifecycle.FLOWERING).modelForState().modelFile(modelFlowering).rotationY(180).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.WEST).with(MushroomBlock.LIFECYCLE, Lifecycle.FLOWERING).modelForState().modelFile(modelFlowering).rotationY(270).addModel()

                .partialState().with(ShelfMushroomBlock.FACING, Direction.NORTH).with(MushroomBlock.LIFECYCLE, Lifecycle.FRUITING).modelForState().modelFile(modelFruiting).rotationY(0).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.EAST).with(MushroomBlock.LIFECYCLE, Lifecycle.FRUITING).modelForState().modelFile(modelFruiting).rotationY(90).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.SOUTH).with(MushroomBlock.LIFECYCLE, Lifecycle.FRUITING).modelForState().modelFile(modelFruiting).rotationY(180).addModel()
                .partialState().with(ShelfMushroomBlock.FACING, Direction.WEST).with(MushroomBlock.LIFECYCLE, Lifecycle.FRUITING).modelForState().modelFile(modelFruiting).rotationY(270).addModel();
    }

    private String getBlockModelString(ResourceLocation block){
        return block.getNamespace() + ":block/" + block.getPath();
    }

    private ModelBuilder<BlockModelBuilder> createModel(String name, String parent){
        return this.models().withExistingParent(name, parent);
    }

    private String getDormantModel(){
        if (debugDormant){
            return "minecraft:rose_bush_bottom";
        } else {
            return "minecraft:air";
        }
    }

}
