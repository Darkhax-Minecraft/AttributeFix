package net.darkhax.attributefix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("attributefix")
public class AttributeFix {
    
    public static final Logger LOG = LogManager.getLogger("Attribute Fix");
    private final ConfigHandler config = new ConfigHandler();
    
    public AttributeFix() {
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, this.config.getSpec());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
    }
    
    private void onLoadComplete (FMLLoadCompleteEvent event) {
        
        this.config.applyChanges();
    }
}