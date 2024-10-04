package net.darkhax.attributefix.fabric.impl;

import net.darkhax.attributefix.common.impl.AttributeFixMod;
import net.fabricmc.api.ClientModInitializer;

public class FabricModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AttributeFixMod.getInstance().init();
    }
}