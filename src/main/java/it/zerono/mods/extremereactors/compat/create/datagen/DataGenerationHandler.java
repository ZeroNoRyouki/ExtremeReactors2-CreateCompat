package it.zerono.mods.extremereactors.compat.create.datagen;

import it.zerono.mods.extremereactors.compat.create.datagen.client.ReactorModelsDataProvider;
import it.zerono.mods.extremereactors.compat.create.datagen.loot.BlockSubProvider;
import it.zerono.mods.extremereactors.compat.create.datagen.recipe.ReactorRecipesDataProvider;
import it.zerono.mods.extremereactors.compat.create.datagen.tag.BlockTagsDataProvider;
import it.zerono.mods.extremereactors.compat.create.datagen.tag.ItemTagsDataProvider;
import it.zerono.mods.zerocore.lib.datagen.IModDataGenerator;
import net.minecraftforge.common.util.NonNullConsumer;
import org.jetbrains.annotations.NotNull;

public class DataGenerationHandler
        implements NonNullConsumer<IModDataGenerator> {

    @Override
    public void accept(@NotNull IModDataGenerator generator) {

        // tags

        generator.addBlockTagsProvider(new BlockTagsDataProvider());
        generator.addItemTagsProvider(new ItemTagsDataProvider());

        // loot

        generator.addLootProvider(builder -> builder.addBlockProvider(BlockSubProvider::new));

        // recipes

        generator.addProvider(ReactorRecipesDataProvider::new);

        // block states and models

        generator.addProvider(ReactorModelsDataProvider::new);
    }
}
