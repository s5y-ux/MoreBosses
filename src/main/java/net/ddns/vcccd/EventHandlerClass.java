package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;


public class EventHandlerClass implements Listener {
	
	private final Main main;
	
	public EventHandlerClass(Main main) {
		this.main = main;
	}

    private int RNG(int scope) {
        return (new Random().nextInt(scope));
    }

    boolean isHoldingBombBow = false;

    @EventHandler
    public void shotbow(EntityShootBowEvent event) {
        ItemStack referenceBow = event.getBow();
        if (referenceBow.getItemMeta().getDisplayName().equals(ChatColor.RED + "BomBow")) {
            isHoldingBombBow = true;
        } else {
        	assert true;
        }
    }

    @EventHandler
    public void shotarrow(ProjectileHitEvent event) {
        if (isHoldingBombBow) {
            event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 3);
            isHoldingBombBow = false;
        } else {
        	assert true;
        }
    }

    @EventHandler
    public void movement(EntityDamageByEntityEvent event) {

        Entity ReferenceEntity = event.getEntity();
        
        if(ReferenceEntity.getCustomName() == null) {
        	assert true;
        }
        
        else if (ReferenceEntity.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
            if (RNG(4) == 3) {
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
                    if (near instanceof Player) {
                        Player player = (Player) near;
                        Location axis = player.getLocation().getDirection().toLocation(player.getWorld());
                        Vector VelocityVector = new Vector(-axis.getX(), 1.5, -axis.getZ());
                        player.setVelocity(VelocityVector.normalize());
                    }
                }

            }
            if (RNG(6) == 5) {
                ReferenceEntity.getWorld().spawnEntity(ReferenceEntity.getLocation(), EntityType.FIREBALL);
            }

            if (RNG(4) == 3) {
                Zombie Minion = (Zombie) ReferenceEntity.getWorld().spawnEntity(ReferenceEntity.getLocation(), EntityType.ZOMBIE);
                Minion.setBaby(true);
                Minion.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lMinion"));
                Minion.setCustomNameVisible(true);
                EntityEquipment equipment = Minion.getEquipment();
                equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
                equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
            }
            
        } else if (ReferenceEntity.getCustomName().equals(ChatColor.YELLOW + "Albert")) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                if (player.getInventory().getItemInMainHand().getItemMeta() == null){
                	if (RNG(2) == 1) {
                        Slime slime = (Slime) ReferenceEntity.getWorld().spawnEntity(ReferenceEntity.getLocation(), EntityType.SLIME);
                        slime.setCustomName(ChatColor.YELLOW + "Albert");
                        slime.setCustomNameVisible(true);
                        slime.setAI(true);
                        for (int i = 0; i < 15; i++) {
                            slime.getWorld().spawnParticle(Particle.CLOUD, slime.getLocation(), 5);
                        }
                    }
                    
                } else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&eAlbert Remover"))) {
                    if (ReferenceEntity instanceof Slime) {
                        Slime test = (Slime) ReferenceEntity;
                        test.remove();
                    }
                	
                } else {
                    if (RNG(2) == 1) {
                        Slime slime = (Slime) ReferenceEntity.getWorld().spawnEntity(ReferenceEntity.getLocation(), EntityType.SLIME);
                        slime.setCustomName(ChatColor.YELLOW + "Albert");
                        slime.setCustomNameVisible(true);
                        slime.setAI(true);
                        for (int i = 0; i < 15; i++) {
                            slime.getWorld().spawnParticle(Particle.CLOUD, slime.getLocation(), 5);
                        }
                    }
                }
            }
        }else if (ReferenceEntity.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"))) {
        	Giant os = (Giant) ReferenceEntity;
            if (RNG(3) == 2) {
                double X, Y, Z;
                X = ReferenceEntity.getLocation().getX();
                Y = ReferenceEntity.getLocation().getY();
                Z = ReferenceEntity.getLocation().getZ();

                for (int i = 0; i < 360; i++) {
                    double Xoffset = 8 * Math.cos(Math.toRadians(i));
                    double Yoffset = 8 * Math.sin(Math.toRadians(i));
                    ReferenceEntity.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, X + Xoffset, Y, Z + Yoffset, 1);
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
                Minion.setBaby(false);
            }
        } else {
            assert true;
        }
    }
}