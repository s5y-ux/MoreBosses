package net.ddns.vcccd;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class SpawnInWorld implements Listener {

	private final Main main;
	private FileConfiguration config;
	
	public SpawnInWorld(Main main) {
		this.main = main;
		this.config = main.getConfig();
	}
	
	private int RNG(int scope) {
        return (new Random().nextInt(7 + scope));
    }
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
			if(this.main.getConfig().getBoolean("SpawnInWorld")){
			Chunk chunk = event.getChunk();
			World chunkWorld = chunk.getWorld();
			String worldReference[] = config.getString("Worlds").split(",");
			for(String worldName : worldReference) {
				if(worldName.equals(chunkWorld.getName())) {
					int x = chunk.getX() * 16 + (int) (Math.random() * 16);
			        int z = chunk.getZ() * 16 + (int) (Math.random() * 16);
			        int y = chunk.getWorld().getHighestBlockYAt(x, z) + 4;

			        // Create a location object
			        Location spawnLocation = new Location(chunkWorld, x, y, z);
					
			        switch(RNG(this.config.getInt("SpawnRNG"))) {
			        case 1:
			        	if(this.config.getBoolean("OswaldoSpawn")) {
			        	new OswaldoEntity(this.config.getInt("OswaldoHealth"), spawnLocation, chunkWorld, main);
			        	}
			        	break;
			        case 2:
			        	if(this.config.getBoolean("BigBoySpawn")) {
			        		new BigBoyEntity(this.config.getInt("BigBoyHealth"), spawnLocation, chunkWorld, main);
			        	}
			        	break;
			        case 3:
			        	if(this.config.getBoolean("TimmothySpawn")) {
			        		new TimmothyEntity(this.config.getInt("TimmothyHealth"), spawnLocation, chunkWorld, main);
			        	}
			        	break;
			        case 4:
			        	if(this.config.getBoolean("BartholomewSpawn")) {
			        		new BartholomewEntity(this.config.getInt("BartholomewHealth"), spawnLocation, chunkWorld, main);
			        	}
			        	break;
			        case 5:
			        	if(this.config.getBoolean("PiggySpawn")) {
			        		new PiggyEntity(this.config.getInt("PiggyHealth"), spawnLocation, chunkWorld, main);
			        	}
			        	break;
			        case 6:
			        	if(this.config.getBoolean("GortSpawn")) {
			        		new GortEntity(this.config.getInt("GortHealth"), spawnLocation, chunkWorld, main);
			        	}
			        	break;
			        case 7:
			        	if(this.config.getBoolean("DrStrangeSpawn")) {
			        		new DrStrangeEntity(this.config.getInt("DrStrangeHealth"), spawnLocation, chunkWorld, main);
			        	}
			        	break;
			        default:
			        	assert true;
			        }
				}
			}
		}
		
	}
	
}
