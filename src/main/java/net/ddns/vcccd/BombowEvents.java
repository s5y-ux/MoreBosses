package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BombowEvents implements Listener {

    private final JavaPlugin plugin;

    public BombowEvents() {
        this.plugin = (JavaPlugin) Bukkit.getPluginManager().getPlugin("MoreBosses");
    }

    @EventHandler
    public void bomBowArrowTracker(EntityShootBowEvent event) {
        try {
            ItemStack bowUsed = event.getBow();
            boolean isBombow = bowUsed != null &&
                    bowUsed.hasItemMeta() &&
                    bowUsed.getItemMeta().hasDisplayName() &&
                    bowUsed.getItemMeta().getDisplayName().equals(ChatColor.RED + "BomBow");

            if (isBombow) {
                event.getProjectile().setCustomName("Exploding Arrow");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onBombowShootEvent(ProjectileHitEvent event) {
        try {
            Projectile explosiveArrow = event.getEntity();
            if (explosiveArrow.getCustomName() == null ||
                !explosiveArrow.getCustomName().equals("Exploding Arrow")) {
                return; // Not a BomBow arrow
            }

            World world = explosiveArrow.getWorld();
            Location whereLanded = explosiveArrow.getLocation();

            FileConfiguration config = plugin.getConfig();
            boolean grief = config.getBoolean("bombowexplosion.grief", true);
            float power = (float) config.getDouble("bombowexplosion.power", 4.0);

            if (!grief) {
                // Place temporary water to absorb explosion
                Material originalMaterial = world.getBlockAt(whereLanded).getType();
                world.getBlockAt(whereLanded).setType(Material.WATER);

                // Remove the water block next tick
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (world.getBlockAt(whereLanded).getType() == Material.WATER) {
                        world.getBlockAt(whereLanded).setType(originalMaterial);
                    }
                }, 1L);
            }

            // Trigger the explosion with configurable power
            world.createExplosion(whereLanded, power, false, grief);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
