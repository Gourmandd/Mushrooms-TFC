package com.gourmandd.mushrooms_tfc.util;

import com.gourmandd.mushrooms_tfc.block.FieldMushroomBlock;
import com.gourmandd.mushrooms_tfc.block.ShelfMushroomBlock;
import com.gourmandd.mushrooms_tfc.block.SymbioteMushroomBlock;
import com.gourmandd.mushrooms_tfc.item.MushroomBlockItem;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCItems;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

public enum Mushrooms implements RegistryMushroom, StringRepresentable {

    PORTOBELLO(MushroomType.FIELD, MapColor.COLOR_BROWN, true, new Lifecycle[] {FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY, FLOWERING, FLOWERING, FRUITING, FRUITING, FRUITING}),
    FLY_AGARIC(MushroomType.SYMBIOTE, MapColor.TERRACOTTA_RED, true, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, FLOWERING, FLOWERING, FRUITING, FRUITING, FRUITING, FLOWERING, HEALTHY, DORMANT, DORMANT}),
    CHICKEN_OF_THE_WOODS(MapColor.TERRACOTTA_YELLOW, new Lifecycle[] {DORMANT, DORMANT, DORMANT, HEALTHY, FLOWERING, FRUITING, FRUITING, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT}, MushroomsTFCTags.Blocks.CHICKEN_OF_THE_WOODS_GROW_ON);

    final MushroomType type;
    final boolean fairyRings;
    final String serializedName;
    final MapColor mapColor;
    final Lifecycle[] lifecycle;
    final TagKey<Block> growsOnTag;

    Mushrooms(MapColor color, Lifecycle[] lifecycle, TagKey<Block> blockTag){
        this(MushroomType.SHELF, color, false, lifecycle, blockTag);
    }

    Mushrooms(MushroomType type, MapColor color, Lifecycle[] lifecycle){
        this(type, color, false, lifecycle);
    }

    Mushrooms(MushroomType type, MapColor color, boolean fairyRings, Lifecycle[] lifecycle){
        this(type, color, fairyRings, lifecycle, MushroomsTFCTags.Blocks.MUSHROOMS_GROWS_ON);
    }

    Mushrooms(MushroomType type, MapColor color, boolean fairyRings, Lifecycle[] lifecycle, TagKey<Block> growsOnTag){
        this.growsOnTag = growsOnTag;
        this.type = type;
        this.fairyRings = fairyRings;
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.mapColor = color;
        this.lifecycle = lifecycle;
    }

    @Override
    public boolean isFieldMushroom() {
        return type == MushroomType.FIELD;
    }

    @Override
    public boolean isShelfMushroom() {
        return type == MushroomType.SHELF;
    }

    @Override
    public boolean isSymbioticMushroom() {
        return type == MushroomType.SYMBIOTE;
    }

    @Override
    public boolean hasFairyRings() {
        return fairyRings;
    }

    @Override
    public MapColor getMapColor() {
        return mapColor;
    }

    @Override
    public Lifecycle[] getLifecycle() {
        return lifecycle;
    }

    @Override
    public TagKey<Block> getSupportingBlockTag() {
        return growsOnTag;
    }

    public Block createBlock(){
        //TODO: create and set proper climate ranges.
        switch (type){
            case FIELD -> {
                return new FieldMushroomBlock(ExtendedProperties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.GRASS), ClimateRanges.TEST_MUSHROOM, MushroomsTFCItems.MUSHROOMS.get(this), lifecycle, this);
            }
            case SHELF -> {
                return new ShelfMushroomBlock(ExtendedProperties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.GRASS), ClimateRanges.TEST_MUSHROOM, MushroomsTFCItems.MUSHROOMS.get(this), lifecycle, this);
            }
            case SYMBIOTE -> {
                return new SymbioteMushroomBlock(ExtendedProperties.of().mapColor(MapColor.COLOR_BROWN).sound(SoundType.GRASS), ClimateRanges.TEST_MUSHROOM, MushroomsTFCItems.MUSHROOMS.get(this), lifecycle, this);
            }
        }
        return null;
    }

    public BlockItem createItem(Block block) {
        return new MushroomBlockItem(block, ClimateRanges.MUSHROOMS.get(this), lifecycle);
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }
}
