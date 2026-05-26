package com.gourmandd.mushrooms_tfc.util;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MushroomsTFCTags {


    public static class Blocks {

        private static TagKey<Block> createTag(String name){
            return TagKey.create(
                    Registries.BLOCK,
                    ResourceLocation.fromNamespaceAndPath(MushroomsTFC.MODID, name)
            );
        }

        public static TagKey<Block> MUSHROOMS_GROWS_ON = createTag("mushrooms_grow_on");
        public static TagKey<Block> PORTOBELLO_GROWS_ON = createTag("portobello_grow_on");
        public static TagKey<Block> FLY_AGARIC_GROWS_ON = createTag("fly_agaric_grow_on");
        public static TagKey<Block> CHICKEN_OF_THE_WOODS_GROW_ON = createTag("chicken_of_the_woods_grow_on");
    }
}
