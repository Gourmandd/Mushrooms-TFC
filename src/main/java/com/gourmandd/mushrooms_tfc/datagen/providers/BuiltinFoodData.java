package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCItems;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.dries007.tfc.common.component.food.FoodCapability;
import net.dries007.tfc.common.component.food.FoodData;
import net.dries007.tfc.common.component.food.FoodDefinition;
import net.dries007.tfc.common.items.Food;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static net.dries007.tfc.common.component.food.FoodData.*;

public class BuiltinFoodData extends DataManagerProvider<FoodDefinition> {

    public BuiltinFoodData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(FoodCapability.MANAGER, output, lookup);
    }

    @Override
    protected void addData(HolderLookup.Provider provider) {
        addMushroom(Mushrooms.PORTOBELLO, ofFood(4.5f, 3f, 1f).protein(0.3f).vegetables(0.5f));
        addMushroom(Mushrooms.FLY_AGARIC, ofFood(4.5f, 3f, 1f).protein(0.1f).vegetables(0.8f));
        addMushroom(Mushrooms.CHICKEN_OF_THE_WOODS, ofFood(4.5f, 3f, 1f).protein(1f).vegetables(0.2f));
    }

    private void addMushroom(Mushrooms mushroom, FoodData food)
    {
        add(MushroomsTFCItems.MUSHROOMS.get(mushroom).get(), food, true);
    }

    private void addMushroom(Mushrooms mushroom, FoodData food, boolean edible)
    {
        add(MushroomsTFCItems.MUSHROOMS.get(mushroom).get(), food, edible);
    }

    private void add(Food item, FoodData food)
    {
        add(TFCItems.FOOD.get(item), food);
    }

    private void add(ItemLike item, FoodData food)
    {
        add(item, food, true);
    }

    private void add(ItemLike item, FoodData food, boolean edible)
    {
        add(nameOf(item).replace("food/", ""), new FoodDefinition(Ingredient.of(item), food, edible));
    }

    private void add(TagKey<Item> tag, FoodData food, boolean edible)
    {
        add(tag.location().getPath().replace("foods/", ""), new FoodDefinition(Ingredient.of(tag), food, edible));
    }

    private String nameOf(ItemLike item)
    {
        assert item.asItem() != Items.BARRIER : "Item should not be Items.BARRIER";
        assert item.asItem() != Items.AIR : "Item should not be Items.AIR";
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }
}
