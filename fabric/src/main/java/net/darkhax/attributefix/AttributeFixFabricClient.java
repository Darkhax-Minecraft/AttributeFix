package net.darkhax.attributefix;

import net.darkhax.attributefix.config.AttributeConfig;
import net.darkhax.attributefix.temp.RegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AttributeFixFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {


        ClientLifecycleEvents.CLIENT_STARTED.register(mc -> {

            RegistryHelper<Attribute> registry = new AttributeRegistryHelper();
            AttributeConfig.load(FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + ".json").toFile(), registry).applyChanges(registry);
        });
    }
}
