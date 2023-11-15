package net.darkhax.attributefix;

import net.darkhax.attributefix.config.AttributeConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

public class AttributeFixFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientLifecycleEvents.CLIENT_STARTED.register(mc -> AttributeConfig.load(FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + ".json").toFile()).applyChanges());
    }
}
