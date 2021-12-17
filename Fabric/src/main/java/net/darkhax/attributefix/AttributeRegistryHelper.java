package net.darkhax.attributefix;

import net.darkhax.attributefix.temp.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.Collection;

public class AttributeRegistryHelper implements RegistryHelper<Attribute> {

    @Override
    public Attribute get(ResourceLocation id) {

        return Registry.ATTRIBUTE.get(id);
    }

    @Override
    public ResourceLocation getId(Attribute value) {

        return Registry.ATTRIBUTE.getKey(value);
    }

    @Override
    public boolean isRegistered(Attribute value) {

        return getId(value) != null;
    }

    @Override
    public boolean exists(ResourceLocation id) {

        return Registry.ATTRIBUTE.containsKey(id);
    }

    @Override
    public Collection<Attribute> getValues() {

        return Registry.ATTRIBUTE.stream().toList();
    }
}