package com.gourmandd.mushrooms_tfc.util;

import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

public interface RegistryMushroom {

    boolean isFieldMushroom();

    boolean isShelfMushroom();

    boolean isSymbioticMushroom();

    boolean hasFairyRings();

    MapColor getMapColor();

    Lifecycle[] getLifecycle();

    TagKey<Block> getSupportingBlockTag();

    enum MushroomType {

        FIELD, // can occur anywhere.
        SHELF, // occurs on logs.
        SYMBIOTE // occurs adjacent to logs.
    }
}
