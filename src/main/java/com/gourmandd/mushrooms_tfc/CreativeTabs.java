package com.gourmandd.mushrooms_tfc;

import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCItems;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MushroomsTFC.MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MUSHROOMS = register("mushrooms", () -> new ItemStack(MushroomsTFCItems.MUSHROOMS.get(Mushrooms.FLY_AGARIC)), CreativeTabs::fillMushrooms);


    private static void fillMushrooms(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        for (Mushrooms mushroom : Mushrooms.values())
        {
            out.accept(MushroomsTFCItems.MUSHROOMS.get(mushroom).get());
            out.accept(MushroomsTFCBlocks.MUSHROOMS.get(mushroom).get());
        }
    }

    private static DeferredHolder<CreativeModeTab, CreativeModeTab> register(String id, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator gen)
    {
        return CREATIVE_TABS.register(id, () -> CreativeModeTab.builder()
                .icon(icon)
                .title(Component.translatable("item_group." + id + "." + MushroomsTFC.MODID))
                .displayItems(gen)
                .build()
        );
    }
}
