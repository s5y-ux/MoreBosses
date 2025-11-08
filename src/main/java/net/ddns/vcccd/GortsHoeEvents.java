package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vindicator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GortsHoeEvents implements Listener {

    private final FileConfiguration config;

    public GortsHoeEvents() {
        this.config = Bukkit.getPluginManager().getPlugin("MoreBosses").getConfig();
    }

    private void spawnCobwebTrap(Entity target) {
        World world = target.getWorld();
        Location center = target.getLocation();
        int baseX = center.getBlockX();
        int baseY = center.getBlockY();
        int baseZ = center.getBlockZ();

        // Read decay time from config (default to 5 if missing)
        int decayTime = config.getInt("GortCobwebDecayTime", 5);

        // Place cobwebs (only replacing air) in the original pattern
        for (int i = 0; i < 2; i++) { // height levels
            for (int x = -1; x < 2; x = x + 2) {
                Location[] rel = {
                    new Location(world, baseX + x, baseY + i, baseZ + x),
                    new Location(world, baseX - x, baseY + i, baseZ),
                    new Location(world, baseX, baseY + i, baseZ - x)
                };
                for (Location o : rel) {
                    if (o.getBlock().getType() == Material.AIR) {
                        o.getBlock().setType(Material.COBWEB);
                    }
                }
            }
        }

        // If decayTime > 0, schedule revert to air
        if (decayTime > 0) {
            Bukkit.getScheduler().runTaskLater(
                Bukkit.getPluginManager().getPlugin("MoreBosses"),
                () -> {
                    for (int i = 0; i < 2; i++) {
                        for (int x = -1; x < 2; x = x + 2) {
                            Location[] rel = {
                                new Location(world, baseX + x, baseY + i, baseZ + x),
                                new Location(world, baseX - x, baseY + i, baseZ),
                                new Location(world, baseX, baseY + i, baseZ - x)
                            };
                            for (Location o : rel) {
                                if (o.getBlock().getType() == Material.COBWEB) {
                                    o.getBlock().setType(Material.AIR);
                                }
                            }
                        }
                    }
                },
                20L * decayTime
            );
        }
    }

    private boolean isGortsHoe(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() &&
               meta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&eGort's Hoe"));
    }

    private boolean isGortTheSerf(Vindicator vindicator) {
        return vindicator.getCustomName() != null &&
               vindicator.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eGort The Serf"));
    }

    @EventHandler
    public void onHoeUse(EntityDamageByEntityEvent event) {
        try {
            if (event.getDamager() instanceof Player player) {
                if (isGortsHoe(player.getInventory().getItemInMainHand())) {
                    for (int i = 0; i < 10; i++) {
                        player.getWorld().spawnParticle(Particle.FIREWORK, event.getEntity().getLocation(), 9);
                    }
                    spawnCobwebTrap(event.getEntity());
                    event.getEntity().setFireTicks(50);
                }
            } else if (event.getDamager() instanceof Vindicator vindicator) {
                if (isGortTheSerf(vindicator)) {
                    for (int i = 0; i < 10; i++) {
                        vindicator.getWorld().spawnParticle(Particle.FIREWORK, event.getEntity().getLocation(), 9);
                    }
                    spawnCobwebTrap(event.getEntity());
                    event.getEntity().setFireTicks(50);
                }
            }
        } catch (Exception ignored) {
        }
    }
}
