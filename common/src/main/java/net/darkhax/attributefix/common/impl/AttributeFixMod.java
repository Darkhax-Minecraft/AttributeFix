package net.darkhax.attributefix.common.impl;

import net.darkhax.attributefix.common.impl.config.RangeConfig;
import net.darkhax.pricklemc.common.api.config.ConfigManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class AttributeFixMod {

    private static AttributeFixMod instance;
    private boolean hasInitialized = false;

    public void init() {
        if (hasInitialized) {
            throw new IllegalStateException("The " + Constants.MOD_NAME + " has already been initialized.");
        }
        for (Attribute attribute : BuiltInRegistries.ATTRIBUTE) {
            final ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey(attribute);
            if (id != null && attribute instanceof RangedAttribute ranged) {
                RangeConfig config = new RangeConfig(id, ranged);
                config = ConfigManager.load(Constants.MOD_ID + "/" + id.getNamespace() + "/" + id.getPath(), config);
                config.apply();
            }
        }
        hasInitialized = true;
    }

    public static AttributeFixMod getInstance() {
        if (instance == null) {
            instance = new AttributeFixMod();
        }
        return instance;
    }
}