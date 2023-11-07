/*
 *
 * ReactorDisplaySourceEntity.java
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
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.MultiblockReactor;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.part.AbstractReactorEntity;
import it.zerono.mods.zerocore.lib.CodeHelper;
import it.zerono.mods.zerocore.lib.data.WideAmount;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.List;

public class ReactorDisplaySourceEntity
        extends AbstractReactorEntity {

    public ReactorDisplaySourceEntity(BlockPos position, BlockState blockState) {
        super(CreateContent.TileEntityTypes.REACTOR_DISPLAYSOURCE.get(), position, blockState);
    }

    public float getEnergyStoredPercentage() {
        return this.evalOnController(controller ->
                controller.getOperationalMode().isPassive() ?
                        (float) controller.getEnergyStoredPercentage() : 0.0f, 0.0f);
    }

    public MutableComponent getEnergyStored(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller -> {

            if (controller.getOperationalMode().isActive()) {
                return "0";
            }

            final var energySystem = controller.getOutputEnergySystem();
            final double energyStored = controller.getEnergyStored(energySystem).doubleValue();

            return CodeHelper.formatAsHumanReadableNumber(energyStored, energySystem.getUnit());

        }, NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getVaporStored(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller -> {

            if (controller.getOperationalMode().isPassive()) {
                return "0";
            }

            return CodeHelper.formatAsMillibuckets(controller.getVaporAmount());

        }, NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getCoolantStored(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller -> {

            if (controller.getOperationalMode().isPassive()) {
                return "0";
            }

            return CodeHelper.formatAsMillibuckets(controller.getCoolantAmount());

        }, NOT_AVAILABLE_TEXT));
    }

    public float getFuelPercentage() {
        return this.evalOnController(controller ->
                (float) controller.getFuelAmount() / (float) controller.getCapacity(), 0.0f);
    }

    public MutableComponent getFuelAmount(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                CodeHelper.formatAsMillibuckets(controller.getFuelAmount()), NOT_AVAILABLE_TEXT));
    }

    public float getWastePercentage() {
        return this.evalOnController(controller ->
                (float) controller.getWasteAmount() / (float) controller.getCapacity(), 0.0f);
    }

    public MutableComponent getWasteAmount(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                CodeHelper.formatAsMillibuckets(controller.getWasteAmount()), NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getCoreHeatTemperature(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                String.format("%.0f C", controller.getFuelHeatValue().getAsDouble()), NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getCasingHeatTemperature(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                String.format("%.0f C", controller.getReactorHeatValue().getAsDouble()), NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getFuelBurnupRate(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                CodeHelper.formatAsMillibuckets(controller.getUiStats().getFuelConsumedLastTick()) + "/t", NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getEnergyGenerated(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller -> {

            if (controller.getOperationalMode().isActive()) {
                return "0";
            }

            final var energySystem = controller.getOutputEnergySystem();
            final double energyOutput = controller.getUiStats().getAmountGeneratedLastTick();

            return CodeHelper.formatAsHumanReadableNumber(energyOutput, energySystem.getUnit()) + "/t";

        }, NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getVaporGenerated(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller -> {

            if (controller.getOperationalMode().isPassive()) {
                return "0";
            }

            return CodeHelper.formatAsHumanReadableNumber(controller.getUiStats().getAmountGeneratedLastTick() / 1000.0, "B/t");

        }, NOT_AVAILABLE_TEXT));
    }

    public List<List<MutableComponent>> getReactorStatus(DisplayTargetStats stats) {
        return this.evalOnController(this::getReactorStatus, Collections.emptyList());
    }

    public List<List<MutableComponent>> getCoreFuelStatus(DisplayTargetStats stats) {
        return this.evalOnController(this::getCoreFuelStatus, Collections.emptyList());
    }

    //region internals

    private List<List<MutableComponent>> getReactorStatus(MultiblockReactor controller) {

        final List<List<MutableComponent>> lines = new ObjectArrayList<>(9);

        lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status")));
        lines.add(List.of(DisplaySource.EMPTY_LINE));

        if (controller.getOperationalMode().isPassive()) {

            final var energySystem = controller.getOutputEnergySystem();
            final double energyOutput = controller.getUiStats().getAmountGeneratedLastTick();
            final WideAmount energyStored = controller.getEnergyStored(energySystem);
            final double energyStoredPercentage = controller.getEnergyStoredPercentage();

            // energy generated
            lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status.value1.passive",
                    CodeHelper.formatAsHumanReadableNumber(energyOutput, energySystem.getUnit()) + "/t")));

            // energy stored
            lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status.value2.passive",
                    CodeHelper.formatAsHumanReadableNumber(energyStored.doubleValue(), energySystem.getUnit()))));

            // energy stored %
            lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status.value3.passive",
                    String.format("%.2f", energyStoredPercentage * 100.0))));

        } else {

            final double vaporOutput = controller.getUiStats().getAmountGeneratedLastTick();
            final int vaporAmount = controller.getVaporAmount();
            final int coolantAmount = controller.getCoolantAmount();

            // vapor generated
            lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status.value1.active",
                    CodeHelper.formatAsHumanReadableNumber(vaporOutput / 1000.0, "B/t"))));

            // vapor amount
            lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status.value2.active",
                    CodeHelper.formatAsMillibuckets(vaporAmount))));

            // coolant amount
            lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status.value3.active",
                    CodeHelper.formatAsMillibuckets(coolantAmount))));
        }

        lines.add(List.of(DisplaySource.EMPTY_LINE));

        final float fuelConsumed = controller.getUiStats().getFuelConsumedLastTick();
        final double fuelHeat = controller.getFuelHeatValue().getAsDouble();

        // fuel burnup rate
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status.value4",
                CodeHelper.formatAsMillibuckets(fuelConsumed) + "/t")));

        // core heat
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.reactor_status.value5",
                String.format("%.0f C", fuelHeat))));

        return lines;
    }

    private List<List<MutableComponent>> getCoreFuelStatus(MultiblockReactor controller) {

        final List<List<MutableComponent>> lines = new ObjectArrayList<>(9);

        lines.add(List.of(Component.translatable("er2create.display_source.reactor.core_fuel_status")));

        final float capacity = controller.getCapacity();
        final int fuelAmount = controller.getFuelAmount();
        final int wasteAmount = controller.getWasteAmount();
        final float reactantsAmount = fuelAmount + wasteAmount;

        // % full
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.core_fuel_status.value1",
                String.format("%.2f", reactantsAmount / capacity * 100.0f))));
        // % depleted
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.core_fuel_status.value2",
                String.format("%.2f", wasteAmount / reactantsAmount * 100.0f))));

        lines.add(List.of(DisplaySource.EMPTY_LINE));

        // rods count
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.core_fuel_status.value3",
                controller.getFuelRodsCount())));

        // max capacity
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.core_fuel_status.value4",
                CodeHelper.formatAsMillibuckets(capacity))));

        // fuel amount
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.core_fuel_status.value5",
                CodeHelper.formatAsMillibuckets(fuelAmount))));

        // waste amount
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.core_fuel_status.value6",
                CodeHelper.formatAsMillibuckets(wasteAmount))));

        // total
        lines.add(List.of(Component.translatable("er2create.display_source.reactor.core_fuel_status.value7",
                CodeHelper.formatAsMillibuckets(capacity))));

        return lines;
    }

    private static final String NOT_AVAILABLE_TEXT = "?";

    //endregion
}
