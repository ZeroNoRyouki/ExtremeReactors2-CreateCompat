package it.zerono.mods.extremereactors.compat.create.datagen.recipe;

import it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat;
import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.extremereactors.datagen.recipe.AbstractRecipesDataProvider;
import it.zerono.mods.extremereactors.gamecontent.Content;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.variant.ReactorVariant;
import it.zerono.mods.zerocore.lib.data.ResourceLocationBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReactorRecipesDataProvider
        extends AbstractRecipesDataProvider {

    public ReactorRecipesDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup,
                                      ResourceLocationBuilder modLocationRoot) {
        super(ExtremeReactorsCreateCompat.MOD_ID, "Reactor recipes", output, registryLookup, modLocationRoot);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> builder) {

        TagKey<Item> metal, fallbackMetal;
        ReactorVariant variant;
        Supplier<? extends ItemLike> casing;

        // Basic parts

        variant = ReactorVariant.Basic;
        casing = Content.Items.REACTOR_CASING_BASIC;
        metal = Tags.Items.INGOTS_IRON;
        fallbackMetal = null;

        this.displaySource(builder, variant, CreateContent.Items.REACTOR_DISPLAYSOURCE_BASIC, casing, metal, fallbackMetal);

        // Reinforced parts

        variant = ReactorVariant.Reinforced;
        casing = Content.Items.REACTOR_CASING_REINFORCED;
        metal = TAG_INGOTS_STEEL;
        fallbackMetal = Tags.Items.STORAGE_BLOCKS_IRON;

        this.displaySource(builder, variant, CreateContent.Items.REACTOR_DISPLAYSOURCE_REINFORCED, casing, metal, fallbackMetal);
    }

    //region internals

    private void displaySource(Consumer<FinishedRecipe> builder, ReactorVariant variant,
                               Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> casing,
                               TagKey<Item> metal, @Nullable TagKey<Item> fallbackMetal) {

        final var idBuilder = this.reactorRoot(variant);

        this.withFallback(builder, idBuilder.buildWithSuffix("diplaysource"), metal,
                idBuilder.buildWithSuffix("diplaysource_alt"), fallbackMetal,
                tag -> this.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                        .define('C', casing.get())
                        .define('M', tag)
                        .define('G', Tags.Items.STORAGE_BLOCKS_GOLD)
                        .define('Z', Tags.Items.DUSTS_GLOWSTONE)
                        .define('X', Content.Items.WRENCH.get())
                        .pattern("CZC")
                        .pattern("MGM")
                        .pattern("CXC")
                        .unlockedBy("has_item", has(casing.get())));
    }

    //endregion
}
