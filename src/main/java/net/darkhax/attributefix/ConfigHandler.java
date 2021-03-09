package net.darkhax.attributefix;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

public class ConfigHandler {
    
    public static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private final Map<RangedAttribute, AttributeData> attributes = new HashMap<>();
    private final ForgeConfigSpec spec;
    
    public ConfigHandler() {
        
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        for (final Attribute attribute : ForgeRegistries.ATTRIBUTES) {
            
            if (attribute instanceof RangedAttribute) {
                
                final RangedAttribute ranged = (RangedAttribute) attribute;
                final AttributeData data = new AttributeData(ranged, builder);
                this.attributes.put(ranged, data);
            }
            
            else {
                
                AttributeFix.LOG.debug("Skipping attribute {}, not a ranged attribute.", attribute.getRegistryName());
            }
        }
        
        this.spec = builder.build();
    }
    
    public ForgeConfigSpec getSpec () {
        
        return this.spec;
    }
    
    public void applyChanges () {
        
        for (final Entry<RangedAttribute, AttributeData> entry : this.attributes.entrySet()) {
            
            final RangedAttribute ranged = entry.getKey();
            final AttributeData data = entry.getValue();
            
            if (data.enabled.get()) {
                
                AttributeFix.LOG.debug("Changing range for {} from {}-{} to {}-{}", ranged.getRegistryName(), FORMAT.format(ranged.minimumValue), FORMAT.format(ranged.maximumValue), FORMAT.format(data.min.get()), FORMAT.format(data.max.get()));
                ranged.minimumValue = data.min.get();
                ranged.maximumValue = data.max.get();
            }
            
            else {
                
                AttributeFix.LOG.debug("Skipping attribute {}. Disabled by config.", ranged.getRegistryName());
            }
        }
    }
    
    private class AttributeData {
        
        private final ForgeConfigSpec.BooleanValue enabled;
        private final ForgeConfigSpec.DoubleValue min;
        private final ForgeConfigSpec.DoubleValue max;
        
        public AttributeData(RangedAttribute attribute, ForgeConfigSpec.Builder builder) {
            
            final ResourceLocation id = attribute.getRegistryName();
            final String group = id.getNamespace().replace(".", "_") + "_" + id.getPath().toString().replace(".", "_");
            
            builder.comment("Values for the " + attribute.getRegistryName().toString() + " attribute.");
            builder.push(group);
            
            builder.comment("Whether or not this attribute should be modified.");
            this.enabled = builder.define("enabled", true);
            
            builder.comment("The minimum vallue for the attribute. Changing this may have unforseen consequences.");
            this.min = builder.defineInRange("min", attribute.minimumValue, -Double.MAX_VALUE, Double.MAX_VALUE);
            
            builder.comment("The maximum value for the attribute.");
            this.max = builder.defineInRange("max", Math.max(attribute.maximumValue, 65536d), -Double.MAX_VALUE, Double.MAX_VALUE);
            
            builder.pop();
        }
    }
}