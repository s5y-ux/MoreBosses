package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;

public class AlbertEntity {
	
	public AlbertEntity(int health, Location playerLocation, World world) {
		Slime albertEntity = (Slime) world.spawnEntity(playerLocation, EntityType.SLIME);
		albertEntity.setCustomName(ChatColor.YELLOW + "Albert");
		albertEntity.setCustomNameVisible(true);
		albertEntity.setAI(true);
	}

}
