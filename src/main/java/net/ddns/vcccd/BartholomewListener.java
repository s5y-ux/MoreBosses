package net.ddns.vcccd;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BartholomewListener implements Listener {
	private ArrayList<Player> BartholomewPlayers = new ArrayList<>();
	private final Main main;

	public BartholomewListener(Main main) { this.main = main; }

	// Helper Methods:
	//================================
	private int RNG(int scope) {
        return (new Random().nextInt(scope));
    }
	//================================
	
	@EventHandler
	public void onBartholomewDeathEvent(EntityDeathEvent event) {
		if(event.getEntity().getCustomName() == null) {
			assert true;
		} else if (event.getEntity().getCustomName().equals(ChatColor.BLACK + "Bartholomew")) {
			ItemStack LeveSword = new ItemStack(Material.STONE_SWORD);
			ItemMeta SwordMeta = LeveSword.getItemMeta();
			SwordMeta.setDisplayName(ChatColor.GRAY + "Leve-Sword");
			LeveSword.setItemMeta(SwordMeta);
			
			Mob ReferenceEntity = (Mob) event.getEntity();
			double X, Y, Z;
            X = ReferenceEntity.getLocation().getX();
            Y = ReferenceEntity.getLocation().getY();
            Z = ReferenceEntity.getLocation().getZ();

            for (int i = -2; i < 2; i++) {
            	ReferenceEntity.getWorld().spawnParticle(Particle.LAVA, X+i, Y, Z+i, 5);
            }
            
            ReferenceEntity.getWorld().dropItem(ReferenceEntity.getLocation(), LeveSword);

			String playersWhoKilledBartholomew = BartholomewPlayers.stream()
					.map(Player::getName)
					.reduce("", (a, b) -> a + ", " + b);

			for(Player player: main.getServer().getOnlinePlayers()) {
				player.sendMessage(main.getPluginPrefix() + "Bartholomew has been slain by" + playersWhoKilledBartholomew);
			}
			BartholomewPlayers.clear();
		}
	}
	
	@EventHandler
	public void onBartholomewAttackEvent(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();

			boolean listContainsPlayer = BartholomewPlayers.contains(event.getDamager());
			if(!listContainsPlayer) {
				BartholomewPlayers.add((Player) event.getDamager());
			}
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
