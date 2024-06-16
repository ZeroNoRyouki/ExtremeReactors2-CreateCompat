/*
 *
 * EnergizerDisplaySourceEntity.java
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

package it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink;

import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.MultiBlockEnergizer;
import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.part.AbstractEnergizerEntity;
import it.zerono.mods.zerocore.lib.data.WideAmount;
import it.zerono.mods.zerocore.lib.energy.EnergySystem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import static it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat.NOT_AVAILABLE_TEXT;

public class EnergizerDisplaySourceEntity
        extends AbstractEnergizerEntity {

    public EnergizerDisplaySourceEntity(BlockPos position, BlockState blockState) {
        super(CreateContent.TileEntityTypes.ENERGIZER_DISPLAYSOURCE.get(), position, blockState);
    }

    protected int getUpdatedModelVariantIndex() {
        return this.isMachineAssembled() ? 1 : 0;
    }

    public MutableComponent getActivationStatus(DisplayTargetStats stats) {
        return Component.translatable(this.isEnergizerActive() ?
                "gui.bigreactors.energizer.active" : "gui.bigreactors.energizer.inactive");
    }

    public float getEnergyStoredPercentage() {
        return this.evalOnController(controller -> (float) controller.getEnergyStoredPercentage(), 0.0f);
    }

    public MutableComponent getEnergyStored(DisplayTargetStats stats) {
        return this.getEnergyReadout(MultiBlockEnergizer::getEnergyStored);
    }

    public MutableComponent getEnergyCapacity(DisplayTargetStats stats) {
        return this.getEnergyReadout(MultiBlockEnergizer::getCapacity);
    }

    public MutableComponent getEnergyInsertedLastTick(DisplayTargetStats stats) {
        return this.getEnergyReadout(MultiBlockEnergizer::getEnergyInsertedLastTick);
    }

    public MutableComponent getEnergyExtractedLastTick(DisplayTargetStats stats) {
        return this.getEnergyReadout(MultiBlockEnergizer::getEnergyExtractedLastTick);
    }

    public MutableComponent getEnergyIoRate(DisplayTargetStats stats) {
        return this.getEnergyReadout(MultiBlockEnergizer::getEnergyIoRate);
    }

    public List<List<MutableComponent>> getEnergizerStatus(DisplayTargetStats stats) {
        return this.evalOnController(this::getEnergizerStatus, Collections.emptyList());
    }

    //region internals

    private MutableComponent getEnergyReadout(BiFunction<@NotNull MultiBlockEnergizer, @NotNull EnergySystem, @NotNull WideAmount> getter) {
        return Component.literal(this.evalOnController(controller -> {

            final var energySystem = controller.getOutputEnergySystem();
            final var energy = getter.apply(controller, energySystem);

            return energySystem.asHumanReadableNumber(energy);

        }, NOT_AVAILABLE_TEXT));
    }

    private List<List<MutableComponent>> getEnergizerStatus(MultiBlockEnergizer controller) {

        final List<List<MutableComponent>> lines = new ObjectArrayList<>(10);

        lines.add(List.of(Component.translatable("er2create.display_source.energizer.energizer_status")));
        lines.add(DisplaySource.EMPTY);

        final var energySystem = controller.getOutputEnergySystem();
        final var capacity = controller.getCapacity(energySystem);
        final var stored = controller.getEnergyStored(energySystem);
        final var io = controller.getEnergyIoRate(energySystem);
        final var inserted = controller.getEnergyInsertedLastTick(energySystem);
        final var extracted = controller.getEnergyExtractedLastTick(energySystem);
        final double filled = controller.getEnergyStoredPercentage();
        final double energyStoredPercentage = controller.getEnergyStoredPercentage();

        // energy stored
        lines.add(List.of(Component.translatable("er2create.display_source.energizer.energizer_status.value1",
                energySystem.asHumanReadableNumber(stored))));

        // energy I/O
        lines.add(List.of(Component.translatable("er2create.display_source.energizer.energizer_status.value2",
                energySystem.asHumanReadableNumber(io))));

        // storage capacity
        lines.add(List.of(Component.translatable("er2create.display_source.energizer.energizer_status.value3",
                energySystem.asHumanReadableNumber(capacity))));

        // energy stored %
        lines.add(List.of(Component.translatable("er2create.display_source.energizer.energizer_status.value4",
                String.format("%.2f", energyStoredPercentage * 100.0))));

        // energy inserted
        lines.add(List.of(Component.translatable("er2create.display_source.energizer.energizer_status.value5",
                energySystem.asHumanReadableNumber(inserted))));

        // energy extracted
        lines.add(List.of(Component.translatable("er2create.display_source.energizer.energizer_status.value6",
                energySystem.asHumanReadableNumber(extracted))));

        lines.add(DisplaySource.EMPTY);

        lines.add(List.of(Component.translatable(controller.isMachineActive() ?
                "gui.bigreactors.energizer.active" : "gui.bigreactors.energizer.inactive")));

        return lines;
    }

    //endregion
}
