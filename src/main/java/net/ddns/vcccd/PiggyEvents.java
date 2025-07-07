package net.ddns.vcccd;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PiggyEvents implements Listener {
	private ArrayList<Player> PiggyPlayers = new ArrayList<>();
	private final Main main;

	public PiggyEvents(Main main) { this.main = main; }

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
	public void onPiggyAttacked(EntityDamageByEntityEvent event) {
		try {
			boolean isPlayer = event.getDamager() instanceof Player;
			boolean listContainsPlayer = PiggyPlayers.contains(event.getDamager());
			if(isPlayer && !listContainsPlayer) {
				PiggyPlayers.add((Player) event.getDamager());
			}
		} catch (Exception e) {}
	}
	
	@EventHandler
	public void onPiggyDeath(EntityDeathEvent event) {
		try {
			PigZombie piggy = (PigZombie) event.getEntity();
			boolean isPiggy = piggy.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lPiGgY"));
			
			if(isPiggy) {
				DropItemAt(event.getEntity(), CustomItem(Material.GOLDEN_AXE, ChatColor.translateAlternateColorCodes('&', "&c&lPiggy&4&lAxe")));
				spawnExperienceOrbs(event.getEntity().getLocation(), 100, 2);

				if(main.getConfig().getBoolean("AnnounceBossKill")){
				for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Piggy has been slain!");
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);

				}
			}
			}
				
		} catch (Exception e) {
			assert true;
		}
	}

}
