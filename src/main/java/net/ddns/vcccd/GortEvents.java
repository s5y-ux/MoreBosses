package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vindicator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GortEvents implements Listener {

	private final Main main;

	public GortEvents(Main main) { this.main = main; }

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

	private void spawnExperienceOrbs(Location location, int totalOrbs, int expPerOrb) {
		World world = location.getWorld();
		for (int i = 0; i < totalOrbs; i++) {
			ExperienceOrb orb = (ExperienceOrb) world.spawn(location, ExperienceOrb.class);
			orb.setExperience(expPerOrb);
		}
	}
	//======================

	@EventHandler
	public void onGortDeath(EntityDeathEvent event) {
		try {
			Vindicator gort = (Vindicator) event.getEntity();
			boolean isGort = gort.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eGort The Serf"));
			
			if(isGort) {
				DropItemAt(event.getEntity(), CustomItem(Material.IRON_HOE, ChatColor.translateAlternateColorCodes('&', "&eGort's Hoe")));
				spawnExperienceOrbs(event.getEntity().getLocation(), 100, 2);

				if(main.getConfig().getBoolean("AnnounceBossKill")){
				for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Gort has been slain!");
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);

				}
			}
			}
				
		} catch (Exception e) {
			assert true;
		}
	}

}
