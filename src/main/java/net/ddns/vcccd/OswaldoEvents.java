package net.ddns.vcccd;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class OswaldoEvents implements Listener{
	
	private ArrayList<Player> OswaldoPlayers = new ArrayList<>();
	private final Main main;

	public OswaldoEvents(Main main) {
		this.main = main;
	}
	
	// Helper Methods:
	//=============================
	private int RNG(int scope) {
        return (new Random().nextInt(scope));
    }
	
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
	//=============================

	@EventHandler
	public void onOswaldoAttacked(EntityDamageByEntityEvent event) {
		try {
			Zombie oswaldo = (Zombie) event.getEntity();
			boolean isPlayer = event.getDamager() instanceof Player;
			boolean listContainsPlayer = OswaldoPlayers.contains(event.getDamager());
			if(isPlayer && !listContainsPlayer) {
				OswaldoPlayers.add((Player) event.getDamager());
			}
		} catch (Exception e) {}
	}
		
	
	@EventHandler
	public void onOswaldoDeath(EntityDeathEvent event) {
		try {
			Zombie oswaldo = (Zombie) event.getEntity();
			boolean isOswaldo = oswaldo.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"));
			
			if(isOswaldo) {
				DropItemAt(oswaldo, CustomItem(Material.NETHERITE_HELMET, ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo\'s Helmet")));
				String playersWhoKilledOswaldo = OswaldoPlayers.stream()
					.map(Player::getName)
					.reduce("", (a, b) -> a + ", " + b);

				if(main.getConfig().getBoolean("AnnounceBossKill")) {
					for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Oswaldo has been slain by" + playersWhoKilledOswaldo);
					}
				}
				OswaldoPlayers.clear();
			}
			
		} catch (Exception e) {
			assert true;
		}
	}
	
	@EventHandler
	public void onOswaldoHit(EntityDamageByEntityEvent event) {
		try {
			Zombie oswaldo = (Zombie) event.getEntity();
			boolean isOswaldo = oswaldo.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"));
			
			if(isOswaldo) {
				if (RNG(4) == 3) {
	                double X, Y, Z;
	                X = oswaldo.getLocation().getX();
	                Y = oswaldo.getLocation().getY();
	                Z = oswaldo.getLocation().getZ();

	                for (int i = 0; i < 360; i = i + 6) {
	                    double Xoffset = 4 * Math.cos(Math.toRadians(i));
	                    double Yoffset = 4 * Math.sin(Math.toRadians(i));
	                    oswaldo.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, X + Xoffset, Y, Z + Yoffset, 1);
	                    oswaldo.getWorld().spawnParticle(Particle.LAVA, X + Xoffset, Y, Z + Yoffset, 1);
	                }

	                for (Entity near: oswaldo.getNearbyEntities(4, 4, 4)) {
	                    if (near instanceof Player) {
	                        Player player = (Player) near;
	                        Location axis = player.getLocation().getDirection().toLocation(player.getWorld());
	                        Vector VelocityVector = new Vector(-axis.getX(), 1.5, -axis.getZ());
	                        player.setVelocity(VelocityVector.normalize());
	                    }
	                }

	            }
	            if (RNG(6) == 5) {
	            	oswaldo.getWorld().spawnEntity(oswaldo.getLocation(), EntityType.FIREBALL);
	            }

	            if (RNG(4) == 3) {
	                Zombie Minion = (Zombie) oswaldo.getWorld().spawnEntity(oswaldo.getLocation(), EntityType.ZOMBIE);
	                Minion.setBaby();
	                Minion.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lMinion"));
	                Minion.setCustomNameVisible(true);
	                EntityEquipment equipment = Minion.getEquipment();
	                equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
	                equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
	            }
			}
			
		} catch (Exception e) {
			assert true;
		}
	}

}
