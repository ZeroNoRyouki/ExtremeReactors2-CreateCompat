package it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink;

import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.zerono.mods.extremereactors.compat.create.gamecontent.CreateContent;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.MultiblockTurbine;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.part.AbstractTurbineEntity;
import it.zerono.mods.zerocore.lib.CodeHelper;
import it.zerono.mods.zerocore.lib.data.WideAmount;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.List;

import static it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat.NOT_AVAILABLE_TEXT;

public class TurbineDisplaySourceEntity
        extends AbstractTurbineEntity {

    public TurbineDisplaySourceEntity(BlockPos position, BlockState blockState) {
        super(CreateContent.TileEntityTypes.TURBINE_DISPLAYSOURCE.get(), position, blockState);
    }

    public MutableComponent getActivationStatus(DisplayTargetStats stats) {
        return Component.translatable(this.isTurbineActive() ?
                "gui.bigreactors.turbine.active" : "gui.bigreactors.turbine.inactive");
    }

    public float getEnergyStoredPercentage() {
        return this.evalOnController(controller -> (float) controller.getEnergyStoredPercentage(), 0.0f);
    }

    public MutableComponent getEnergyStored(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller -> {

            final var energySystem = controller.getOutputEnergySystem();
            final double energyStored = controller.getEnergyStored(energySystem).doubleValue();

            return CodeHelper.formatAsHumanReadableNumber(energyStored, energySystem.getUnit());

        }, NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getVaporStored(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                CodeHelper.formatAsMillibuckets(controller.getVaporAmount()), NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getCoolantStored(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                CodeHelper.formatAsMillibuckets(controller.getCoolantAmount()), NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getEnergyGenerated(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller -> {

            final var energySystem = controller.getOutputEnergySystem();
            final double energyOutput = controller.getEnergyGeneratedLastTick();

            return CodeHelper.formatAsHumanReadableNumber(energyOutput, energySystem.getUnit()) + "/t";

        }, NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getMaxIntakeRate(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                String.format("%d mB/t", controller.getMaxIntakeRate()), NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getRotorSpeed(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                String.format("%.2f RPM", controller.getRotorSpeed()), NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getVentSetting(DisplayTargetStats stats) {
        return this.evalOnControllerOrGet(this::getVentSettingText, () -> Component.literal(NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getInductorMode(DisplayTargetStats stats) {
        return this.evalOnControllerOrGet(this::getInductorMode, () -> Component.literal(NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getRotorBladesCount(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                String.format("%d", controller.getRotorBladesCount()), NOT_AVAILABLE_TEXT));
    }

    public MutableComponent getRotorEfficiency(DisplayTargetStats stats) {
        return Component.literal(this.evalOnController(controller ->
                String.format("%.1f%%", Mth.clamp(controller.getRotorEfficiencyLastTick(), 0.0f, 1.0f) * 100),
                NOT_AVAILABLE_TEXT));
    }

    public List<List<MutableComponent>> getTurbineStatus(DisplayTargetStats stats) {
        return this.evalOnController(this::getTurbineStatus, Collections.emptyList());
    }

    //region internals

    private List<List<MutableComponent>> getTurbineStatus(MultiblockTurbine controller) {

        final List<List<MutableComponent>> lines = new ObjectArrayList<>(9);

        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status")));
        lines.add(DisplaySource.EMPTY);

        final var energySystem = controller.getOutputEnergySystem();
        final double energyOutput = controller.getEnergyGeneratedLastTick();
        final WideAmount energyStored = controller.getEnergyStored(energySystem);
        final double energyStoredPercentage = controller.getEnergyStoredPercentage();

        // energy generated
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value1",
                CodeHelper.formatAsHumanReadableNumber(energyOutput, energySystem.getUnit()) + "/t")));

        // energy stored
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value2",
                CodeHelper.formatAsHumanReadableNumber(energyStored.doubleValue(), energySystem.getUnit()))));

        // energy stored %
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value3",
                String.format("%.2f", energyStoredPercentage * 100.0))));

        lines.add(DisplaySource.EMPTY);

        // vapor stored
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value4",
                CodeHelper.formatAsMillibuckets(controller.getVaporAmount()))));

        // coolant stored
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value5",
                CodeHelper.formatAsMillibuckets(controller.getCoolantAmount()))));

        // vent settings
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value6")
                        .append(this.getVentSettingText(controller))));

        lines.add(DisplaySource.EMPTY);

        // rpm
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value7",
                String.format("%.2f", controller.getRotorSpeed()))));

        // vapor intake rate
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value8",
                String.format("%d", controller.getMaxIntakeRate()))));

        // inductors coils on/off
        lines.add(List.of(Component.translatable("er2create.display_source.turbine.turbine_status.value9")
                .append(this.getInductorMode(controller))));

        lines.add(DisplaySource.EMPTY);

        lines.add(List.of(Component.translatable(controller.isMachineActive() ?
                "gui.bigreactors.turbine.active" : "gui.bigreactors.turbine.inactive")));

        return lines;
    }

    private MutableComponent getVentSettingText(MultiblockTurbine controller) {
        return switch (controller.getVentSetting()) {
            case VentAll -> Component.translatable("gui.bigreactors.turbine.controller.vent.all.line1");
            case VentOverflow -> Component.translatable("gui.bigreactors.turbine.controller.vent.overflow.line1");
            case DoNotVent -> Component.translatable("gui.bigreactors.turbine.controller.vent.donotvent.line1");
        };
    }

    private MutableComponent getInductorMode(MultiblockTurbine controller) {
        return Component.translatable(controller.isInductorEngaged() ?
                "gui.bigreactors.turbine.controller.inductor.mode.engaged" : "gui.bigreactors.turbine.controller.inductor.mode.disengaged");
    }

    //endregion
}
