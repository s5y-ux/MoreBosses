package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

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

				String playersWhoKilledPiggy = PiggyPlayers.stream()
						.map(Player::getName)
						.reduce("", (a, b) -> a + ", " + b);

				for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Piggy has been slain by" + playersWhoKilledPiggy);
				}
				PiggyPlayers.clear();
			}
				
		} catch (Exception e) {
			assert true;
		}
	}

}
