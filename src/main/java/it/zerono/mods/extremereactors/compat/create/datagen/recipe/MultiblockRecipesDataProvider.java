package it.zerono.mods.extremereactors.compat.create.datagen.recipe;

import it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat;
import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.extremereactors.datagen.recipe.AbstractRecipesDataProvider;
import it.zerono.mods.extremereactors.gamecontent.Content;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.variant.ReactorVariant;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.variant.TurbineVariant;
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

public class MultiblockRecipesDataProvider
        extends AbstractRecipesDataProvider {

    public MultiblockRecipesDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup,
                                         ResourceLocationBuilder modLocationRoot) {
        super(ExtremeReactorsCreateCompat.MOD_ID, "Multiblock recipes", output, registryLookup, modLocationRoot);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> builder) {

        TagKey<Item> metal, fallbackMetal;

        // Basic parts

        metal = Tags.Items.INGOTS_IRON;
        fallbackMetal = null;

        this.reactorDisplaySource(builder, ReactorVariant.Basic, CreateContent.Items.REACTOR_DISPLAYSOURCE_BASIC,
                Content.Items.REACTOR_CASING_BASIC, metal, fallbackMetal);
        this.turbineDisplaySource(builder, TurbineVariant.Basic, CreateContent.Items.TURBINE_DISPLAYSOURCE_BASIC,
                Content.Items.TURBINE_CASING_BASIC, metal, fallbackMetal);

        // Reinforced parts

        metal = TAG_INGOTS_STEEL;
        fallbackMetal = Tags.Items.STORAGE_BLOCKS_IRON;

        this.reactorDisplaySource(builder, ReactorVariant.Reinforced, CreateContent.Items.REACTOR_DISPLAYSOURCE_REINFORCED,
                Content.Items.REACTOR_CASING_REINFORCED, metal, fallbackMetal);
        this.turbineDisplaySource(builder, TurbineVariant.Reinforced, CreateContent.Items.TURBINE_DISPLAYSOURCE_REINFORCED,
                Content.Items.TURBINE_CASING_REINFORCED, metal, fallbackMetal);

        // Energizer part

        this.energizerDisplaySource(builder, CreateContent.Items.ENERGIZER_DISPLAYSOURCE, Content.Items.ENERGIZER_CASING);
    }

    //region internals

    private void reactorDisplaySource(Consumer<FinishedRecipe> builder, ReactorVariant variant,
                                      Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> casing,
                                      TagKey<Item> metal, @Nullable TagKey<Item> fallbackMetal) {
        this.displaySource(builder, this.reactorRoot(variant), result, casing, metal, fallbackMetal);
    }

    private void turbineDisplaySource(Consumer<FinishedRecipe> builder, TurbineVariant variant,
                                      Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> casing,
                                      TagKey<Item> metal, @Nullable TagKey<Item> fallbackMetal) {
        this.displaySource(builder, this.turbineRoot(variant), result, casing, metal, fallbackMetal);
    }

    private void energizerDisplaySource(Consumer<FinishedRecipe> builder,
                                        Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> casing) {
        this.displaySource(builder, this.energizerRoot(), result, casing, Tags.Items.INGOTS_IRON);
    }

    private void displaySource(Consumer<FinishedRecipe> builder, ResourceLocationBuilder idBuilder,
                               Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> casing,
                               TagKey<Item> metal) {

        this.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('C', casing.get())
                .define('M', metal)
                .define('G', Tags.Items.STORAGE_BLOCKS_GOLD)
                .define('Z', Tags.Items.DUSTS_GLOWSTONE)
                .define('X', Content.Items.WRENCH.get())
                .pattern("CZC")
                .pattern("MGM")
                .pattern("CXC")
                .unlockedBy("has_item", has(casing.get()))
                .save(builder, idBuilder.buildWithSuffix("diplaysource"));
    }

    private void displaySource(Consumer<FinishedRecipe> builder, ResourceLocationBuilder idBuilder,
                               Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> casing,
                               TagKey<Item> metal, @Nullable TagKey<Item> fallbackMetal) {

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
