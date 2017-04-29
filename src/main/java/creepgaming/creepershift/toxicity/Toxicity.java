package creepgaming.creepershift.toxicity;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class Toxicity {

	@SidedProxy(clientSide = "creepgaming.creepershift.toxicity.ClientProxy")
	public static CommonProxy proxy;
	public static Logger logger;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.init();

	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preinit();

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		proxy.postinit();
	}

}