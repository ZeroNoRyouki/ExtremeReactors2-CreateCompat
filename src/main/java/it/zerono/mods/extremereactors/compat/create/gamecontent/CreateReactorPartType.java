/*
 *
 * CreateReactorPartType.java
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

import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.IReactorPartType;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.MultiblockReactor;
import it.zerono.mods.zerocore.base.multiblock.part.GenericDeviceBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartTypeProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.util.NonNullFunction;
import net.minecraftforge.common.util.NonNullSupplier;

public enum CreateReactorPartType
        implements IReactorPartType {

    DisplaySource(() -> CreateContent.TileEntityTypes.REACTOR_DISPLAYSOURCE::get, GenericDeviceBlock::new,
            "part.er2create.reactor.displaysource"),

    ;


    CreateReactorPartType(NonNullSupplier<NonNullSupplier<BlockEntityType<?>>> tileTypeSupplier,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<IReactorPartType>,
                                  MultiblockPartBlock<MultiblockReactor, IReactorPartType>> blockFactory,
                          String translationKey) {
        this(tileTypeSupplier, blockFactory, translationKey, bp -> bp);
    }

    CreateReactorPartType(NonNullSupplier<NonNullSupplier<BlockEntityType<?>>> tileTypeSupplier,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<IReactorPartType>,
                                  MultiblockPartBlock<MultiblockReactor, IReactorPartType>> blockFactory,
                          String translationKey,
                          NonNullFunction<Block.Properties, Block.Properties> blockPropertiesFixer) {
        this(tileTypeSupplier, blockFactory, translationKey, blockPropertiesFixer, ep -> ep);
    }

    CreateReactorPartType(NonNullSupplier<NonNullSupplier<BlockEntityType<?>>> tileTypeSupplier,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<IReactorPartType>,
                                  MultiblockPartBlock<MultiblockReactor, IReactorPartType>> blockFactory,
                          String translationKey,
                          NonNullFunction<Block.Properties, Block.Properties> blockPropertiesFixer,
                          NonNullFunction<MultiblockPartBlock.MultiblockPartProperties<IReactorPartType>,
                                  MultiblockPartBlock.MultiblockPartProperties<IReactorPartType>> partPropertiesFixer) {
        this._properties = new MultiblockPartTypeProperties<>(tileTypeSupplier, blockFactory, translationKey,
                blockPropertiesFixer, partPropertiesFixer);
    }

    //region IMultiblockPartType2

    @Override
    public MultiblockPartTypeProperties<MultiblockReactor, IReactorPartType> getPartTypeProperties() {
        return this._properties;
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }

    //endregion
    //region internals

    private final MultiblockPartTypeProperties<MultiblockReactor, IReactorPartType> _properties;

    //endregion
}
