package net.ddns.vcccd;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
}
