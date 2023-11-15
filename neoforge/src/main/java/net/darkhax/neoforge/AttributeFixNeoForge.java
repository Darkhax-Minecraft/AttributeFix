package net.darkhax.neoforge;

import net.darkhax.attributefix.Constants;
import net.darkhax.attributefix.config.AttributeConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLPaths;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeFixNeoForge {

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {

        AttributeConfig.load(FMLPaths.CONFIGDIR.get().resolve(Constants.MOD_ID + ".json").toFile()).applyChanges();
    }
}