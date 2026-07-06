package net.darkhax.attributefix.impl;

import net.darkhax.attributefix.common.impl.AttributeFixMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod(AttributeFixMod.MOD_ID)
@EventBusSubscriber(modid = AttributeFixMod.MOD_ID)
public class NeoForgeMod {

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        AttributeFixMod.LOG.info("Initializing {} on NeoForge.", AttributeFixMod.MOD_NAME);
        AttributeFixMod.applyInternalChanges();
    }
}