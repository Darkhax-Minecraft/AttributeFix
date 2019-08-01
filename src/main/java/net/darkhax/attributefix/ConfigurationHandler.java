package net.darkhax.attributefix;

import static net.minecraft.entity.SharedMonsterAttributes.ARMOR;
import static net.minecraft.entity.SharedMonsterAttributes.ARMOR_TOUGHNESS;
import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_DAMAGE;
import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_SPEED;
import static net.minecraft.entity.SharedMonsterAttributes.FLYING_SPEED;
import static net.minecraft.entity.SharedMonsterAttributes.FOLLOW_RANGE;
import static net.minecraft.entity.SharedMonsterAttributes.KNOCKBACK_RESISTANCE;
import static net.minecraft.entity.SharedMonsterAttributes.LUCK;
import static net.minecraft.entity.SharedMonsterAttributes.MAX_HEALTH;
import static net.minecraft.entity.SharedMonsterAttributes.MOVEMENT_SPEED;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigurationHandler {

    private static final IAttribute[] ATTRIBUTES = new IAttribute[] { MAX_HEALTH, FOLLOW_RANGE, KNOCKBACK_RESISTANCE, MOVEMENT_SPEED, FLYING_SPEED, ATTACK_DAMAGE, ATTACK_SPEED, ARMOR, ARMOR_TOUGHNESS, LUCK };

	private final Map<RangedAttribute, AttributeData> attributes = new HashMap<>();
	private final ForgeConfigSpec spec;
	
	public ConfigurationHandler () {
		
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		
		for (IAttribute attribute : ATTRIBUTES) {
			
			if (attribute instanceof RangedAttribute) {
				
				RangedAttribute rangedAttribute = (RangedAttribute) attribute;
				attributes.put(rangedAttribute, new AttributeData(rangedAttribute, builder));
			}
		}
		
		this.spec = builder.build();
	}
	
	public ForgeConfigSpec getSpec() {
		
		return spec;
	}
	
	public void applyChanges() {
		
		for (IAttribute attribute : ATTRIBUTES) {
			
			if (attribute instanceof RangedAttribute) {
				
				RangedAttribute rangedAttribute = (RangedAttribute) attribute;
				AttributeData data = attributes.get(rangedAttribute);
				
				rangedAttribute.minimumValue = data.min.get();
				rangedAttribute.maximumValue = data.max.get();
			}
		}
	}
	
    private class AttributeData {
    	
    	private final ForgeConfigSpec.DoubleValue min;
    	private final ForgeConfigSpec.DoubleValue max;
    	
    	public AttributeData(RangedAttribute attribute, ForgeConfigSpec.Builder builder) {
    		
    		String group = attribute.getName().replace("generic.", "").replace(".", "").toLowerCase();
    		
    		builder.comment("Values for the " + group + " attribute.");
    		builder.push(group);
    		min = builder.defineInRange("min", attribute.minimumValue, 0d, Double.MAX_VALUE);
    		max = builder.defineInRange("max", Math.max(attribute.minimumValue, 65536d), 0d, Double.MAX_VALUE);
    		
    		builder.pop();
    	}
    }
}