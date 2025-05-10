package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;


public class DrStrangeEntity {
	
	@SuppressWarnings("deprecation")
	public DrStrangeEntity(int health, Location local, World world, Main main) {
		Enderman DrStrange = (Enderman) world.spawnEntity(local, EntityType.ENDERMAN);
		DrStrange.setCustomName(ChatColor.DARK_PURPLE + "Dr. Strange");
		DrStrange.setCustomNameVisible(true);
		DrStrange.setMaxHealth(health);
		DrStrange.setHealth(health);
		DrStrange.setRemoveWhenFarAway(main.getConfig().getBoolean("DrStrangeDeSpawn"));
	}
	
}
