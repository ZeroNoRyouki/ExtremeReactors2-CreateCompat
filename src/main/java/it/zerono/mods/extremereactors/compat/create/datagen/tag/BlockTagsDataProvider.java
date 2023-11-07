package it.zerono.mods.extremereactors.compat.create.datagen.tag;

import it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat;
import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.zerocore.lib.datagen.provider.tag.IIntrinsicTagDataProvider;
import it.zerono.mods.zerocore.lib.datagen.provider.tag.ModIntrinsicTagAppender;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.NonNullFunction;

public class BlockTagsDataProvider
        implements IIntrinsicTagDataProvider<Block> {

    @Override
    public String getName() {
        return ExtremeReactorsCreateCompat.MOD_NAME + " blocks tags";
    }

    @Override
    public void build(HolderLookup.Provider registryLookup,
                      NonNullFunction<TagKey<Block>, ModIntrinsicTagAppender<Block>> builder) {

        CreateContent.Blocks.getAll().forEach(s -> {

            builder.apply(BlockTags.MINEABLE_WITH_PICKAXE).add(s);
            builder.apply(BlockTags.NEEDS_IRON_TOOL).add(s);
        });
    }
}
