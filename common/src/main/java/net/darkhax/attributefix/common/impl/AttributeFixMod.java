package net.darkhax.attributefix.common.impl;

import net.darkhax.attributefix.common.impl.config.RangeConfig;
import net.darkhax.pricklemc.common.api.config.ConfigManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeFixMod {

    public static final String MOD_ID = "attributefix";
    public static final String MOD_NAME = "AttributeFix";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    private static AttributeFixMod instance;
    private boolean hasInitialized = false;

    public void init() {
        if (hasInitialized) {
            throw new IllegalStateException("The " + MOD_NAME + " has already been initialized.");
        }
        final long startTime = System.nanoTime();
        for (Attribute attribute : BuiltInRegistries.ATTRIBUTE) {
            final Identifier id = BuiltInRegistries.ATTRIBUTE.getKey(attribute);
            if (id != null && attribute instanceof RangedAttribute ranged) {
                RangeConfig config = new RangeConfig(id, ranged);
                config = ConfigManager.load(MOD_ID + "/" + id.getNamespace() + "/" + id.getPath(), config);
                config.apply();
            }
        }
        final long endTime = System.nanoTime();
        LOG.info("Initialized AttributeFix. Took {}ms.", String.format("%,.2f", (endTime - startTime) / 1000000d));
        hasInitialized = true;
    }

    public static AttributeFixMod getInstance() {
        if (instance == null) {
            instance = new AttributeFixMod();
        }
        return instance;
    }
}