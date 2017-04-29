package creepgaming.creepershift.toxicity.toxichandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import creepgaming.creepershift.toxicity.Reference;
import creepgaming.creepershift.toxicity.Toxicity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;

public class ToxicWorldData extends WorldSavedData {
	
	private HashMap<Chunk, Integer> toxicChunks;
	private static final String DATA_CHUNKS = Reference.MODID + "_toxicChunkData";

	public ToxicWorldData(String name) {
		super(name);
		toxicChunks = new HashMap<Chunk, Integer>();
	}

	public ToxicWorldData() {
		super(DATA_CHUNKS);
		toxicChunks = new HashMap<Chunk, Integer>();
	}

	/*
	 * Reads the data TAGDATA 11 is ARRAY_INT We pull the saved ints from our
	 * array, reconstruct the chunk and the pollution level.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList tagList = nbt.getTagList(DATA_CHUNKS, 11);

		for (int i = 0; i < tagList.tagCount(); i++) {

			int[] tag = tagList.getIntArrayAt(i);

			toxicChunks.put(DimensionManager.getWorld(tag[0]).getChunkFromChunkCoords(tag[1], tag[2]), tag[3]);
		}

	}

	/*
	 * Saves the data We store the chunks world, position and pollution level in
	 * an array of INTEGERS.
	 */

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {

		NBTTagList tagList = new NBTTagList();

		compound.setTag(DATA_CHUNKS, tagList);

		for (Entry<Chunk, Integer> entry : toxicChunks.entrySet()) {

			tagList.appendTag(new NBTTagIntArray(new int[] { entry.getKey().getWorld().provider.getDimension(),
					entry.getKey().xPosition, entry.getKey().zPosition, entry.getValue() }));
		}

		return compound;

	}

	/*
	 * Static implementation that initiates the WorldData instance.
	 */

	public static ToxicWorldData get(World world) {
		MapStorage storage = world.getMapStorage();
		ToxicWorldData instance = (ToxicWorldData) storage.getOrLoadData(ToxicWorldData.class, DATA_CHUNKS);

		if (instance == null) {
			Toxicity.logger.error("instance was null");
			instance = new ToxicWorldData();
			storage.setData(DATA_CHUNKS, instance);
		}
		return instance;

	}

	/*
	 * Increase Chunk toxicity level by 1.
	 */
	public void increaseToxicity(Chunk chunk, int i) {
		/*
		 * Debug
		 */
			if (toxicChunks.containsKey(chunk))
				toxicChunks.put(chunk, (toxicChunks.get(chunk) + i));
			else
				toxicChunks.put(chunk, i);

		markDirty();
		checkThreshold(chunk);

	}

	
	private void checkThreshold(Chunk chunk){
		
		World worldIn = chunk.getWorld();
		List<EntityPlayer> players = worldIn.playerEntities;	
		
	}
	
	/*
	 * Return toxicity level of a chunk If the chunk isn't registered yet, it
	 * has a level of 0.
	 */
	public int getToxicLevel(Chunk chunk) {

			if (toxicChunks.containsKey(chunk))
			return toxicChunks.get(chunk);
			
		return 0;
	}

	/*
	 * Returns the HashMap, creates the instance if it doesn't exist.
	 */

	public static HashMap<Chunk, Integer> getToxicChunks(World world) {

		return get(world).toxicChunks;
	}

}
