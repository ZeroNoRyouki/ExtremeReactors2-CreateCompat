package it.zerono.mods.extremereactors.compat.create.datagen.tag;

import it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat;
import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.extremereactors.gamecontent.ContentTags;
import it.zerono.mods.zerocore.lib.datagen.provider.tag.IIntrinsicTagDataProvider;
import it.zerono.mods.zerocore.lib.datagen.provider.tag.ModIntrinsicTagAppender;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.NonNullFunction;

public class ItemTagsDataProvider
        implements IIntrinsicTagDataProvider<Item> {

    @Override
    public String getName() {
        return ExtremeReactorsCreateCompat.MOD_NAME + " items tags";
    }

    @Override
    public void build(HolderLookup.Provider registryLookup,
                      NonNullFunction<TagKey<Item>, ModIntrinsicTagAppender<Item>> builder) {

        builder.apply(ContentTags.Items.USING_REACTOR_CASING_BASIC).add(CreateContent.Items.REACTOR_DISPLAYSOURCE_BASIC);
        builder.apply(ContentTags.Items.USING_REACTOR_CASING_REINFORCED).add(CreateContent.Items.REACTOR_DISPLAYSOURCE_REINFORCED);
        builder.apply(ContentTags.Items.USING_TURBINE_CASING_BASIC).add(CreateContent.Items.TURBINE_DISPLAYSOURCE_BASIC);
        builder.apply(ContentTags.Items.USING_TURBINE_CASING_REINFORCED).add(CreateContent.Items.TURBINE_DISPLAYSOURCE_REINFORCED);
    }
}
