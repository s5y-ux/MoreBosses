package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Vindicator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GortEvents implements Listener {
	
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
	public void onGortDeath(EntityDeathEvent event) {
		try {
			Vindicator gort = (Vindicator) event.getEntity();
			boolean isGort = gort.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eGort The Serf"));
			
			if(isGort) {
				DropItemAt(event.getEntity(), CustomItem(Material.IRON_HOE, ChatColor.translateAlternateColorCodes('&', "&eGort's Hoe")));
			}
				
		} catch (Exception e) {
			assert true;
		}
	}

}
