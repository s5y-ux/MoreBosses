package net.ddns.vcccd;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DrStrangeEvents implements Listener {

	@EventHandler
	public void onDrStrangeAttack(EntityDamageByEntityEvent event) {
		try {
			Enderman drStrange = (Enderman) event.getDamager();
			boolean isDrStrange = drStrange.getCustomName().equals(ChatColor.DARK_PURPLE + "Dr. Strange");
			
			if(isDrStrange) {
				Random rand = new Random();
				Player player =(Player) event.getEntity();
				Location playerLocal = player.getLocation();
				Location randomLocal = new Location(event.getDamager().getWorld(), playerLocal.getX() + (double) rand.nextInt(50), 
						playerLocal.getY(), playerLocal.getZ() + (double) rand.nextInt(50));
				Location highestBlock = player.getWorld().getHighestBlockAt(randomLocal).getLocation();
				highestBlock.setY(highestBlock.getY() + 1);
				Location enderLocation = highestBlock;
				enderLocation.setX(enderLocation.getX() + 5);
				Location newHighestBlock = playerLocal.getWorld().getHighestBlockAt(enderLocation).getLocation();
				newHighestBlock.setY(newHighestBlock.getY() + 1);
				player.teleport(highestBlock);
				event.getDamager().teleport(newHighestBlock);
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
			}
			
		} catch (Exception e) {
			assert true;
		}
	}

	@EventHandler
	public void onDrStrangeDeath(EntityDeathEvent event) {
		if(event.getEntity() instanceof Enderman && event.getEntity().getCustomName().equals(ChatColor.DARK_PURPLE + "Dr. Strange")) {
			ItemStack timeStone = new ItemStack(Material.EMERALD);
			ItemMeta timeMeta = timeStone.getItemMeta();
			timeMeta.setDisplayName(ChatColor.GREEN + "Time Stone");
			timeMeta.setLore(java.util.List.of(ChatColor.GRAY + "A powerful stone that controls time.", ChatColor.translateAlternateColorCodes('&', "&eRight-click &7to slow down time for nearby entities."), ChatColor.translateAlternateColorCodes('&', "&eRight-click &7on blocks to speed up growth."), ChatColor.translateAlternateColorCodes('&', "&eShift + Right-click &7on entities to toggle their age.")));
			timeStone.setItemMeta(timeMeta);
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), timeStone);
		}
	}
}
