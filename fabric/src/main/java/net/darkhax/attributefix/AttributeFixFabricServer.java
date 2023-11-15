package net.darkhax.attributefix;

import net.darkhax.attributefix.config.AttributeConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

public class AttributeFixFabricServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            if (server.isDedicatedServer()) {
                AttributeConfig.load(FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + ".json").toFile()).applyChanges();
            }
        });
    }
}