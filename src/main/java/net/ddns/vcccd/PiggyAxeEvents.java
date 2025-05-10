package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PiggyAxeEvents implements Listener {
	
	private void rocketEffect(Entity Damager, Entity Damagee) {
		Firework toTheMoon = (Firework) Damagee.getWorld().spawnEntity(Damagee.getLocation(), EntityType.FIREWORK_ROCKET);
		toTheMoon.addPassenger(Damagee);
		Damagee.getWorld().createExplosion(Damagee.getLocation(), 1);
	}
	
	private void ParticleEffect(Entity parameter) {
		Location location = parameter.getLocation();
		for (int i = 0; i < 360; i=i+18) {
            double Xoffset = 3 * Math.cos(Math.toRadians(i));
            double Yoffset = 3 * Math.sin(Math.toRadians(i));
            parameter.getWorld().spawnParticle(Particle.FIREWORK, location.getX() + Xoffset, location.getY(), location.getZ() + Yoffset, 1);
            parameter.getWorld().spawnParticle(Particle.FLAME, location.getX() + Xoffset, location.getY(), location.getZ() + Yoffset, 1);
        }
	}
	
	@EventHandler
	public void onPiggyAxeAttack(EntityDamageByEntityEvent event) {
		try {
			if(event.getDamager() instanceof Player) {
				Player damager = (Player) event.getDamager();
				ItemStack itemInUse = damager.getInventory().getItemInMainHand();
				ItemMeta itemMeta = itemInUse.getItemMeta();
				if(itemMeta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lPiggy&4&lAxe"))) {
					ParticleEffect(damager);
					rocketEffect(damager, event.getEntity());
				}
			} 
	} catch (Exception e) {
		assert true;
	}
}
}
