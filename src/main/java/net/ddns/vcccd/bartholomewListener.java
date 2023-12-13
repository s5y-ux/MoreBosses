package net.ddns.vcccd;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;

public class bartholomewListener implements Listener {
	
	private int RNG(int scope) {
        return (new Random().nextInt(scope));
    }
	
	@EventHandler
	public void Attacked(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
		if(event.getEntity().getCustomName() == null) {
			assert true;
		} else {
			if(event.getEntity().getCustomName().equals(ChatColor.BLACK + "Bartholomew")) {
				if(RNG(4)==3) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 50, 3));
				}
				if(RNG(4)==3) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 3));
				}
				if(RNG(4)==3) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 10));
				}
				if(RNG(15)==14) {
					player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					player.sendMessage("You have been disarmed...");
				}
			}
		}
	}
	}

}
