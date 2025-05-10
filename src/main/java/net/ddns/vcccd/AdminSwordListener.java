package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AdminSwordListener implements Listener{
	
	@EventHandler
	public void useAdminSword(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player.getInventory().getItemInMainHand().getItemMeta() == null) {
				assert true;
			} else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Admin Sword")) {
				if(event.getEntity() instanceof Mob) {
					Mob mob = (Mob) event.getEntity();
					mob.setHealth(0);
				}
			}
		}
	}

}
