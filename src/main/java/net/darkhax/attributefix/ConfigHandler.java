package net.darkhax.attributefix;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

public class ConfigHandler {

	private final Map<RangedAttribute, AttributeData> attributes = new HashMap<>();
	private final ForgeConfigSpec spec;
    
	public ConfigHandler () {
		
		final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		
		for (Attribute attribute : ForgeRegistries.ATTRIBUTES) {
			
			if (attribute instanceof RangedAttribute) {
				
				final RangedAttribute ranged = (RangedAttribute) attribute;
				final AttributeData data = new AttributeData(ranged, builder);				
				attributes.put(ranged, data);
			}
			
			else {
				
				AttributeFix.LOG.debug("Skipping attribute {}, not a ranged attribute.", attribute.getRegistryName());
			}
		}
		
		this.spec = builder.build();
	}
	
	public void save() {
		
		ModConfig modConfig = new ModConfig(Type.COMMON, spec, ModLoadingContext.get().getActiveContainer());
        final CommentedFileConfig configData = modConfig.getHandler().reader(FMLPaths.CONFIGDIR.relative()).apply(modConfig);
        Method setConfigDataMethod = ObfuscationReflectionHelper.findMethod(ModConfig.class, "setConfigData", CommentedConfig.class);
        
        try {
			setConfigDataMethod.invoke(modConfig, configData);
		} 
        
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        	AttributeFix.LOG.error("Forge's config code could not be accessed.", e);
        	throw new IllegalStateException(e);
		}
        
        modConfig.save();
	}
	
	public ForgeConfigSpec getSpec() {
		
		return spec;
	}
	
	public void applyChanges() {
		
		for (Entry<RangedAttribute, AttributeData> entry : this.attributes.entrySet()) {
			
			final RangedAttribute ranged = entry.getKey();
			final AttributeData data = entry.getValue();
			
			if (data.enabled.get()) {
				
				ranged.minimumValue = data.min.get();
				ranged.maximumValue = data.max.get();
				AttributeFix.LOG.debug("Changing range for {} from {}-{} to {}-{}", ranged.getRegistryName(), ranged.minimumValue, ranged.maximumValue, data.min, data.max);
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
    		String group = id.getNamespace().replace(".", "_") + "_" + id.getPath().toString().replace(".", "_");
    		
    		builder.comment("Values for the " + attribute.getRegistryName().toString() + " attribute.");
    		builder.push(group);
    		
    		builder.comment("Whether or not this attribute should be modified.");
    		enabled = builder.define("enabled", true);
    		
    		builder.comment("The minimum vallue for the attribute. Changing this may have unforseen consequences.");
    		min = builder.defineInRange("min", attribute.minimumValue, Double.MIN_VALUE, Double.MAX_VALUE);
    		
    		builder.comment("The maximum value for the attribute.");
    		max = builder.defineInRange("max", Math.max(attribute.maximumValue, 65536d), Double.MIN_VALUE, Double.MAX_VALUE);    		
    		
    		builder.pop();
    	}
    }
}