package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TimmothyEvents implements Listener {
	
	//Helper Methods
	//====================
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
	//====================
	
	@EventHandler
	public void onTimmothyDeath(EntityDeathEvent event) {
		try {
			Skeleton timmothy = (Skeleton) event.getEntity();
			boolean isTimmothy = timmothy.getCustomName().equals(ChatColor.AQUA + "Timmothy");
			
			if(isTimmothy) {
				DropItemAt(event.getEntity(), CustomItem(Material.BOW, ChatColor.RED + "BomBow"));
			}
			
		} catch (Exception e) {
			assert true;
		}
	}

}
