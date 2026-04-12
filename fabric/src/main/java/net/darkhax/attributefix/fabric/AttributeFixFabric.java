package net.darkhax.attributefix.fabric;

import net.darkhax.attributefix.common.impl.AttributeFixMod;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class AttributeFixFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            if (server.isDedicatedServer()) {
                AttributeFixMod.getInstance().init();
            }
        });
    }
}