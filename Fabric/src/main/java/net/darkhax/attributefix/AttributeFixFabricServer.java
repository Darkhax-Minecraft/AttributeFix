package net.darkhax.attributefix;

import net.darkhax.attributefix.config.AttributeConfig;
import net.darkhax.attributefix.temp.RegistryHelper;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AttributeFixFabricServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {

            if (server.isDedicatedServer()) {

                RegistryHelper<Attribute> registry = new AttributeRegistryHelper();
                AttributeConfig.load(FabricLoader.getInstance().getConfigDir().resolve(Constants.MOD_ID + ".json").toFile(), registry).applyChanges(registry);
            }
        });
    }
}