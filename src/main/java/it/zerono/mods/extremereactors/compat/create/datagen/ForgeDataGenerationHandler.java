package it.zerono.mods.extremereactors.compat.create.datagen;

import it.zerono.mods.extremereactors.compat.create.ExtremeReactorsCreateCompat;
import it.zerono.mods.zerocore.lib.datagen.ForgeModDataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeDataGenerationHandler {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        new ForgeModDataGenerator(event, ExtremeReactorsCreateCompat.ROOT_LOCATION, new DataGenerationHandler());
    }
}
