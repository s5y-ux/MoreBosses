package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private final Map<UUID, BossBar> activeBossBars = new HashMap<>();

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
        }, 0L, 20L); // 20 ticks = 1 second
    }

    private void displayBar(Entity entity, double maxHealth, Player player, BarColor color) {
        BossBar bar = activeBossBars.computeIfAbsent(entity.getUniqueId(), id ->
                Bukkit.createBossBar(entity.getCustomName(), color, BarStyle.SOLID)
        );

        double progress = ((Mob) entity).getHealth() / maxHealth;
        bar.setProgress(Math.max(0.0, Math.min(1.0, progress)));
        bar.setVisible(true);
        if (!bar.getPlayers().contains(player)) {
            bar.addPlayer(player);
        }
    }

@EventHandler
public void onEntityDamage(EntityDamageByEntityEvent event) {
    if (!(event.getDamager() instanceof Player)) return;
    if (!(event.getEntity() instanceof Mob)) return;
    if (event.getEntity().getCustomName() == null) return;

    Player player = (Player) event.getDamager();
    FileConfiguration config = main.getConfig();
    String name = event.getEntity().getCustomName();

    if (name.equals(ChatColor.translateAlternateColorCodes('&', "&eAlbert"))) {
        displayBar(event.getEntity(), config.getDouble("AlbertHealth", 16), player, BarColor.YELLOW);

    } else if (name.equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
        displayBar(event.getEntity(), config.getDouble("OswaldoHealth", 300), player, BarColor.RED);

    } else if (name.equals(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"))) {
        displayBar(event.getEntity(), config.getDouble("BigBoyHealth", 300), player, BarColor.WHITE);

    } else if (name.equals(ChatColor.AQUA + "Timmothy")) {
        displayBar(event.getEntity(), config.getDouble("TimmothyHealth", 300), player, BarColor.BLUE);

    } else if (name.equals(ChatColor.BLACK + "Bartholomew")) {
        displayBar(event.getEntity(), config.getDouble("BartholomewHealth", 300), player, BarColor.PURPLE);

    } else if (name.equals(ChatColor.translateAlternateColorCodes('&', "&c&lPiGgY"))) {
        displayBar(event.getEntity(), config.getDouble("PiggyHealth", 300), player, BarColor.PINK);

    } else if (name.equals(ChatColor.DARK_PURPLE + "Dr. Strange")) {
        displayBar(event.getEntity(), config.getDouble("DrStrangeHealth", 300), player, BarColor.PURPLE);
    }
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
