/*
 *
 * IDisplayLinkContextAdapter.java
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

package it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.sources;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.ReactorDisplaySourceEntity;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.TurbineDisplaySourceEntity;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface IDisplayLinkContextAdapter<Part> {

    IDisplayLinkContextAdapter<ReactorDisplaySourceEntity> REACTOR = () -> ReactorDisplaySourceEntity.class;
    IDisplayLinkContextAdapter<TurbineDisplaySourceEntity> TURBINE = () -> TurbineDisplaySourceEntity.class;

    Class<Part> getMultiblockPartClass();

    default Optional<Part> getMultiblockPart(DisplayLinkContext context) {

        final var clazz = this.getMultiblockPartClass();
        final var be = context.getSourceBlockEntity();

        if (clazz.isInstance(be)) {
            return Optional.of(clazz.cast(be));
        } else {
            return Optional.empty();
        }
    }

    default <R> R invoke(DisplayLinkContext context, Function<Part, R> mapper, R defaultValue) {
        return this.getMultiblockPart(context)
                .map(mapper)
                .orElse(defaultValue);
    }

    default <R> R invoke(DisplayLinkContext context, DisplayTargetStats stats,
                         BiFunction<Part, DisplayTargetStats, R> mapper, R defaultValue) {
        return this.getMultiblockPart(context)
                .map(part -> mapper.apply(part, stats))
                .orElse(defaultValue);
    }
}
