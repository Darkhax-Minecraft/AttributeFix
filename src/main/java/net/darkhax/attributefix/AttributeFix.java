package net.darkhax.attributefix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("attributefix")
@EventBusSubscriber(modid = "attributefix", bus = EventBusSubscriber.Bus.MOD)
public class AttributeFix {
    
    public static final Logger LOG = LogManager.getLogger("Attribute Fix");
    
    @SubscribeEvent
    public static void setup (FMLCommonSetupEvent event) {
        
        final ConfigHandler config = new ConfigHandler();
        config.save();
        config.applyChanges();
    }
}