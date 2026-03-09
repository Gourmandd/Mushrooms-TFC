package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.block.MushroomBlock;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BuiltinBlockStates extends BlockStateProvider {

    // whether to use a debug model, make sure to never commit the debug generated data.
    final static boolean debugDormant = true;

    public BuiltinBlockStates(PackOutput output,  ExistingFileHelper exFileHelper) {
        super(output, MushroomsTFC.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        addMushroom(MushroomsTFCBlocks.TEST_MUSHROOM);
    }

    private void addMushroom(DeferredHolder<Block, Block> block){
        if (debugDormant){
            addMushroom(block, createModel(getBlockModelString(block.getId()) + "_dormant", "minecraft:acacia_planks"));
        } else {
            addMushroom(block, createModel(getBlockModelString(block.getId()) + "_dormant", "minecraft:air"));
        }
    }

    private void addMushroom(DeferredHolder<Block, Block> block, ModelFile modelDormant){

        ResourceLocation texture1 = ResourceLocation.parse("mushrooms_tfc:block/plant/test_healthy");
        ResourceLocation texture2 = ResourceLocation.parse("mushrooms_tfc:block/plant/test_flowering");
        ResourceLocation texture3 = ResourceLocation.parse("mushrooms_tfc:block/plant/test_fruiting");

        ModelFile modelHealthy = createModel(getBlockModelString(block.getId()) + "_healthy", "minecraft:cross").texture("cross", texture1).texture("particle", texture1);
        ModelFile modelFlowering = createModel(getBlockModelString(block.getId()) + "_flowering", "minecraft:cross").texture("cross", texture2).texture("particle", texture2);
        ModelFile modelFruiting = createModel(getBlockModelString(block.getId()) + "_fruiting", "minecraft:cross").texture("cross", texture3).texture("particle", texture3);

        // for the block's item model.
        createModel(getBlockModelString(block.getId()), "minecraft:cross").texture("cross", texture3).texture("particle", texture3);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(MushroomBlock.LIFECYCLE, Lifecycle.DORMANT).modelForState().modelFile(modelDormant).rotationY(0).addModel()
                .partialState().with(MushroomBlock.LIFECYCLE, Lifecycle.HEALTHY).modelForState().modelFile(modelHealthy).rotationY(0).addModel()
                .partialState().with(MushroomBlock.LIFECYCLE, Lifecycle.FLOWERING).modelForState().modelFile(modelFlowering).rotationY(0).addModel()
                .partialState().with(MushroomBlock.LIFECYCLE, Lifecycle.FRUITING).modelForState().modelFile(modelFruiting).rotationY(0).addModel();
    }

    private String getBlockModelString(ResourceLocation block){
        return block.getNamespace() + ":block/" + block.getPath();
    }

    private ModelBuilder<BlockModelBuilder> createModel(String name, String parent){
        return this.models().withExistingParent(name, parent);
    }
}
