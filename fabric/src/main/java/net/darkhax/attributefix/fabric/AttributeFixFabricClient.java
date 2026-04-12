package net.darkhax.attributefix.fabric;

import net.darkhax.attributefix.common.impl.AttributeFixMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class AttributeFixFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(mc -> AttributeFixMod.getInstance().init());
    }
}