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

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "attributefix", name = "Attribute Fix", version = "@VERSION@", certificateFingerprint = "@FINGERPRINT@")
public class AttributeFix {

    public static final IAttribute[] ATTRIBUTES = new IAttribute[] { MAX_HEALTH, FOLLOW_RANGE, KNOCKBACK_RESISTANCE, MOVEMENT_SPEED, FLYING_SPEED, ATTACK_DAMAGE, ATTACK_SPEED, ARMOR, ARMOR_TOUGHNESS, LUCK };

    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

        final ConfigurationHandler config = new ConfigurationHandler(event.getSuggestedConfigurationFile());

        for (final IAttribute attribute : ATTRIBUTES) {

            if (attribute instanceof RangedAttribute) {

                config.configureRangedAttribute((RangedAttribute) attribute);
            }
        }

        config.syncConfigData();
    }
}