package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AlbertEvents implements Listener {
	
	@EventHandler
	public void onPlayerAttackAlbert(EntityDamageByEntityEvent event) {
		try {
			Slime slime = (Slime) event.getEntity();
			Player player = (Player) event.getDamager();
		if(slime.getCustomName().equals(ChatColor.YELLOW + "Albert")) {
			String playerItemName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
			if(!playerItemName.equals(ChatColor.translateAlternateColorCodes('&', "&eAlbert Remover"))) {
			Location slimeLocation = slime.getLocation();
			World world = slime.getWorld();
			new AlbertEntity(32 ,slimeLocation, world);
		} else {
			slime.remove();
		}
		}
		} catch (Exception e) {
			assert true;
		}
	}

}
