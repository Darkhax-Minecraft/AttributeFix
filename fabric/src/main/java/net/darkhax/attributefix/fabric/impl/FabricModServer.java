package net.darkhax.attributefix.fabric.impl;

import net.darkhax.attributefix.common.impl.AttributeFixMod;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class FabricModServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            if (server.isDedicatedServer()) {
                AttributeFixMod.getInstance().init();
            }
        });
    }
}