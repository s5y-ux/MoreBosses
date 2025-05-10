package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vindicator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GortsHoeEvents implements Listener {
	
	// TODO: Use ChatGPT to fix this, this is awful...
	
	@EventHandler
	public void onHoeUse(EntityDamageByEntityEvent event) {
		try {
			Player damager = (Player) event.getDamager();
			ItemStack itemInUse = damager.getInventory().getItemInMainHand();
			ItemMeta itemMeta = itemInUse.getItemMeta();
		if (itemMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&eGort's Hoe"))) {
			for(int i = 0; i < 10; i++) {
				event.getEntity().getWorld().spawnParticle(Particle.FIREWORK, event.getEntity().getLocation(), 9);
			}
			Location shortener = event.getEntity().getLocation();
			World entityWorld = event.getEntity().getWorld();
			int coordinates[] = {(int) shortener.getX(), (int) shortener.getY(), (int) shortener.getZ()};
			for(int i = 0; i < 2; i++) {
				for(int x=-1; x < 2; x=x+2) {
					Location rel[] = {new Location(entityWorld, coordinates[0] + x, coordinates[1] + i, coordinates[2] + x), 
							new Location(entityWorld, coordinates[0] - x, coordinates[1] + i, coordinates[2]),
							new Location(entityWorld, coordinates[0], coordinates[1] + i, coordinates[2] - x)};
					for(Location o: rel) {
						o.getBlock().setType(Material.COBWEB);
					}
				}
			}
			event.getEntity().setFireTicks(50);
		}
	
} catch (Exception e) {
	assert true;
}
		try {
			Vindicator ourMan = (Vindicator) event.getDamager();
			if(ourMan.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eGort The Serf"))) {
				for(int i = 0; i < 10; i++) {
					event.getEntity().getWorld().spawnParticle(Particle.FIREWORK, event.getEntity().getLocation(), 9);
				}
				Location shortener = event.getEntity().getLocation();
				World entityWorld = event.getEntity().getWorld();
				int coordinates[] = {(int) shortener.getX(), (int) shortener.getY(), (int) shortener.getZ()};
				for(int i = 0; i < 2; i++) {
					for(int x=-1; x < 2; x=x+2) {
						Location rel[] = {new Location(entityWorld, coordinates[0] + x, coordinates[1] + i, coordinates[2] + x), 
								new Location(entityWorld, coordinates[0] - x, coordinates[1] + i, coordinates[2]),
								new Location(entityWorld, coordinates[0], coordinates[1] + i, coordinates[2] - x)};
						for(Location o: rel) {
							o.getBlock().setType(Material.COBWEB);
						}
					}
				}
				event.getEntity().setFireTicks(50);
			}
	
} catch (Exception e) {
	assert true;
}
	}
	}
