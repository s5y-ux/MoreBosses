package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import java.util.Random;

public class PlayerItemsEvent implements Listener {
	
	private int RNG(int scope) {
        return (new Random().nextInt(scope));
    }
	
	@EventHandler
	public void HitWithHelmet(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player user = (Player) event.getEntity();
			if(user.getInventory().getHelmet() == null) {
				assert true;
			} else if(user.getInventory().getHelmet().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo\'s Helmet"))) {
				if(RNG(3) == 2) {
				Entity ReferenceEntity = event.getEntity();
				
				double X, Y, Z;
                X = ReferenceEntity.getLocation().getX();
                Y = ReferenceEntity.getLocation().getY();
                Z = ReferenceEntity.getLocation().getZ();
				
				for (int i = 0; i < 360; i = i + 6) {
                    double Xoffset = 4 * Math.cos(Math.toRadians(i));
                    double Yoffset = 4 * Math.sin(Math.toRadians(i));
                    ReferenceEntity.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, X + Xoffset, Y, Z + Yoffset, 1);
                    ReferenceEntity.getWorld().spawnParticle(Particle.LAVA, X + Xoffset, Y, Z + Yoffset, 1);
                }
				
				for (Entity near: ReferenceEntity.getNearbyEntities(4, 4, 4)) {
                        Location axis = near.getLocation().getDirection().toLocation(near.getWorld());
                        Vector VelocityVector = new Vector(-axis.getX(), 1.5, -axis.getZ());
                        near.setVelocity(VelocityVector.normalize());
                    
                }
			}
			}
		}
	}

}
