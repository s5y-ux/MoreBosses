package net.ddns.vcccd;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class DrStrange {
	public DrStrange(Player player, int health) {
		Enderman DrStrange = (Enderman) player.getWorld().spawnEntity(player.getLocation(), EntityType.ENDERMAN);
		DrStrange.setCustomName(ChatColor.DARK_PURPLE + "Dr. Strange");
		DrStrange.setCustomNameVisible(true);
		DrStrange.setMaxHealth(health);
		DrStrange.setHealth(health);
	}
	public DrStrange(int health, Location local, World world) {
		Enderman DrStrange = (Enderman) world.spawnEntity(local, EntityType.ENDERMAN);
		DrStrange.setCustomName(ChatColor.DARK_PURPLE + "Dr. Strange");
		DrStrange.setCustomNameVisible(true);
		DrStrange.setMaxHealth(health);
		DrStrange.setHealth(health);
	}
}
