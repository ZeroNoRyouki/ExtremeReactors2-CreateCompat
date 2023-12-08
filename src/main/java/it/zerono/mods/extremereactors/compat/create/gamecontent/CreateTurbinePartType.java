package it.zerono.mods.extremereactors.compat.create.gamecontent;

import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.ITurbinePartType;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.MultiblockTurbine;
import it.zerono.mods.zerocore.base.multiblock.part.GenericDeviceBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartTypeProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.util.NonNullFunction;
import net.minecraftforge.common.util.NonNullSupplier;

public enum CreateTurbinePartType
        implements ITurbinePartType {

    DisplaySource(() -> CreateContent.TileEntityTypes.TURBINE_DISPLAYSOURCE::get, GenericDeviceBlock::new,
            "part.er2create.turbine.displaysource"),

    ;

    CreateTurbinePartType(NonNullSupplier<NonNullSupplier<BlockEntityType<?>>> tileTypeSupplier,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<ITurbinePartType>,
                                  MultiblockPartBlock<MultiblockTurbine, ITurbinePartType>> blockFactory,
                          String translationKey) {
        this(tileTypeSupplier, blockFactory, translationKey, bp -> bp);
    }

    CreateTurbinePartType(NonNullSupplier<NonNullSupplier<BlockEntityType<?>>> tileTypeSupplier,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<ITurbinePartType>,
                                  MultiblockPartBlock<MultiblockTurbine, ITurbinePartType>> blockFactory,
                          String translationKey,
                          NonNullFunction<Block.Properties, Block.Properties> blockPropertiesFixer) {
        this._properties = new MultiblockPartTypeProperties<>(tileTypeSupplier, blockFactory, translationKey, blockPropertiesFixer);
    }

    //region IMultiblockPartType2

    @Override
    public MultiblockPartTypeProperties<MultiblockTurbine, ITurbinePartType> getPartTypeProperties() {
        return this._properties;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }

    //endregion
    //region internals

    private final MultiblockPartTypeProperties<MultiblockTurbine, ITurbinePartType> _properties;

    //endregion
}
