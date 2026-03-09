package com.gourmandd.mushrooms_tfc;

import com.gourmandd.mushrooms_tfc.datagen.DatagenEntryPoint;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlockEntities;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.registry.MushrromsTFCItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MushroomsTFC.MODID)

public class MushroomsTFC {
    public static final String MODID = "mushrooms_tfc";
    public static final Logger LOGGER = LogUtils.getLogger();

//
//    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
//    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
//            .title(Component.translatable("itemGroup.examplemod")) //The language key for the title of your CreativeModeTab
//            .withTabsBefore(CreativeModeTabs.COMBAT)
//            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
//            .displayItems((parameters, output) -> {
//                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
//            }).build());

    public MushroomsTFC(IEventBus modEventBus, ModContainer modContainer) {

        //NeoForge.EVENT_BUS.register(this);
        MushroomsTFCBlocks.BLOCKS.register(modEventBus);
        MushroomsTFCBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        MushrromsTFCItems.ITEMS.register(modEventBus);

        modEventBus.addListener(DatagenEntryPoint::gatherData);

        if (FMLEnvironment.dist == Dist.CLIENT){
            ClientEventHandler.init(modEventBus, modContainer);
        }
    }
}
