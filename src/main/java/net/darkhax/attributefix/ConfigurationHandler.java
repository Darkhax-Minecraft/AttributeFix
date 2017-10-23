package net.darkhax.attributefix;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

    public static Configuration config;

    public ConfigurationHandler (File file) {

        config = new Configuration(file);
    }

    public void configureRangedAttribute (RangedAttribute attribute) {

        final String name = StringUtils.capitalize(attribute.getName().replace("generic.", ""));

        attribute.maximumValue = config.getFloat("max" + name, "attributes", this.getAdjustedAmount((float) attribute.maximumValue), Float.MIN_VALUE, Float.MAX_VALUE, "The maximum amount for the " + name + " attribute.");
        attribute.minimumValue = config.getFloat("min" + name, "attributes", (float) attribute.minimumValue, Float.MIN_VALUE, Float.MAX_VALUE, "The minimum amount for the " + name + " attribute.");

        if (attribute.minimumValue > attribute.maximumValue) {

            throw new IllegalArgumentException("The " + name + " attribute was configured incorrectly! Minimum must not be greater than maximum!");
        }
    }

    private float getAdjustedAmount (float amount) {

        return Math.max(amount, 65536f);
    }

    public void syncConfigData () {

        if (config.hasChanged()) {

            config.save();
        }
    }
}