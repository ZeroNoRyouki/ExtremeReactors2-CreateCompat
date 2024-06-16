package it.zerono.mods.extremereactors.compat.create.datagen.client;

import it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat;
import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.extremereactors.datagen.client.AbstractMultiblockModelsDataProvider;
import it.zerono.mods.zerocore.lib.data.ResourceLocationBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class MultiblockModelsDataProvider
        extends AbstractMultiblockModelsDataProvider {

    public MultiblockModelsDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                        ResourceLocationBuilder modLocationRoot) {
        super(ExtremeReactorsCreateCompat.MOD_NAME + " Multiblock block states and models", output, lookupProvider, modLocationRoot);
    }

    //region BlockStateDataProvider

    @Override
    public void provideData() {

        String variant;

        //region basic

        variant = "reactor/basic";
        this.displaySource(CreateContent.Blocks.REACTOR_DISPLAYSOURCE_BASIC, variant);

        variant = "turbine/basic";
        this.displaySource(CreateContent.Blocks.TURBINE_DISPLAYSOURCE_BASIC, variant);

        //endregion
        //region reinforced

        variant = "reactor/reinforced";
        this.displaySource(CreateContent.Blocks.REACTOR_DISPLAYSOURCE_REINFORCED, variant);

        variant = "turbine/reinforced";
        this.displaySource(CreateContent.Blocks.TURBINE_DISPLAYSOURCE_REINFORCED, variant);

        //endregion
        //region energizer

        this.displaySource(CreateContent.Blocks.ENERGIZER_DISPLAYSOURCE, "energizer");

        //endregion
    }

    //endregion
    //region internals

    protected <B extends Block> void displaySource(final Supplier<? extends Block> block, final String subFolder) {
        this.genericPart(block, "displaysource", subFolder, "_assembled");
    }

    //endregion
}
