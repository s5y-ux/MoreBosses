package net.ddns.vcccd;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class BossSpawn implements Listener {
	
	@SuppressWarnings("unused")
	private final Main main;
	private FileConfiguration config;
	
	public BossSpawn(Main main) {
		this.main = main;
		this.config = main.getConfig();
	}
	
	private int RNG(int scope) {
        return (new Random().nextInt(scope));
    }
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		if(event.isNewChunk()) {
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
			        	new Oswaldo(this.config.getInt("OswaldoHealth"), spawnLocation, chunkWorld);
			        	}
			        	break;
			        case 2:
			        	if(this.config.getBoolean("BigBoySpawn")) {
			        		new BigBoy(this.config.getInt("BigBoyHealth"), spawnLocation, chunkWorld);
			        	}
			        	break;
			        case 3:
			        	if(this.config.getBoolean("TimmothySpawn")) {
			        		new Timmothy(this.config.getInt("TimmothySpawn"), spawnLocation, chunkWorld);
			        	}
			        	break;
			        case 4:
			        	if(this.config.getBoolean("BartholomewSpawn")) {
			        		new bartholomew(this.config.getInt("BartholomewHealth"), spawnLocation, chunkWorld);
			        	}
			        	break;
			        case 5:
			        	if(this.config.getBoolean("PiggySpawn")) {
			        		new Piggy(this.config.getInt("PiggyHealth"), spawnLocation, chunkWorld);
			        	}
			        	break;
			        case 6:
			        	if(this.config.getBoolean("GortSpawn")) {
			        		new VinNumber(this.config.getInt("GortHealth"), spawnLocation, chunkWorld);
			        	}
			        	break;
			        case 7:
			        	if(this.config.getBoolean("DrStrangeSpawn")) {
			        		new DrStrange(this.config.getInt("DrStrangeHealth"), spawnLocation, chunkWorld);
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
