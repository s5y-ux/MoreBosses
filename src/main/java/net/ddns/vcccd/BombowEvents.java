package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public class BombowEvents implements Listener {
	
	@EventHandler
	public void bomBowArrowTracker(EntityShootBowEvent event) {
		try {
			ItemStack bowUsed = event.getBow();
			boolean isBombow = bowUsed.getItemMeta().getDisplayName().equals(ChatColor.RED + "BomBow");
			
			if(isBombow) {
				event.getProjectile().setCustomName("Exploding Arrow");
			}
			
		} catch (Exception e) {
			assert true;
		}
	}
	
	@EventHandler
	public void onBombowShootEvent(ProjectileHitEvent event) {
		try {
			Projectile explosiveArrow = event.getEntity();
			World world = explosiveArrow.getWorld();
			Location whereLanded = explosiveArrow.getLocation();
			boolean isExplosiveArrow = explosiveArrow.getCustomName().equals("Exploding Arrow");
			
			if(isExplosiveArrow) {
				world.createExplosion(whereLanded, 5);
				explosiveArrow.remove();
			}
			
		} catch(Exception e) {
			assert true;
		}
	}
}
