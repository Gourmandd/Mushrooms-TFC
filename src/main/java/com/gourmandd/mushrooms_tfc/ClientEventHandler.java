package com.gourmandd.mushrooms_tfc;

import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.stream.Stream;

public class ClientEventHandler {

    public static void init(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(ClientEventHandler::clientSetup);
    }

    public static void clientSetup(FMLClientSetupEvent event) {

        final RenderType cutoutMipped = RenderType.cutoutMipped();

        Stream.of(Mushrooms.values()).forEach(mushroom -> {
            ItemBlockRenderTypes.setRenderLayer(MushroomsTFCBlocks.MUSHROOMS.get(mushroom).get(), cutoutMipped);
        });
    }
}
