package it.zerono.mods.extremereactors.compat.create.datagen.loot;

import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.zerocore.lib.datagen.provider.loot.ModBlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;

import java.util.Set;

public class BlockSubProvider
        extends ModBlockLootSubProvider {

    public BlockSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(CreateContent.Blocks.REACTOR_DISPLAYSOURCE_BASIC, CreateContent.Blocks.REACTOR_DISPLAYSOURCE_REINFORCED);
        this.dropSelf(CreateContent.Blocks.TURBINE_DISPLAYSOURCE_BASIC, CreateContent.Blocks.TURBINE_DISPLAYSOURCE_REINFORCED);
    }
}
