package net.ddns.vcccd;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vindicator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class ListenToAxe implements Listener{
	
	private double invert(double parameter) {
		return(parameter - (parameter * 2));
	}
	
	private void DumbAeffect(Entity Damager, Entity Damagee) {
		Firework toTheMoon = (Firework) Damagee.getWorld().spawnEntity(Damagee.getLocation(), EntityType.FIREWORK);
		toTheMoon.addPassenger(Damagee);
		Damagee.getWorld().createExplosion(Damagee.getLocation(), 1);
	}
	
	private void ParticleEffect(Entity parameter) {
		Location location = parameter.getLocation();
		for (int i = 0; i < 360; i=i+18) {
            double Xoffset = 3 * Math.cos(Math.toRadians(i));
            double Yoffset = 3 * Math.sin(Math.toRadians(i));
            parameter.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location.getX() + Xoffset, location.getY(), location.getZ() + Yoffset, 1);
            parameter.getWorld().spawnParticle(Particle.FLAME, location.getX() + Xoffset, location.getY(), location.getZ() + Yoffset, 1);
        }
	}
	
	@EventHandler
	public void Axe(EntityDamageByEntityEvent event) {
		try {
		if(event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			ItemStack itemInUse = damager.getInventory().getItemInMainHand();
			ItemMeta itemMeta = itemInUse.getItemMeta();
			if(itemMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lPiggy&4&lAxe"))) {
				ParticleEffect(damager);
				DumbAeffect(damager, event.getEntity());
			} else if (itemMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&eGorf's Hoe"))) {
				for(int i = 0; i < 10; i++) {
					event.getEntity().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, event.getEntity().getLocation(), 9);
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
		
	} else if (event.getDamager() instanceof PigZombie) {
		PigZombie damager = (PigZombie) event.getDamager();
		if(damager.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lPiGgY"))) {
			ParticleEffect(damager);
			DumbAeffect(damager, event.getEntity());
		}
	} else if (event.getDamager() instanceof Vindicator) {
		Vindicator ourMan = (Vindicator) event.getDamager();
		if(ourMan.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eGort The Serf"))) {
			for(int i = 0; i < 10; i++) {
				event.getEntity().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, event.getEntity().getLocation(), 9);
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
	} else if(event.getDamager() instanceof Enderman) {
		if(event.getDamager().getCustomName().equals(ChatColor.DARK_PURPLE + "Dr. Strange")) {
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
	}
		
	

} catch (Exception e) {
	assert(true);
}
}
}

