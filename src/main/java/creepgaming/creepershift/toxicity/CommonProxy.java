package creepgaming.creepershift.toxicity;

import creepgaming.creepershift.toxicity.event.BaseEventHandler;
import creepgaming.creepershift.toxicity.item.ItemHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public  void init(){
		MinecraftForge.EVENT_BUS.register(new BaseEventHandler());
		new ItemHandler();
	}
	
	public  void preinit(){
		
	}
	
	public  void postinit(){
		
	}
	
}
