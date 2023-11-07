/*
 *
 * ClientInitializer.java
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

package it.zerono.mods.extremereactors.compat.create.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import it.zerono.mods.extremereactors.compat.create.client.model.CreateReactorModelBuilder;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.variant.ReactorVariant;
import it.zerono.mods.zerocore.lib.client.model.ICustomModelBuilder;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInitializer {

    @SubscribeEvent
    public static void onRegisterModels(final ModelEvent.RegisterAdditional event) {
        s_modelBuilders.forEach(b -> b.onRegisterModels(event));
    }

    @SubscribeEvent
    public static void onModelBake(final ModelEvent.ModifyBakingResult event) {
        s_modelBuilders.forEach(builder -> builder.onBakeModels(event));
    }

    //region internals

    private static final List<ICustomModelBuilder> s_modelBuilders = Streams.concat(
                Arrays.stream(ReactorVariant.values()).map(CreateReactorModelBuilder::new)
            )
            .collect(ImmutableList.toImmutableList());

    //endregion
}
