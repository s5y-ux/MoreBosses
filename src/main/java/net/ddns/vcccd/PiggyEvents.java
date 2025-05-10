package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PiggyEvents implements Listener {
	
	//Helper Methods:
	//======================
	private ItemStack CustomItem(Material item, String name) {
		ItemStack ReturnItem = new ItemStack(item);
		ItemMeta ReturnItemData = ReturnItem.getItemMeta();
		ReturnItemData.setDisplayName(name);
		ReturnItem.setItemMeta(ReturnItemData);
		return(ReturnItem);
	}
	
	private void DropItemAt(LivingEntity entity, ItemStack item) {
		Location location = entity.getLocation();
		World world = entity.getWorld();
		world.dropItem(location, item);
	}
	//======================
	
	@EventHandler
	public void onPiggyDeath(EntityDeathEvent event) {
		try {
			PigZombie piggy = (PigZombie) event.getEntity();
			boolean isPiggy = piggy.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lPiGgY"));
			
			if(isPiggy) {
				DropItemAt(event.getEntity(), CustomItem(Material.GOLDEN_AXE, ChatColor.translateAlternateColorCodes('&', "&c&lPiggy&4&lAxe")));
			}
				
		} catch (Exception e) {
			assert true;
		}
	}

}
