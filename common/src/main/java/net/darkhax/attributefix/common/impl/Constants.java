package net.darkhax.attributefix.common.impl;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "attributefix";
    public static final String MOD_NAME = "AttributeFix";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static Identifier id(String path) {
        return Identifier.tryBuild(MOD_ID, path);
    }
}