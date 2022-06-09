package net.darkhax.attributefix;

import net.darkhax.attributefix.temp.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;

public class AttributeRegistryHelper implements RegistryHelper<Attribute> {

    @Override
    public Attribute get(ResourceLocation id) {

        return ForgeRegistries.ATTRIBUTES.getValue(id);
    }

    @Override
    public ResourceLocation getId(Attribute value) {

        return ForgeRegistries.ATTRIBUTES.getKey(value);
    }

    @Override
    public boolean isRegistered(Attribute value) {

        return ForgeRegistries.ATTRIBUTES.containsValue(value);
    }

    @Override
    public boolean exists(ResourceLocation id) {

        return ForgeRegistries.ATTRIBUTES.containsKey(id);
    }

    @Override
    public Collection<Attribute> getValues() {

        return ForgeRegistries.ATTRIBUTES.getValues();
    }
}