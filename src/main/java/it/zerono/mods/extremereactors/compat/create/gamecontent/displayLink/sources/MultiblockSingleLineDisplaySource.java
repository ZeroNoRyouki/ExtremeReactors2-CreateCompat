/*
 *
 * MultiblockSingleLineDisplaySource.java
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

import com.google.common.base.Preconditions;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.SingleLineDisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.BiFunction;

public class MultiblockSingleLineDisplaySource<Part>
        extends SingleLineDisplaySource {

    public MultiblockSingleLineDisplaySource(IDisplayLinkContextAdapter<Part> adapter,
                                             BiFunction<Part, DisplayTargetStats, MutableComponent> mapper) {

        this._adapter = Preconditions.checkNotNull(adapter, "Adapter must not be null.");
        this._mapper = Preconditions.checkNotNull(mapper, "Mapper must not be null.");
    }

    //region SingleLineDisplaySource

    @Override
    protected MutableComponent provideLine(DisplayLinkContext context, DisplayTargetStats stats) {
        return this._adapter.invoke(context, stats, this._mapper, EMPTY_LINE);
    }

    @Override
    protected boolean allowsLabeling(DisplayLinkContext context) {
        return true;
    }

    //endregion
    //region internals

    private final IDisplayLinkContextAdapter<Part> _adapter;
    private final BiFunction<Part, DisplayTargetStats, MutableComponent> _mapper;

    //endregion
}
