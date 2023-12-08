/*
 *
 * CreateMultiblockModelBuilder.java
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

package it.zerono.mods.extremereactors.compat.create.client.model;

import it.zerono.mods.extremereactors.ExtremeReactors;
import it.zerono.mods.extremereactors.gamecontent.multiblock.common.client.model.AbstractMultiblockModelBuilder;
import it.zerono.mods.extremereactors.gamecontent.multiblock.common.variant.IMultiblockGeneratorVariant;
import it.zerono.mods.zerocore.lib.block.multiblock.IMultiblockPartType;
import it.zerono.mods.zerocore.lib.data.ResourceLocationBuilder;

public abstract class CreateMultiblockModelBuilder<PartType extends IMultiblockPartType>
        extends AbstractMultiblockModelBuilder<PartType> {

    protected CreateMultiblockModelBuilder(String multiblockShortName, IMultiblockGeneratorVariant variant,
                                           ResourceLocationBuilder root, ResourceLocationBuilder blockRoot) {
        super(multiblockShortName, root, blockRoot, ExtremeReactors.ROOT_LOCATION
                .appendPath("block", "reactor", variant.getName())
                .buildWithSuffix("assembledplating"), true);
    }
}
