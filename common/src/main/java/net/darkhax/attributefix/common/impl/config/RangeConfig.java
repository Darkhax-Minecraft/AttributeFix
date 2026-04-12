package net.darkhax.attributefix.common.impl.config;

import com.google.common.collect.ImmutableMap;
import net.darkhax.attributefix.common.impl.AttributeFixMod;
import net.darkhax.attributefix.common.mixin.AccessorRangedAttribute;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import java.util.Map;

public class RangeConfig {

    private static final Map<Identifier, Double> NEW_DEFAULT_VALUES = ImmutableMap.of(
            Identifier.fromNamespaceAndPath("minecraft", "max_health"), 1_000_000D,
            Identifier.fromNamespaceAndPath("minecraft", "armor"), 1_000_000D,
            Identifier.fromNamespaceAndPath("minecraft", "armor_toughness"), 1_000_000D,
            Identifier.fromNamespaceAndPath("minecraft", "attack_damage"), 1_000_000D,
            Identifier.fromNamespaceAndPath("minecraft", "attack_knockback"), 1_000_000D
    );

    private final transient Identifier id;
    private final transient RangedAttribute attribute;

    @Value(comment = "Determines if the range for the attribute should be modified or not.")
    public boolean modify_range;

    @Value(comment = "The lowest possible value for the attribute.")
    public double min;

    @Value(comment = "The highest possible value for the attribute.")
    public double max;

    public RangeConfig(Identifier id, RangedAttribute attribute) {
        this.id = id;
        this.attribute = attribute;
        this.modify_range = NEW_DEFAULT_VALUES.containsKey(id);
        this.min = attribute.getMinValue();
        this.max = NEW_DEFAULT_VALUES.getOrDefault(id, attribute.getMaxValue());
    }

    public void apply() {
        if (this.modify_range && this.attribute instanceof AccessorRangedAttribute accessor) {
            if (this.min >= this.max) {
                AttributeFixMod.LOG.error("User Error: A minimum can not exceed or equal a maximum value! Attribute={} Min={} Max={}", this.id, this.min, this.max);
                throw new IllegalStateException("User Error: A minimum can not exceed or equal a maximum value! Attribute=" + this.id + " Min=" + this.min + " Max=" + this.max);
            }
            if (this.min != this.attribute.getMinValue()) {
                AttributeFixMod.LOG.info("Changing minimum value for {} from {} to {}.", this.id, this.attribute.getMinValue(), this.min);
                accessor.attributefix$setMinValue(this.min);
            }
            if (this.max != this.attribute.getMaxValue()) {
                AttributeFixMod.LOG.info("Changing maximum value for {} from {} to {}.", this.id, this.attribute.getMaxValue(), this.max);
                accessor.attributefix$setMaxValue(this.max);
            }
        }
    }
}