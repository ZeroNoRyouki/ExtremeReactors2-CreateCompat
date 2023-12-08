/*
 *
 * CreateTurbineModelBuilder.java
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

import it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat;
import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateTurbinePartType;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.ITurbinePartType;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.variant.TurbineVariant;

public class CreateTurbineModelBuilder
        extends CreateMultiblockModelBuilder<ITurbinePartType> {

    public CreateTurbineModelBuilder(TurbineVariant variant) {

        super("turbine", variant, ExtremeReactorsCreateCompat.ROOT_LOCATION,
                ExtremeReactorsCreateCompat.ROOT_LOCATION.appendPath("block", "turbine"));

        this.addBlockWithVariants(CreateTurbinePartType.DisplaySource, variant, $ -> true, "displaysource");
    }
}
