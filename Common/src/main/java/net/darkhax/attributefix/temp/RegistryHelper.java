package net.darkhax.attributefix.temp;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public interface RegistryHelper<T> {

    T get(ResourceLocation id);

    ResourceLocation getId(T value);

    boolean isRegistered(T value);

    boolean exists(ResourceLocation id);

    Collection<T> getValues();
}