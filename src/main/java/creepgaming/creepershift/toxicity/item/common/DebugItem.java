package creepgaming.creepershift.toxicity.item.common;

import creepgaming.creepershift.toxicity.toxichandler.PolluterWorldData;
import creepgaming.creepershift.toxicity.toxichandler.ToxicWorldData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class DebugItem extends Item{

	
	public DebugItem(){
		super();
		this.setUnlocalizedName("debugItem");
		this.setRegistryName("debugItem");
		
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {

		/*
		 * Get the chunks pollution level
		 * 
		 */
		if (!worldIn.isRemote && !playerIn.isSneaking())
		playerIn.addChatComponentMessage(new TextComponentString("Toxicity: " + ToxicWorldData.get(worldIn).getToxicLevel(worldIn.getChunkFromBlockCoords(playerIn.getPosition()))));
	
		if(!worldIn.isRemote && playerIn.isSneaking())
			for(BlockPos pos : PolluterWorldData.getPolluters(worldIn).keySet()){
				playerIn.addChatComponentMessage(new TextComponentString("Pos: " + pos));
			}
			
		
		
		
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);

	}

	
	
	
	
}
