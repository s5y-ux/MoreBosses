package net.ddns.vcccd;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LeveSwordEvent implements Listener {
	
	// Helper Methods:
		//================================
		private int RNG(int scope) {
	        return (new Random().nextInt(scope));
	    }
		//================================
	
	@EventHandler
	public void onLeveSworduse(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player.getInventory().getItemInMainHand().getItemMeta() == null) {
				assert true;
			} else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Leve-Sword")) {
				if(RNG(4) == 3) {
					try {
						LivingEntity ReferenceEntity = (LivingEntity) event.getEntity();
						ReferenceEntity.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 50, 3));
						double X, Y, Z;
						X = ReferenceEntity.getLocation().getX();
						Y = ReferenceEntity.getLocation().getY();
						Z = ReferenceEntity.getLocation().getZ();

						for (int i = -2; i < 2; i++) {
							ReferenceEntity.getWorld().spawnParticle(Particle.CLOUD, X+i, Y, Z+i, 5);
						}
					} catch (Exception e) {
						assert true;
					}
					
				}
			}
		}
	}

}
