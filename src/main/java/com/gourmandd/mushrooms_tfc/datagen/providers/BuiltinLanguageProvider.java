package com.gourmandd.mushrooms_tfc.datagen.providers;

import com.gourmandd.mushrooms_tfc.MushroomsTFC;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCBlocks;
import com.gourmandd.mushrooms_tfc.registry.MushroomsTFCItems;
import com.gourmandd.mushrooms_tfc.util.Mushrooms;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Locale;
import java.util.stream.Stream;

public class BuiltinLanguageProvider extends LanguageProvider {

    public BuiltinLanguageProvider(PackOutput packOutput) {
        super(packOutput, MushroomsTFC.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {

        // misc lang
        add("item_group.mushrooms." + MushroomsTFC.MODID, "Mushrooms TFC Items");

        Stream.of(Mushrooms.values()).forEach(mushroom -> {
            add(MushroomsTFCItems.MUSHROOMS.get(mushroom).get(), getName(mushroom.getSerializedName()));
            add(MushroomsTFCBlocks.MUSHROOMS.get(mushroom).get(), getName(mushroom.getSerializedName()));
        });

//        // Adds a translation with the given key and the given value.
//        add("translation.key.1", "Translation 1");
//
//        // Helpers are available for various common object types. Every helper has two variants: an add() variant
//        // for the object itself, and an addTypeHere() variant that accepts a supplier for the object.
//        // The different names for the supplier variants are required due to generic type erasure.
//        // All following examples assume the existence of the values as suppliers of the needed type.
//
//        // Adds a block translation.
//        add(MyBlocks.EXAMPLE_BLOCK.get(), "Example Block");
//        addBlock(MyBlocks.EXAMPLE_BLOCK, "Example Block");
//        // Adds an item translation.
//        add(MyItems.EXAMPLE_ITEM.get(), "Example Item");
//        addItem(MyItems.EXAMPLE_ITEM, "Example Item");
//        // Adds an item stack translation. This is mainly for items that have NBT-specific names.
//        add(MyItems.EXAMPLE_ITEM_STACK.get(), "Example Item");
//        addItemStack(MyItems.EXAMPLE_ITEM_STACK, "Example Item");
//        // Adds an entity type translation.
//        add(MyEntityTypes.EXAMPLE_ENTITY_TYPE.get(), "Example Entity");
//        addEntityType(MyEntityTypes.EXAMPLE_ENTITY_TYPE, "Example Entity");
//        // Adds an enchantment translation.
//        add(MyEnchantments.EXAMPLE_ENCHANTMENT.get(), "Example Enchantment");
//        addEnchantment(MyEnchantments.EXAMPLE_ENCHANTMENT, "Example Enchantment");
//        // Adds a mob effect translation.
//        add(MyMobEffects.EXAMPLE_MOB_EFFECT.get(), "Example Effect");
//        addEffect(MyMobEffects.EXAMPLE_MOB_EFFECT, "Example Effect");
    }

    public static String getName(String string){
        final String[] new_string = {""};
        final int[] count = {0};

        Stream.of(string.toLowerCase(Locale.ROOT).split("_")).forEach(str -> {
            if (count[0] == 0){
                new_string[0] = str.substring(0, 1).toUpperCase() + str.substring(1);
            } else {
                new_string[0] = new_string[0] + " " + str.substring(0, 1).toUpperCase() + str.substring(1);
            }
            count[0] = count[0] + 1;
        });

        return new_string[0];
    }
}
