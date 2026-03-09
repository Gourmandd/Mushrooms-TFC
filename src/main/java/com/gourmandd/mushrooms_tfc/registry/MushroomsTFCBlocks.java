package com.gourmandd.mushrooms_tfc.registry;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.block.MushroomBlock;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class MushroomsTFCBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MushroomsTFC.MODID);

    public static final DeferredHolder<Block, Block> TEST_MUSHROOM = register("plant/test_mushroom", () -> MushroomBlock.createNewBlock(Items.BROWN_MUSHROOM::asItem));

    private static <T extends Block> DeferredHolder<Block, T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(MushroomsTFCBlocks.BLOCKS, MushrromsTFCItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
