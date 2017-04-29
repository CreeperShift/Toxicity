package creepgaming.creepershift.toxicity.item;

import creepgaming.creepershift.toxicity.item.common.DebugItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemHandler {

	
	
	
	public ItemHandler(){
		init();
		
	}
	
	private void init(){
		
		
		Item item = new DebugItem();
		GameRegistry.register(item);
		
	}
	
	
}
