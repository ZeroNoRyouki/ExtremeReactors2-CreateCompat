/*
 *
 * CreateContent.java
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

import com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours;
import com.simibubi.create.content.redstone.displayLink.source.DisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import it.zerono.mods.extremereactors.ExtremeReactors;
import it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.EnergizerDisplaySourceEntity;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.ReactorDisplaySourceEntity;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.TurbineDisplaySourceEntity;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.sources.IDisplayLinkContextAdapter;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.sources.MultiblockMultiLineDisplaySource;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.sources.MultiblockPercentOrProgressBarDisplaySource;
import it.zerono.mods.extremereactors.compat.create.gamecontent.displayLink.sources.MultiblockSingleLineDisplaySource;
import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.IEnergizerPartType;
import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.MultiBlockEnergizer;
import it.zerono.mods.extremereactors.gamecontent.multiblock.energizer.variant.EnergizerVariant;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.IReactorPartType;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.MultiblockReactor;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reactor.variant.ReactorVariant;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.ITurbinePartType;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.MultiblockTurbine;
import it.zerono.mods.extremereactors.gamecontent.multiblock.turbine.variant.TurbineVariant;
import it.zerono.mods.zerocore.base.multiblock.part.GenericDeviceBlock;
import it.zerono.mods.zerocore.lib.block.ModBlock;
import it.zerono.mods.zerocore.lib.block.multiblock.MultiblockPartBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CreateContent {

    public static void initialize() {

        final IEventBus bus = Mod.EventBusSubscriber.Bus.MOD.bus().get();

        Blocks.initialize(bus);
        DisplaySources.initialize(bus);
        Items.initialize(bus);
        TileEntityTypes.initialize(bus);
        CreativeTabs.initialize(bus);
    }

    public static final class Blocks {

        private static final DeferredRegister<Block> BLOCKS = defer(ForgeRegistries.BLOCKS);

        static void initialize(final IEventBus bus) {

            BLOCKS.register(bus);
        }

        public static Collection<RegistryObject<Block>> getAll() {
            return BLOCKS.getEntries();
        }

        public static final RegistryObject<GenericDeviceBlock<MultiblockReactor, IReactorPartType>> REACTOR_DISPLAYSOURCE_BASIC =
                registerReactorBlock("basic_reactordisplaysource", ReactorVariant.Basic, CreateReactorPartType.DisplaySource);

        public static final RegistryObject<GenericDeviceBlock<MultiblockReactor, IReactorPartType>> REACTOR_DISPLAYSOURCE_REINFORCED =
                registerReactorBlock("reinforced_reactordisplaysource", ReactorVariant.Reinforced, CreateReactorPartType.DisplaySource);

        public static final RegistryObject<GenericDeviceBlock<MultiblockTurbine, ITurbinePartType>> TURBINE_DISPLAYSOURCE_BASIC =
                registerTurbineBlock("basic_turbinedisplaysource", TurbineVariant.Basic, CreateTurbinePartType.DisplaySource);

        public static final RegistryObject<GenericDeviceBlock<MultiblockTurbine, ITurbinePartType>> TURBINE_DISPLAYSOURCE_REINFORCED =
                registerTurbineBlock("reinforced_turbinedisplaysource", TurbineVariant.Reinforced, CreateTurbinePartType.DisplaySource);

        public static final RegistryObject<GenericDeviceBlock<MultiBlockEnergizer, IEnergizerPartType>> ENERGIZER_DISPLAYSOURCE =
                registerEnergizerBlock("energizerdisplaysource", CreateEnergizerPartType.DisplaySource);

        //region internals

        @SuppressWarnings("unchecked")
        private static <T extends MultiblockPartBlock<MultiblockReactor, IReactorPartType>>
        RegistryObject<T> registerReactorBlock(String name, ReactorVariant variant, IReactorPartType partType) {
            return BLOCKS.register(name, () -> (T) (partType.createBlock(variant)));
        }

        @SuppressWarnings("unchecked")
        private static <T extends MultiblockPartBlock<MultiblockTurbine, ITurbinePartType>>
        RegistryObject<T> registerTurbineBlock(String name, TurbineVariant variant, ITurbinePartType partType) {
            return BLOCKS.register(name, () -> (T) (partType.createBlock(variant)));
        }

        @SuppressWarnings("unchecked")
        private static <T extends MultiblockPartBlock<MultiBlockEnergizer, IEnergizerPartType>>
        RegistryObject<T> registerEnergizerBlock(String name, IEnergizerPartType partType) {
            return BLOCKS.register(name, () -> (T) (partType.createBlock(EnergizerVariant.INSTANCE)));
        }

        //endregion
    }

    @SuppressWarnings("unused")
    public static final class Items {

        private static final DeferredRegister<Item> ITEMS = defer(ForgeRegistries.ITEMS);

        static void initialize(final IEventBus bus) {
            ITEMS.register(bus);
        }

        public static final RegistryObject<BlockItem> REACTOR_DISPLAYSOURCE_BASIC =
                registerItemBlock("basic_reactordisplaysource", () -> Blocks.REACTOR_DISPLAYSOURCE_BASIC::get);

        public static final RegistryObject<BlockItem> REACTOR_DISPLAYSOURCE_REINFORCED =
                registerItemBlock("reinforced_reactordisplaysource", () -> Blocks.REACTOR_DISPLAYSOURCE_REINFORCED::get);

        public static final RegistryObject<BlockItem> TURBINE_DISPLAYSOURCE_BASIC =
                registerItemBlock("basic_turbinedisplaysource", () -> Blocks.TURBINE_DISPLAYSOURCE_BASIC::get);

        public static final RegistryObject<BlockItem> TURBINE_DISPLAYSOURCE_REINFORCED =
                registerItemBlock("reinforced_turbinedisplaysource", () -> Blocks.TURBINE_DISPLAYSOURCE_REINFORCED::get);

        public static final RegistryObject<BlockItem> ENERGIZER_DISPLAYSOURCE =
                registerItemBlock("energizerdisplaysource", () -> Blocks.ENERGIZER_DISPLAYSOURCE::get);

        //region internals

        private static RegistryObject<BlockItem> registerItemBlock(String name, Supplier<Supplier<ModBlock>> blockSupplier) {
            return ITEMS.register(name,
                    () -> blockSupplier.get().get().createBlockItem(new Item.Properties().stacksTo(64)));
        }

        //endregion
    }

    public static final class TileEntityTypes {

        private static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = defer(ForgeRegistries.BLOCK_ENTITY_TYPES);

        static void initialize(final IEventBus bus) {
            TILE_ENTITIES.register(bus);
        }

        public static final RegistryObject<BlockEntityType<ReactorDisplaySourceEntity>> REACTOR_DISPLAYSOURCE =
                registerBlockEntity("reactordisplaysource", ReactorDisplaySourceEntity::new,
                        () -> Blocks.REACTOR_DISPLAYSOURCE_BASIC::get,
                        () -> Blocks.REACTOR_DISPLAYSOURCE_REINFORCED::get);

        public static final RegistryObject<BlockEntityType<TurbineDisplaySourceEntity>> TURBINE_DISPLAYSOURCE =
                registerBlockEntity("turbinedisplaysource", TurbineDisplaySourceEntity::new,
                        () -> Blocks.TURBINE_DISPLAYSOURCE_BASIC::get,
                        () -> Blocks.TURBINE_DISPLAYSOURCE_REINFORCED::get);

        public static final RegistryObject<BlockEntityType<EnergizerDisplaySourceEntity>> ENERGIZER_DISPLAYSOURCE =
                registerBlockEntity("energizerdisplaysource", EnergizerDisplaySourceEntity::new,
                        () -> Blocks.ENERGIZER_DISPLAYSOURCE::get);

        //region internals

        @SuppressWarnings("ConstantConditions")
        @SafeVarargs
        private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>>
        registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> factory,
                            Supplier<Supplier<Block>>... validBlockSuppliers) {
            return TILE_ENTITIES.register(name, () -> {

                final Block[] validBlocks = new Block[validBlockSuppliers.length];

                if (validBlockSuppliers.length > 0) {
                    for (int i = 0; i < validBlockSuppliers.length; ++i) {
                        validBlocks[i] = validBlockSuppliers[i].get().get();
                    }
                }

                return BlockEntityType.Builder.of(factory, validBlocks).build(null);
            });
        }

        //endregion
    }

    public static final class DisplaySources {

        static void initialize(final IEventBus bus) {
            bus.addListener(EventPriority.LOWEST, DisplaySources::onRegister);
        }

        //region internals

        private static void onRegister(RegisterEvent event) {

            if (event.getRegistryKey().equals(Registries.BLOCK)) {

                reactorMultiLineSource("reactor_status", ReactorDisplaySourceEntity::getReactorStatus);
                reactorMultiLineSource("core_fuel_status", ReactorDisplaySourceEntity::getCoreFuelStatus);
                reactorSingleLineSource("active", ReactorDisplaySourceEntity::getActivationStatus);
                reactorSingleLineSource("fuel_amount", ReactorDisplaySourceEntity::getFuelAmount);
                reactorPercentageSource("fuel_percentage", ReactorDisplaySourceEntity::getFuelPercentage);
                reactorSingleLineSource("waste_amount", ReactorDisplaySourceEntity::getWasteAmount);
                reactorPercentageSource("waste_percentage", ReactorDisplaySourceEntity::getWastePercentage);
                reactorSingleLineSource("energy_stored", ReactorDisplaySourceEntity::getEnergyStored);
                reactorPercentageSource("energy_percentage", ReactorDisplaySourceEntity::getEnergyStoredPercentage);
                reactorSingleLineSource("vapor_stored", ReactorDisplaySourceEntity::getVaporStored);
                reactorSingleLineSource("coolant_stored", ReactorDisplaySourceEntity::getCoolantStored);
                reactorSingleLineSource("core_temperature", ReactorDisplaySourceEntity::getCoreHeatTemperature);
                reactorSingleLineSource("casing_temperature", ReactorDisplaySourceEntity::getCasingHeatTemperature);
                reactorSingleLineSource("fuel_burnup_rate", ReactorDisplaySourceEntity::getFuelBurnupRate);
                reactorSingleLineSource("energy_generated", ReactorDisplaySourceEntity::getEnergyGenerated);
                reactorSingleLineSource("vapor_generated", ReactorDisplaySourceEntity::getVaporGenerated);

                turbineMultiLineSource("turbine_status", TurbineDisplaySourceEntity::getTurbineStatus);
                turbineSingleLineSource("active", TurbineDisplaySourceEntity::getActivationStatus);
                turbineSingleLineSource("energy_stored", TurbineDisplaySourceEntity::getEnergyStored);
                turbinePercentageSource("energy_percentage", TurbineDisplaySourceEntity::getEnergyStoredPercentage);
                turbineSingleLineSource("vapor_stored", TurbineDisplaySourceEntity::getVaporStored);
                turbineSingleLineSource("coolant_stored", TurbineDisplaySourceEntity::getCoolantStored);
                turbineSingleLineSource("energy_generated", TurbineDisplaySourceEntity::getEnergyGenerated);
                turbineSingleLineSource("vapor_max_intake_rate", TurbineDisplaySourceEntity::getMaxIntakeRate);
                turbineSingleLineSource("rotor_speed", TurbineDisplaySourceEntity::getRotorSpeed);
                turbineSingleLineSource("rotor_blades_count", TurbineDisplaySourceEntity::getRotorBladesCount);
                turbineSingleLineSource("rotor_efficiency", TurbineDisplaySourceEntity::getRotorEfficiency);
                turbineSingleLineSource("vent_settings", TurbineDisplaySourceEntity::getVentSetting);
                turbineSingleLineSource("inductor_mode", TurbineDisplaySourceEntity::getInductorMode);

                energizerMultiLineSource("energizer_status", EnergizerDisplaySourceEntity::getEnergizerStatus);
                energizerPercentageSource("energy_percentage", EnergizerDisplaySourceEntity::getEnergyStoredPercentage);
                energizerSingleLineSource("energy_stored", EnergizerDisplaySourceEntity::getEnergyStored);
                energizerSingleLineSource("active", EnergizerDisplaySourceEntity::getActivationStatus);
                energizerSingleLineSource("energy_capacity", EnergizerDisplaySourceEntity::getEnergyCapacity);
                energizerSingleLineSource("energy_inserted", EnergizerDisplaySourceEntity::getEnergyInsertedLastTick);
                energizerSingleLineSource("energy_extracted", EnergizerDisplaySourceEntity::getEnergyExtractedLastTick);
                energizerSingleLineSource("energy_io", EnergizerDisplaySourceEntity::getEnergyIoRate);
            }
        }

        private static void reactorPercentageSource(String name, Function<ReactorDisplaySourceEntity, Float> mapper) {
            reactorSource(name, new MultiblockPercentOrProgressBarDisplaySource<>(IDisplayLinkContextAdapter.REACTOR, mapper));
        }

        private static void reactorSingleLineSource(String name,
                                                    BiFunction<ReactorDisplaySourceEntity, DisplayTargetStats, MutableComponent> mapper) {
            reactorSource(name, new MultiblockSingleLineDisplaySource<>(IDisplayLinkContextAdapter.REACTOR, mapper));
        }

        private static void reactorMultiLineSource(String name,
                                                   BiFunction<ReactorDisplaySourceEntity, DisplayTargetStats, List<List<MutableComponent>>> mapper) {
            reactorSource(name, new MultiblockMultiLineDisplaySource<>(IDisplayLinkContextAdapter.REACTOR, mapper));
        }

        private static <T extends DisplaySource> void reactorSource(String name, T displaySource) {
            registerDisplaySource("reactor." + name, displaySource, Blocks.REACTOR_DISPLAYSOURCE_BASIC,
                    Blocks.REACTOR_DISPLAYSOURCE_REINFORCED);
        }

        private static void turbinePercentageSource(String name, Function<TurbineDisplaySourceEntity, Float> mapper) {
            turbineSource(name, new MultiblockPercentOrProgressBarDisplaySource<>(IDisplayLinkContextAdapter.TURBINE, mapper));
        }

        private static void turbineSingleLineSource(String name,
                                                    BiFunction<TurbineDisplaySourceEntity, DisplayTargetStats, MutableComponent> mapper) {
            turbineSource(name, new MultiblockSingleLineDisplaySource<>(IDisplayLinkContextAdapter.TURBINE, mapper));
        }

        private static void turbineMultiLineSource(String name,
                                                   BiFunction<TurbineDisplaySourceEntity, DisplayTargetStats, List<List<MutableComponent>>> mapper) {
            turbineSource(name, new MultiblockMultiLineDisplaySource<>(IDisplayLinkContextAdapter.TURBINE, mapper));
        }

        private static <T extends DisplaySource> void turbineSource(String name, T displaySource) {
            registerDisplaySource("turbine." + name, displaySource, Blocks.TURBINE_DISPLAYSOURCE_BASIC,
                    Blocks.TURBINE_DISPLAYSOURCE_REINFORCED);
        }

        private static void energizerPercentageSource(String name, Function<EnergizerDisplaySourceEntity, Float> mapper) {
            energizerSource(name, new MultiblockPercentOrProgressBarDisplaySource<>(IDisplayLinkContextAdapter.ENERGIZER, mapper));
        }

        private static void energizerSingleLineSource(String name,
                                                      BiFunction<EnergizerDisplaySourceEntity, DisplayTargetStats, MutableComponent> mapper) {
            energizerSource(name, new MultiblockSingleLineDisplaySource<>(IDisplayLinkContextAdapter.ENERGIZER, mapper));
        }

        private static void energizerMultiLineSource(String name,
                                                     BiFunction<EnergizerDisplaySourceEntity, DisplayTargetStats, List<List<MutableComponent>>> mapper) {
            energizerSource(name, new MultiblockMultiLineDisplaySource<>(IDisplayLinkContextAdapter.ENERGIZER, mapper));
        }

        private static <T extends DisplaySource> void energizerSource(String name, T displaySource) {
            registerDisplaySource("energizer." + name, displaySource, Blocks.ENERGIZER_DISPLAYSOURCE);
        }

        @SafeVarargs
        private static <T extends DisplaySource, B extends Block> void registerDisplaySource(String name, T displaySource,
                                                                                             Supplier<B>... blocks) {

            AllDisplayBehaviours.register(ExtremeReactorsCreateCompat.ROOT_LOCATION.buildWithPrefix(name), displaySource);

            for (final var block : blocks) {
                AllDisplayBehaviours.assignBlock(displaySource, block.get());
            }
        }

        //endregion
    }

    public static final class CreativeTabs {

        private static final DeferredRegister<CreativeModeTab> TABS = defer(Registries.CREATIVE_MODE_TAB);

        static void initialize(final IEventBus bus) {

            TABS.register(bus);

            TABS.register("tab.general", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.er2create.general"))
                    .icon(() -> new ItemStack(Items.REACTOR_DISPLAYSOURCE_BASIC.get()))
                    .withTabsBefore(ExtremeReactors.ROOT_LOCATION.buildWithSuffix("tab.turbine"))
                    .displayItems((parameters, output) -> {

                        output.accept(Items.REACTOR_DISPLAYSOURCE_BASIC.get());
                        output.accept(Items.REACTOR_DISPLAYSOURCE_REINFORCED.get());
                        output.accept(Items.TURBINE_DISPLAYSOURCE_BASIC.get());
                        output.accept(Items.TURBINE_DISPLAYSOURCE_REINFORCED.get());
                        output.accept(Items.ENERGIZER_DISPLAYSOURCE.get());
                    })
                    .build()
            );
        }
    }

    //region internals

    private static <T> DeferredRegister<T> defer(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, ExtremeReactorsCreateCompat.MOD_ID);
    }

    private static <T> DeferredRegister<T> defer(ResourceKey<? extends Registry<T>> key) {
        return DeferredRegister.create(key, ExtremeReactorsCreateCompat.MOD_ID);
    }

    private CreateContent() {
    }

    //endregion
}
