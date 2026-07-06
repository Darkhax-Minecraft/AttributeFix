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
    private static boolean hasInitialized = false;

    /**
     * This is an internal method. Other mods or user scripts should never invoke this method. AttributeFix will load
     * and apply itself.
     */
    public static void applyInternalChanges() {
        if (!hasInitialized) {
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
        else {
            LOG.warn("{} has already been initialized this session.", MOD_NAME, new Throwable());
        }
    }
}