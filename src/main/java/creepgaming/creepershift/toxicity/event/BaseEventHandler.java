package creepgaming.creepershift.toxicity.event;

import java.util.HashMap;
import java.util.Map.Entry;

import creepgaming.creepershift.toxicity.toxichandler.PolluterWorldData;
import creepgaming.creepershift.toxicity.toxichandler.ToxicWorldData;
import net.minecraft.block.BlockFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class BaseEventHandler {

	/*
	 * Just to remind me TODO:This is shit
	 */

	@SubscribeEvent
	public void worldEvent(WorldTickEvent event) {
		if (event.side == Side.SERVER) {

			if (event.world.getTotalWorldTime() % 20 == 0) {

				HashMap<BlockPos, TileEntity> map = PolluterWorldData.getPolluters(event.world);

				if (!map.isEmpty()) {

					for (Entry<BlockPos, TileEntity> entry : map.entrySet()) {

						if (entry.getValue() instanceof TileEntityFurnace) {
							if (((TileEntityFurnace) entry.getValue()).isBurning()) {
								ToxicWorldData.get(event.world)
										.increaseToxicity(event.world.getChunkFromBlockCoords(entry.getKey()), 10);

							}
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void placeBlockEvent(PlaceEvent event) {

		if (event.getPlacedBlock().getBlock() instanceof BlockFurnace) {
			PolluterWorldData.get(event.getWorld()).addPolluter(event.getPos(),
					event.getWorld().getTileEntity(event.getPos()));
		}

	}

	@SubscribeEvent
	public void onInteract(EntityInteract event) {

		event.getEntityPlayer().addChatComponentMessage(new TextComponentString(
				"chunk +" + event.getWorld().getChunkFromBlockCoords(event.getEntityPlayer().getPosition())));

	}

}
