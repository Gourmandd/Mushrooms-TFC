package com.gourmandd.mushrooms_tfc.registry;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.dries007.tfc.util.Helpers;
import net.minecraft.world.item.Item;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class MushroomsTFCItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MushroomsTFC.MODID);

    public static final Map<Mushrooms, DeferredHolder<Item, Item>> MUSHROOMS = Helpers.mapOf(Mushrooms.class, mushroom ->
            register("food/" + mushroom.getSerializedName(), () -> new Item(new Item.Properties().food(mushroom.getFoodProperties())))
    );

    private static DeferredHolder<Item, Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static DeferredHolder<Item, Item> register(String name, Supplier<Item> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
