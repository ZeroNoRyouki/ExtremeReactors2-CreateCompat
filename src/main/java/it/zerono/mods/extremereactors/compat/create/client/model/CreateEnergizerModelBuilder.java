/*
 *
 * CreateEnergizerModelBuilder.java
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
import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.client.model.EnergizerModelBuilder;
import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.variant.EnergizerVariant;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class CreateEnergizerModelBuilder
        extends EnergizerModelBuilder {

    public CreateEnergizerModelBuilder(EnergizerVariant ignored) {
    }

    @Override
    protected void build() {

        final Function<String, ResourceLocation> modelToReplaceIdGetter = blockName ->
                new ModelResourceLocation(ExtremeReactorsCreateCompat.ROOT_LOCATION.buildWithSuffix("energizer" + blockName), "");

        final Function<String, ResourceLocation> variantModelIdGetter = modelName ->
                ExtremeReactorsCreateCompat.ROOT_LOCATION.appendPath("block", "energizer").buildWithSuffix(modelName);

        this.addBlockWithVariants(modelToReplaceIdGetter, variantModelIdGetter, CreateTurbinePartType.DisplaySource,
                "displaysource",
                "displaysource_assembled");
    }

//    protected void addBlockWithVariants(CreateTurbinePartType partType, String blockCommonName,
//                                        String... additionalVariantsModelNames) {
//
//        this.addBlock(partType.ordinal(), getBlockStateRL(blockCommonName), 0, false);
//        this.addBlockVariants(partType, blockCommonName, additionalVariantsModelNames);
//    }
//
//    protected void addBlockVariants(CreateTurbinePartType partType, String blockCommonName,
//                                    String... additionalVariantsModelNames) {
//
//        final List<ResourceLocation> variants = Lists.newArrayListWithCapacity(1 + additionalVariantsModelNames.length);
//
//        variants.add(getBlockStateRL(blockCommonName));
//        Arrays.stream(additionalVariantsModelNames)
//                .map(EnergizerModelBuilder::getModelRL)
//                .collect(Collectors.toCollection(() -> variants));
//
//        this.addModels(partType.ordinal(), variants);
//    }
//
//    @Override
//    protected ResourceLocation getBlockStateRL(String blockCommonName, String blockStateVariant) {
//        return new ModelResourceLocation(ExtremeReactorsCreateCompat.ROOT_LOCATION.buildWithSuffix("energizer" + blockCommonName), blockStateVariant);
//    }
//
//
//
//    protected void addBlockWithVariants(EnergizerPartType partType, String blockCommonName,
//                                        String... additionalVariantsModelNames) {
//
//        this.addBlock(partType.ordinal(), getBlockStateRL(blockCommonName), 0, false);
//        this.addBlockVariants(partType, blockCommonName, additionalVariantsModelNames);
//    }
//
//    protected ResourceLocation getBlockStateRL(String blockCommonName) {
//        return getBlockStateRL(blockCommonName, "");
//    }
//
//    protected ResourceLocation getBlockStateRL(String blockCommonName, String blockStateVariant) {
//        return new ModelResourceLocation(ExtremeReactors.ROOT_LOCATION.buildWithSuffix("energizer" + blockCommonName), blockStateVariant);
//    }
//
//    protected static ResourceLocation getModelRL(String modelName) {
//        return ExtremeReactors.ROOT_LOCATION.appendPath("block", "energizer").buildWithSuffix(modelName);
//    }
//
//    protected void addBlockVariants(EnergizerPartType partType, String blockCommonName,
//                                    String... additionalVariantsModelNames) {
//
//        final List<ResourceLocation> variants = Lists.newArrayListWithCapacity(1 + additionalVariantsModelNames.length);
//
//        variants.add(getBlockStateRL(blockCommonName));
//        Arrays.stream(additionalVariantsModelNames)
//                .map(CreateEnergizerModelBuilder::getModelRL)
//                .collect(Collectors.toCollection(() -> variants));
//
//        this.addModels(partType.ordinal(), variants);
//    }


}
