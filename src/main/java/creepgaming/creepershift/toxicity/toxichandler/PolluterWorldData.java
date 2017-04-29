package creepgaming.creepershift.toxicity.toxichandler;

import java.util.HashMap;
import java.util.Map.Entry;

import creepgaming.creepershift.toxicity.Reference;
import creepgaming.creepershift.toxicity.Toxicity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;

public class PolluterWorldData extends WorldSavedData{
	
	
	/**
	 * Saves our Polluters between world loads.
	 * 
	 * TODO: Rewrite from <BlockPos, TilEntity> to <BlockPos, Chunk>. We don't really need the tile entity information,
	 *  and chunk lets us calculate a chunk at a time, which means we only update the pollution value once.
	 * 
	 */
	
	
	private HashMap<BlockPos, TileEntity> polluters;
	private static final String DATA_POLLUTER = Reference.MODID + "_polluter";

	public PolluterWorldData(String name) {
		super(name);
		polluters = new HashMap<BlockPos, TileEntity>();
	}

	public PolluterWorldData() {
		super(DATA_POLLUTER);
		polluters = new HashMap<BlockPos, TileEntity>();
	}

	/*
	 * Reads the data TAGDATA 11 is ARRAY_INT We pull the saved ints from our
	 * array, reconstruct the chunk and the pollution level.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList tagList = nbt.getTagList(DATA_POLLUTER, 11);

		for (int i = 0; i < tagList.tagCount(); i++) {
			int[] tag = tagList.getIntArrayAt(i);
			BlockPos pos = new BlockPos(tag[0], tag[1], tag[2]);
			polluters.put(pos, DimensionManager.getWorld(0).getTileEntity(pos));
		}

	}

	/*
	 * Saves the data We store the chunks world, position and pollution level in
	 * an array of INTEGERS.
	 */

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {

		NBTTagList tagList = new NBTTagList();

		compound.setTag(DATA_POLLUTER, tagList);

		for (Entry<BlockPos, TileEntity> entry : polluters.entrySet()) {
			tagList.appendTag(new NBTTagIntArray(new int[]{entry.getKey().getX(), entry.getKey().getY(), entry.getKey().getZ()}));
		}

		return compound;

	}

	/*
	 * Static implementation that initiates the WorldData instance.
	 */

	public static PolluterWorldData get(World world) {
		MapStorage storage = world.getMapStorage();
		PolluterWorldData instance = (PolluterWorldData) storage.getOrLoadData(PolluterWorldData.class, DATA_POLLUTER);

		if (instance == null) {
			Toxicity.logger.error("instance was null");
			instance = new PolluterWorldData();
			storage.setData(DATA_POLLUTER, instance);
		}
		return instance;

	}

	
	public void addPolluter(BlockPos pos, TileEntity tile){
		polluters.put(pos, tile);
		markDirty();
	}
	

	/*
	 * Returns the HashMap, creates the instance if it doesn't exist.
	 */

	
	public static HashMap<BlockPos, TileEntity> getPolluters(World world){
		return get(world).polluters;
	}
}
