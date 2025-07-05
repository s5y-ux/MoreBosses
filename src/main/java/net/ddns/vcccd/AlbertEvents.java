package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;

public class AlbertEvents implements Listener {
	private ArrayList<Player> AlbertPlayers = new ArrayList<>();
	private final Main main;

	public AlbertEvents(Main main) { this.main = main; }

	@EventHandler
	public void onPlayerAttackAlbert(EntityDamageByEntityEvent event) {
		try {
			Slime slime = (Slime) event.getEntity();
			Player player = (Player) event.getDamager();
			boolean listContainsPlayer = AlbertPlayers.contains(player);
			if(!listContainsPlayer) {
				AlbertPlayers.add((Player) event.getDamager());
			}
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

	@EventHandler
	public void onAlbertDeath(EntityDeathEvent event) {
		try {
			Slime piggy = (Slime) event.getEntity();
			boolean isAlbert = piggy.getCustomName().equals(ChatColor.YELLOW + "Albert");

			if(isAlbert) {
				String playersWhoKilledPiggy = AlbertPlayers.stream()
						.map(Player::getName)
						.reduce("", (a, b) -> a + ", " + b);

				for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Albert has been slain by" + playersWhoKilledPiggy);
				}
				AlbertPlayers.clear();
			}

		} catch (Exception e) {
			assert true;
		}
	}

}
