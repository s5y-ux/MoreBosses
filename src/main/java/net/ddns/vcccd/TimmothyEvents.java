package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TimmothyEvents implements Listener {

	private final Main main;

	public TimmothyEvents(Main main) { this.main = main; }

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

	private void spawnExperienceOrbs(Location location, int totalOrbs, int expPerOrb) {
    World world = location.getWorld();
    for (int i = 0; i < totalOrbs; i++) {
        ExperienceOrb orb = (ExperienceOrb) world.spawn(location, ExperienceOrb.class);
        orb.setExperience(expPerOrb);
    }
}
	//====================
	
	@EventHandler
	public void onTimmothyDeath(EntityDeathEvent event) {
		try {
			Skeleton timmothy = (Skeleton) event.getEntity();
			boolean isTimmothy = timmothy.getCustomName().equals(ChatColor.AQUA + "Timmothy");
			
			if(isTimmothy) {
				DropItemAt(event.getEntity(), CustomItem(Material.BOW, ChatColor.RED + "BomBow"));
				spawnExperienceOrbs(event.getEntity().getLocation(), 100, 2);

				if(main.getConfig().getBoolean("AnnounceBossKill")){
				for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Timmothy has been slain!");
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);

				}
			}
				
			}
			
		} catch (Exception e) {
			assert true;
		}
	}

}
