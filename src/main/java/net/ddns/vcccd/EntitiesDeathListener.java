package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntitiesDeathListener implements Listener{
	
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
	
	@EventHandler
	public void onBossDeath(EntityDeathEvent event) {
		if(event.getEntity().getCustomName() == null) {
			assert true;
		}
	else if(event.getEntity().getCustomName().equals(ChatColor.AQUA + "Timmothy")) {
			DropItemAt(event.getEntity(), CustomItem(Material.BOW, ChatColor.RED + "BomBow"));
			event.getEntity().getWorld().spawnParticle(Particle.LAVA, event.getEntity().getLocation(), 30);
			
		} else if(event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
			DropItemAt(event.getEntity(), CustomItem(Material.NETHERITE_HELMET, ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo\'s Helmet")));
			event.getEntity().getWorld().spawnParticle(Particle.LAVA, event.getEntity().getLocation(), 30);
			
		} else if(event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"))) {
			DropItemAt(event.getEntity(), CustomItem(Material.TRIDENT, ChatColor.translateAlternateColorCodes('&', "&e&lBig Boy\'s Trident")));
			event.getEntity().getWorld().spawnParticle(Particle.LAVA, event.getEntity().getLocation(), 30);
		}
	}

}
