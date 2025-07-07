package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class AlbertEvents implements Listener {

	private final Main main;

	public AlbertEvents(Main main) { this.main = main; }

	private void spawnExperienceOrbs(Location location, int totalOrbs, int expPerOrb) {
		World world = location.getWorld();
		for (int i = 0; i < totalOrbs; i++) {
			ExperienceOrb orb = (ExperienceOrb) world.spawn(location, ExperienceOrb.class);
			orb.setExperience(expPerOrb);
		}
	}

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

	@EventHandler
	public void onAlbertDeath(EntityDeathEvent event) {
		try {
			Slime piggy = (Slime) event.getEntity();
			boolean isAlbert = piggy.getCustomName().equals(ChatColor.YELLOW + "Albert");

			if(isAlbert) {
				if(main.getConfig().getBoolean("AnnounceBossKill")){
				for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Albert has been slain!");
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);

				}
			}
			}

		} catch (Exception e) {
			assert true;
		}
	}

}
