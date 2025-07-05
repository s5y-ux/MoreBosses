package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TimmothyEvents implements Listener {
	private ArrayList<Player> TimmothyPlayers = new ArrayList<>();
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
	//====================

	@EventHandler
	public void onTimmothyAttacked(EntityDamageByEntityEvent event) {
		try {
			boolean isPlayer = event.getDamager() instanceof Player;
			boolean listContainsPlayer = TimmothyPlayers.contains(event.getDamager());
			if(isPlayer && !listContainsPlayer) {
				TimmothyPlayers.add((Player) event.getDamager());
			}
		} catch (Exception e) {}
	}
	
	@EventHandler
	public void onTimmothyDeath(EntityDeathEvent event) {
		try {
			Skeleton timmothy = (Skeleton) event.getEntity();
			boolean isTimmothy = timmothy.getCustomName().equals(ChatColor.AQUA + "Timmothy");
			
			if(isTimmothy) {
				DropItemAt(event.getEntity(), CustomItem(Material.BOW, ChatColor.RED + "BomBow"));

				String playersWhoKilledTimmothy = TimmothyPlayers.stream()
						.map(Player::getName)
						.reduce("", (a, b) -> a + ", " + b);

				for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Timmothy has been slain by" + playersWhoKilledTimmothy);
				}
				TimmothyPlayers.clear();
			}
			
		} catch (Exception e) {
			assert true;
		}
	}

}
