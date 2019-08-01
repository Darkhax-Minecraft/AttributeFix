package net.darkhax.attributefix;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("attributefix")
public class AttributeFix {
	
    private final ConfigurationHandler config;
    
    public AttributeFix () {
    	
    	this.config = new ConfigurationHandler();
    	ModLoadingContext.get().registerConfig(Type.COMMON, config.getSpec());
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    
    private void setup (FMLCommonSetupEvent event) {

    	this.config.applyChanges();
    }
}