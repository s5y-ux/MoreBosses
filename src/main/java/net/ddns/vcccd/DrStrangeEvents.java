package net.ddns.vcccd;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DrStrangeEvents implements Listener {

    private final Main main;

    public DrStrangeEvents(Main main) {
        this.main = main;
    }

    private void spawnExperienceOrbs(Location location, int totalOrbs, int expPerOrb) {
        World world = location.getWorld();
        for (int i = 0; i < totalOrbs; i++) {
            ExperienceOrb orb = (ExperienceOrb) world.spawn(location, ExperienceOrb.class);
            orb.setExperience(expPerOrb);
        }
    }

    @EventHandler
    public void onDrStrangeAttack(EntityDamageByEntityEvent event) {
        try {
            if (!(event.getDamager() instanceof Enderman)) return;

            Enderman drStrange = (Enderman) event.getDamager();
            if (!((ChatColor.DARK_PURPLE + "Dr. Strange").equals(drStrange.getCustomName()))) return;

            Player targetPlayer;

            if (main.getConfig().getBoolean("DrStrangeAngryAtClosestPlayer")) {
                // Target closest player
                double closestDistance = Double.MAX_VALUE;
                Player closest = null;
                for (Player p : drStrange.getWorld().getPlayers()) {
                    double dist = p.getLocation().distance(drStrange.getLocation());
                    if (dist < closestDistance) {
                        closestDistance = dist;
                        closest = p;
                    }
                }
                targetPlayer = closest;
            } else {
                // Attack whoever triggered the event
                if (!(event.getEntity() instanceof Player)) return;
                targetPlayer = (Player) event.getEntity();
            }

            if (targetPlayer == null) return;

            Random rand = new Random();

            int radiusX = main.getConfig().getInt("TeleportAllowedRadius.x", 15);
            int radiusY = main.getConfig().getInt("TeleportAllowedRadius.y", 2);
            int radiusZ = main.getConfig().getInt("TeleportAllowedRadius.z", 15);

            // Load allowed teleport blocks from config
            List<String> blockList = main.getConfig().getStringList("DrStrangeTeleportBlockType");
            Set<Material> allowedBlocks = new HashSet<>();
            boolean allowAllBlocks = false;

            for (String s : blockList) {
                if (s.equalsIgnoreCase("minecraft:*") || s.equals("*")) {
                    allowAllBlocks = true;
                    break; // no need to check further
                }
                try {
                    Material m = Material.matchMaterial(s.replace("minecraft:", ""));
                    if (m != null) allowedBlocks.add(m);
                } catch (Exception ignored) {}
            }

            Location targetLoc = findSafeLocation(
                targetPlayer.getWorld(),
                targetPlayer.getLocation(),
                radiusX, radiusY, radiusZ,
                rand, allowedBlocks, allowAllBlocks
            );

            if (targetLoc != null) {
                targetPlayer.teleport(targetLoc);
                drStrange.teleport(targetLoc.clone().add(1, 0, 0)); // slight offset
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a safe location within the given radius where the block below is in the allowedBlocks set.
     */
private Location findSafeLocation(World world, Location base, int radiusX, int radiusY, int radiusZ,
                                  Random rand, Set<Material> allowedBlocks, boolean allowAllBlocks) {
    for (int i = 0; i < 20; i++) { // Max 20 tries
        double offsetX = rand.nextInt(radiusX * 2 + 1) - radiusX;
        double offsetY = rand.nextInt(radiusY * 2 + 1) - radiusY;
        double offsetZ = rand.nextInt(radiusZ * 2 + 1) - radiusZ;

        Location blockBelowLoc = base.clone().add(offsetX, offsetY, offsetZ);

        if (blockBelowLoc.getY() <= 0) continue;

        Material blockType = blockBelowLoc.getBlock().getType();
        if (allowAllBlocks || allowedBlocks.contains(blockType)) {
            // Return the block space above the surface block
            return blockBelowLoc.clone().add(0, 1, 0);
        }
    }
    return null;
}

    @EventHandler
    public void onDrStrangeDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Enderman &&
            (ChatColor.DARK_PURPLE + "Dr. Strange").equals(event.getEntity().getCustomName())) {

            ItemStack timeStone = new ItemStack(Material.EMERALD);
            ItemMeta timeMeta = timeStone.getItemMeta();
            timeMeta.setDisplayName(ChatColor.GREEN + "Time Stone");
            timeMeta.setLore(List.of(
                ChatColor.GRAY + "A powerful stone that controls time.",
                ChatColor.translateAlternateColorCodes('&', "&eRight-click &7to slow down time for nearby entities."),
                ChatColor.translateAlternateColorCodes('&', "&eRight-click &7on blocks to speed up growth."),
                ChatColor.translateAlternateColorCodes('&', "&eShift + Right-click &7on entities to toggle their age.")
            ));
            timeStone.setItemMeta(timeMeta);

            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), timeStone);
            spawnExperienceOrbs(event.getEntity().getLocation(), 100, 2);

            if (main.getConfig().getBoolean("AnnounceBossKill")) {
                for (Player player : main.getServer().getOnlinePlayers()) {
                    player.sendMessage(main.getPluginPrefix() + "Dr. Strange has been slain!");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
                }
            }
        }
    }
}
