package com.gourmandd.mushrooms_tfc.registry;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.block_entity.MushroomBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MushroomsTFCBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MushroomsTFC.MODID);

    public static final TFCBlockEntities.Id<MushroomBlockEntity> MUSHROOMS = register("mushroom", MushroomBlockEntity::new, Stream.of(
            MushroomsTFCBlocks.MUSHROOMS.values()
    ).flatMap(Collection::stream));

    private static <T extends BlockEntity> TFCBlockEntities.Id<T> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return new TFCBlockEntities.Id<>(RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks));
    }
}
