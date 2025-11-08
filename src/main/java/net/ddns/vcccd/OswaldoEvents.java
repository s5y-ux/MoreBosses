package net.ddns.vcccd;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class OswaldoEvents implements Listener {

    private final Main main;

    public OswaldoEvents(Main main) {
        this.main = main;

        
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if (entity instanceof Zombie) {
                            Zombie zombie = (Zombie) entity;
                            if (zombie.getCustomName() != null &&
                                zombie.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
                                if (RNG(3) == 0) { 
                                    doFireballAttack(zombie);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(main, 0L, 100L); 
    }

    
    private int RNG(int scope) {
        return (new Random().nextInt(scope));
    }

    private ItemStack CustomItem(Material item, String name) {
        ItemStack ReturnItem = new ItemStack(item);
        ItemMeta ReturnItemData = ReturnItem.getItemMeta();
        ReturnItemData.setDisplayName(name);
        ReturnItem.setItemMeta(ReturnItemData);
        return ReturnItem;
    }

    private void DropItemAt(LivingEntity entity, ItemStack item) {
        Location location = entity.getLocation();
        World world = entity.getWorld();
        world.dropItem(location, item);
    }

    private void spawnExperienceOrbs(Location location, int totalOrbs, int expPerOrb) {
        World world = location.getWorld();
        for (int i = 0; i < totalOrbs; i++) {
            ExperienceOrb orb = (ExperienceOrb) world.spawn(location, ExperienceOrb.class);
            orb.setExperience(expPerOrb);
        }
    }

    private void doFireballAttack(Zombie oswaldo) {
        Location loc = oswaldo.getLocation().add(0, 1.3, 0); 
        org.bukkit.entity.Fireball fireball = (org.bukkit.entity.Fireball)
                oswaldo.getWorld().spawnEntity(loc, EntityType.FIREBALL);

        fireball.setDirection(oswaldo.getLocation().getDirection()); 
        fireball.setYield(1F); 
        fireball.setIsIncendiary(true); 
        fireball.setShooter(oswaldo); 
    }

    
    @EventHandler
    public void onOswaldoDeath(EntityDeathEvent event) {
        try {
            Zombie oswaldo = (Zombie) event.getEntity();
            boolean isOswaldo = oswaldo.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"));

            if (isOswaldo) {
                DropItemAt(oswaldo, CustomItem(Material.NETHERITE_HELMET,
                        ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo\'s Helmet")));
                spawnExperienceOrbs(event.getEntity().getLocation(), 100, 2);

                if (main.getConfig().getBoolean("AnnounceBossKill")) {
                    for (Player player : main.getServer().getOnlinePlayers()) {
                        player.sendMessage(main.getPluginPrefix() + "Oswaldo has been slain!");
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
                    }
                }
            }

        } catch (Exception e) {
            assert true;
        }
    }

    @EventHandler
    public void onOswaldoHit(EntityDamageByEntityEvent event) {
        try {
            Zombie oswaldo = (Zombie) event.getEntity();
            boolean isOswaldo = oswaldo.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"));

            if (isOswaldo) {
                
                if (RNG(4) == 3) {
                    double X = oswaldo.getLocation().getX();
                    double Y = oswaldo.getLocation().getY();
                    double Z = oswaldo.getLocation().getZ();

                    for (int i = 0; i < 360; i += 6) {
                        double Xoffset = 4 * Math.cos(Math.toRadians(i));
                        double Yoffset = 4 * Math.sin(Math.toRadians(i));
                        oswaldo.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, X + Xoffset, Y, Z + Yoffset, 1);
                        oswaldo.getWorld().spawnParticle(Particle.LAVA, X + Xoffset, Y, Z + Yoffset, 1);
                    }

                    for (Entity near : oswaldo.getNearbyEntities(4, 4, 4)) {
                        if (near instanceof Player) {
                            Player player = (Player) near;
                            Location axis = player.getLocation().getDirection().toLocation(player.getWorld());
                            Vector VelocityVector = new Vector(-axis.getX(), 1.5, -axis.getZ());
                            player.setVelocity(VelocityVector.normalize());
                        }
                    }
                }

                
                if (RNG(6) == 5) {
                    doFireballAttack(oswaldo);
                }

                
                if (RNG(4) == 3) {
                    Zombie Minion = (Zombie) oswaldo.getWorld().spawnEntity(oswaldo.getLocation(), EntityType.ZOMBIE);
                    Minion.setBaby();
                    Minion.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lMinion"));
                    Minion.setCustomNameVisible(true);
                    EntityEquipment equipment = Minion.getEquipment();
                    equipment.setHelmet(new ItemStack(Material.IRON_HELMET));
                    equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                }
            }

        } catch (Exception e) {
            assert true;
        }
    }
}
