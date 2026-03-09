package com.gourmandd.mushrooms_tfc;

import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandler {

    public static void init(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(ClientEventHandler::clientSetup);
    }

    public static void clientSetup(FMLClientSetupEvent event) {

        final RenderType cutoutMipped = RenderType.cutoutMipped();

        ItemBlockRenderTypes.setRenderLayer(MushroomsTFCBlocks.TEST_MUSHROOM.get(), cutoutMipped);
    }
}
