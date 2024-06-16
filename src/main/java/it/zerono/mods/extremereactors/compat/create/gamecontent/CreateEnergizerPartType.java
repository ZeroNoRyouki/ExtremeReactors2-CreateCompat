/*
 *
 * CreateEnergizerPartType.java
 *
 * This file is part of Extreme Reactors Create Compat by ZeroNoRyouki, a Minecraft mod.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * DO NOT REMOVE OR EDIT THIS HEADER
 *
 */

package it.zerono.mods.extremereactors.compat.create.gamecontent;

import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.IEnergizerPartType;
import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.MultiBlockEnergizer;
import it.zerono.mods.zerocore.base.multiblock.part.GenericDeviceBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartTypeProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.util.NonNullFunction;
import net.minecraftforge.common.util.NonNullSupplier;

public enum CreateEnergizerPartType
        implements IEnergizerPartType {


    DisplaySource(() -> CreateContent.TileEntityTypes.ENERGIZER_DISPLAYSOURCE::get, GenericDeviceBlock::new,
            "part.er2create.energizer.displaysource"),

    ;

    CreateEnergizerPartType(NonNullSupplier<NonNullSupplier<BlockEntityType<?>>> tileTypeSupplier,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<IEnergizerPartType>,
                                  MultiblockPartBlock<MultiBlockEnergizer, IEnergizerPartType>> blockFactory,
                          String translationKey) {
        this(tileTypeSupplier, blockFactory, translationKey, bp -> bp);
    }

    CreateEnergizerPartType(NonNullSupplier<NonNullSupplier<BlockEntityType<?>>> tileTypeSupplier,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<IEnergizerPartType>,
                                  MultiblockPartBlock<MultiBlockEnergizer, IEnergizerPartType>> blockFactory,
                          String translationKey,
                          NonNullFunction<Block.Properties, Block.Properties> blockPropertiesFixer) {
        this(tileTypeSupplier, blockFactory, translationKey, blockPropertiesFixer, ep -> ep);
    }

    CreateEnergizerPartType(NonNullSupplier<NonNullSupplier<BlockEntityType<?>>> tileTypeSupplier,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<IEnergizerPartType>,
                                  MultiblockPartBlock<MultiBlockEnergizer, IEnergizerPartType>> blockFactory,
                          String translationKey,
                          NonNullFunction<Block.Properties, Block.Properties> blockPropertiesFixer,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<IEnergizerPartType>,
                                  MultiblockPartBlock.MultiblockPartProperties<IEnergizerPartType>> partPropertiesFixer) {
        this._properties = new MultiblockPartTypeProperties<>(tileTypeSupplier, blockFactory, translationKey,
                blockPropertiesFixer, partPropertiesFixer);
    }

    //region IMultiblockPartType2

    @Override
    public MultiblockPartTypeProperties<MultiBlockEnergizer, IEnergizerPartType> getPartTypeProperties() {
        return this._properties;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }

    //endregion
    //region internals

    private final MultiblockPartTypeProperties<MultiBlockEnergizer, IEnergizerPartType> _properties;

    //endregion
}
