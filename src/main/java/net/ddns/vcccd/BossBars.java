package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class BossBars implements CommandExecutor, Listener {

    private final Main main;
    // Map to store actively loaded BossBars, keyed by the entity's UUID.
    private final Map<UUID, BossBar> activeBossBars = new HashMap<>();

    // Static data class to hold constant boss information
    private static class BossProperties {
        public final String configKey;
        public final double defaultHealth;
        public final BarColor color;

        public BossProperties(String configKey, double defaultHealth, BarColor color) {
            this.configKey = configKey;
            this.defaultHealth = defaultHealth;
            this.color = color;
        }
    }

    // Static map defining all custom bosses and their properties
    private static final Map<String, BossProperties> BOSS_DATA = new HashMap<>();
    static {
        // Name (with color codes translated), ConfigKey, DefaultHealth, Color
        BOSS_DATA.put(ChatColor.translateAlternateColorCodes('&', "&eAlbert"), new BossProperties("AlbertHealth", 16.0, BarColor.YELLOW));
        BOSS_DATA.put(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"), new BossProperties("OswaldoHealth", 300.0, BarColor.RED));
        BOSS_DATA.put(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"), new BossProperties("BigBoyHealth", 300.0, BarColor.WHITE));
        BOSS_DATA.put(ChatColor.AQUA + "Timmothy", new BossProperties("TimmothyHealth", 300.0, BarColor.BLUE));
        BOSS_DATA.put(ChatColor.BLACK + "Bartholomew", new BossProperties("BartholomewHealth", 300.0, BarColor.PURPLE));
        BOSS_DATA.put(ChatColor.translateAlternateColorCodes('&', "&c&lPiGgY"), new BossProperties("PiggyHealth", 300.0, BarColor.PINK));
        BOSS_DATA.put(ChatColor.DARK_PURPLE + "Dr. Strange", new BossProperties("DrStrangeHealth", 300.0, BarColor.PURPLE));
        BOSS_DATA.put(ChatColor.translateAlternateColorCodes('&', "&eGort The Serf"), new BossProperties("GortHealth", 30.0, BarColor.YELLOW));
    }

    public BossBars(Main main) {
        this.main = main;

        // Watchdog: cleanup dead/removed bosses every second
        Bukkit.getScheduler().runTaskTimer(main, () -> {
            Iterator<Map.Entry<UUID, BossBar>> it = activeBossBars.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<UUID, BossBar> entry = it.next();
                Entity e = Bukkit.getEntity(entry.getKey());
                if (e == null || e.isDead()) {
                    entry.getValue().removeAll();
                    it.remove();
                }
            }
        }, 0L, 20L);

        Bukkit.getScheduler().runTaskTimer(main, this::proximityWatcher, 0L, 5L);
    }

    // --- PROXIMITY LOGIC (Replaces Damage Logic) ---

    private void proximityWatcher() {
        FileConfiguration config = main.getConfig();
        // Set the radius (in blocks) for the bar to appear.
        final double PROXIMITY_RADIUS = 40.0;

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {

                String name = entity.getCustomName();
                // Check if entity is a boss
                if (name == null || !(entity instanceof Mob)) continue;

                BossProperties props = BOSS_DATA.get(name);
                if (props == null) continue; // Not a custom boss

                Mob mob = (Mob) entity;
                UUID bossId = entity.getUniqueId();

                // Create BossBar
                BossBar bar = activeBossBars.computeIfAbsent(bossId, id -> {
                    // Get Max Health from config or use default value
                    double maxHealth = config.getDouble(props.configKey, props.defaultHealth);

                    // Ensure the mob's max health is correct upon discovery
                    mob.setMaxHealth(maxHealth);

                    // Create the bossbar
                    return Bukkit.createBossBar(name, props.color, BarStyle.SOLID);
                });

                // Update Health/Progress and Title
                double maxHealth = mob.getMaxHealth();
                double currentHealth = mob.getHealth();
                double progress = currentHealth / maxHealth;

                bar.setProgress(Math.max(0.0, Math.min(1.0, progress)));

                bar.setTitle(name);

                // Manage Player Visibility (Proximity Check)
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld().equals(entity.getWorld())) {
                        double distance = player.getLocation().distance(entity.getLocation());

                        if (distance <= PROXIMITY_RADIUS) {
                            if (!bar.getPlayers().contains(player)) {
                                bar.addPlayer(player);
                                bar.setVisible(true);
                            }
                        } else {
                            if (bar.getPlayers().contains(player)) {
                                bar.removePlayer(player);
                            }
                        }
                    } else {
                        // Player is in a different world, remove them
                        if (bar.getPlayers().contains(player)) {
                            bar.removePlayer(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        BossBar bar = activeBossBars.remove(event.getEntity().getUniqueId());
        if (bar != null) {
            bar.removeAll();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            for (BossBar bar : activeBossBars.values()) {
                bar.removeAll();
            }
            activeBossBars.clear();
            sender.sendMessage(main.getPluginPrefix() + "All boss bars removed.");
        }
        return true;
    }
}
