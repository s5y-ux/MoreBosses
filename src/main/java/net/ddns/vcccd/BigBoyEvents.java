package net.ddns.vcccd;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BigBoyEvents implements Listener {
	
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
	public void onBigBoyDeathEvent(EntityDeathEvent event) {
		try {
			Giant bigBoy = (Giant) event.getEntity();
			boolean isBigBoy = bigBoy.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"));
			if(isBigBoy) {
				DropItemAt(event.getEntity(), CustomItem(Material.TRIDENT, ChatColor.translateAlternateColorCodes('&', "&e&lBig Boy\'s Trident")));
			}
		} catch (Exception e) {
			assert true;
		}
	}
			
	@EventHandler
	public void onBigBoyAttacked(EntityDamageByEntityEvent event) {
		try {
			Entity ReferenceEntity = event.getEntity();
			@SuppressWarnings("unused")
			Giant canCast = (Giant) ReferenceEntity;
            if (RNG(3) == 2) {
                double X, Y, Z;
                X = ReferenceEntity.getLocation().getX();
                Y = ReferenceEntity.getLocation().getY();
                Z = ReferenceEntity.getLocation().getZ();

                for (int i = 0; i < 360; i++) {
                    double Xoffset = 8 * Math.cos(Math.toRadians(i));
                    double Yoffset = 8 * Math.sin(Math.toRadians(i));
                    ReferenceEntity.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, X + Xoffset, Y, Z + Yoffset, 1);
                    ReferenceEntity.getWorld().spawnParticle(Particle.LAVA, X + Xoffset, Y, Z + Yoffset, 1);
                }

                for (Entity near: ReferenceEntity.getNearbyEntities(8, 8, 8)) {
                    if (near instanceof Player) {
                        Player player = (Player) near;
                        Location axis = player.getLocation().getDirection().toLocation(player.getWorld());
                        Vector VelocityVector = new Vector(-axis.getX(), 1.5, -axis.getZ());
                        player.setVelocity(VelocityVector.normalize());
                    }
                }

            }
            if (RNG(3) == 2) {
                ReferenceEntity.getWorld().spawnEntity(ReferenceEntity.getLocation(), EntityType.FIREBALL);
            }

            if (RNG(2) == 1) {
                Zombie Minion = (Zombie) ReferenceEntity.getWorld().spawnEntity(ReferenceEntity.getLocation(), EntityType.ZOMBIE);               
                Minion.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lMinion"));
                Minion.setCustomNameVisible(true);
                EntityEquipment equipment = Minion.getEquipment();
                equipment.setHelmet(new ItemStack(Material.GOLDEN_HELMET));
                equipment.setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
                Minion.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999999, 3));
                Minion.setAdult();
            }
		} catch (Exception e) {
			assert true;
		}
	}
}
