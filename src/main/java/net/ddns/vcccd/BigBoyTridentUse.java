package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class BigBoyTridentUse implements Listener {

    private final FileConfiguration config;

    public BigBoyTridentUse() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("MoreBosses");
        if (plugin == null) {
            throw new IllegalStateException("MoreBosses plugin not found!");
        }
        this.config = plugin.getConfig();
    }

    // Particle helper
    private void particle(Location playerLocation, Particle particle,
                          Vector playerFacingDirection, World playerWorld, int i, double[] offset) {
        double[] dir = {playerFacingDirection.getX(),
                playerFacingDirection.getY(), playerFacingDirection.getZ()};

        playerWorld.spawnParticle(particle,
                playerLocation.getX() + offset[0] + i * dir[0],
                playerLocation.getY() + offset[1] + i * dir[1],
                playerLocation.getZ() + offset[2] + i * dir[2], 3);
    }

    // Particle stream
    private void particles(Player player) {
        World playerWorld = player.getWorld();
        Location playerLocation = player.getLocation();
        Vector playerFacingDirection = playerLocation.getDirection();
        Particle lazer = Particle.HAPPY_VILLAGER;

        for (int i = 1; i < 100; i++) {
            double[][] vectorTuples = {
                    {0, 1.35, 0}, {0, 1.5, 0}, {0, 1.65, 0},
                    {-0.15, 1.5, 0}, {0, 1.5, -0.15}
            };
            for (double[] tuple : vectorTuples) {
                particle(playerLocation, lazer, playerFacingDirection, playerWorld, i, tuple);
            }
        }
    }

    // When player right-clicks to throw the trident
    @EventHandler
    public void onBigBoyTridentThrow(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;

        String name = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        if (!name.equals(ChatColor.translateAlternateColorCodes('&', "&e&lBig Boy's Trident"))) return;

        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            // Play throw sound & particles
            event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_AMETHYST_BLOCK_FALL, 500, 0);
            particles(event.getPlayer());
        }
    }

    // When the trident actually lands
    @EventHandler
    public void onBigBoyTridentHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Trident trident)) return;
        if (!(trident.getShooter() instanceof Player player)) return;

        // Check the actual thrown trident item
        ItemStack tridentItem = trident.getItem();
        if (tridentItem == null || !tridentItem.hasItemMeta()) return;

        String name = tridentItem.getItemMeta().getDisplayName();
        if (!name.equals(ChatColor.translateAlternateColorCodes('&', "&e&lBig Boy's Trident"))) return;

        int expCost = config.getInt("bigboytrident.exp-cost", 3);
        float explosionPower = (float) config.getDouble("bigboytrident.explosion-power", 5);
        boolean griefs = config.getBoolean("bigboytrident.griefs", true);

        // Only deduct EXP in survival/adventure
        if (player.getGameMode() != GameMode.CREATIVE) {
            if (player.getLevel() < expCost) {
                player.sendMessage(ChatColor.RED + "You don't have enough EXP to use this weapon...");
                return;
            }
            player.setLevel(player.getLevel() - expCost);
        }

        // Boom at exact impact location
        Location hitLocation = trident.getLocation();
        hitLocation.getWorld().createExplosion(hitLocation, explosionPower, griefs);
    }
}
