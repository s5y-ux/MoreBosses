package net.ddns.vcccd;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BartholomewListener implements Listener {
	private ArrayList<Player> BartholomewPlayers = new ArrayList<>();
	private final Main main;

	public BartholomewListener(Main main) { this.main = main; }

	// Helper Methods:
	//================================
	private int RNG(int scope) {
        return (new Random().nextInt(scope));
    }

	private void spawnExperienceOrbs(Location location, int totalOrbs, int expPerOrb) {
		World world = location.getWorld();
		for (int i = 0; i < totalOrbs; i++) {
			ExperienceOrb orb = (ExperienceOrb) world.spawn(location, ExperienceOrb.class);
			orb.setExperience(expPerOrb);
		}
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
			spawnExperienceOrbs(event.getEntity().getLocation(), 100, 2);

				if(main.getConfig().getBoolean("AnnounceBossKill")){
				for(Player player: main.getServer().getOnlinePlayers()) {
					player.sendMessage(main.getPluginPrefix() + "Bartholomew has been slain!");
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);

				}
			}
		}
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
                    ItemStack heldItem = player.getInventory().getItemInMainHand();
                    if (heldItem != null && heldItem.getType() != Material.AIR) {
                        player.getWorld().dropItemNaturally(player.getLocation(), heldItem);
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        player.sendMessage("You have been disarmed...");
                    }
                }
            }
        }
    }
} 

} 
