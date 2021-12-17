package net.darkhax.attributefix;

import net.darkhax.attributefix.config.AttributeConfig;
import net.darkhax.attributefix.temp.RegistryHelper;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeFixForge {

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {

        RegistryHelper<Attribute> registry = new AttributeRegistryHelper();
        AttributeConfig.load(FMLPaths.CONFIGDIR.get().resolve(Constants.MOD_ID + ".json").toFile(), registry).applyChanges(registry);

        for (Attribute attribute : registry.getValues()) {

            if (attribute instanceof RangedAttribute ranged) {

                Constants.LOG.info("{}    {}-{}    {}", attribute.getRegistryName(), ranged.getMinValue(), ranged.getMaxValue(), ranged.sanitizeValue(15_000d));
            }
        }
    }
}