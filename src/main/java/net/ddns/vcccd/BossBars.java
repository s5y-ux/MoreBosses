package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class BossBars implements CommandExecutor, Listener {
    private BossBar AlbertBar = Bukkit.createBossBar(ChatColor.YELLOW + "Albert", BarColor.YELLOW, BarStyle.SOLID);
    private BossBar OswaldoBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"), BarColor.RED, BarStyle.SOLID);
    private BossBar BigBoyBar = Bukkit.createBossBar(ChatColor.BOLD + "Big Boy", BarColor.WHITE, BarStyle.SOLID);
    private BossBar TimmothyBar = Bukkit.createBossBar(ChatColor.AQUA + "Timmothy", BarColor.BLUE, BarStyle.SOLID);
    private BossBar BartholomewBar = Bukkit.createBossBar(ChatColor.BLACK + "Bartholomew", BarColor.PURPLE, BarStyle.SOLID);

    private void displayBar(BossBar Bar, Entity entity, int maxHealth, Player player) {
        Mob mob = (Mob) entity;
        Bar.addPlayer(player);
        Bar.setProgress(mob.getHealth() / maxHealth);
        Bar.setVisible(true);
    }

    @EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getCustomName() == null) {
            assert true;
        } else {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eAlbert"))) {
                displayBar(AlbertBar, event.getEntity(), 16, player);

            } else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {

                displayBar(OswaldoBar, event.getEntity(), 20, player);

            } else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"))) {

                displayBar(BigBoyBar, event.getEntity(), 100, player);

            } else if (event.getEntity().getCustomName().equals(ChatColor.AQUA + "Timmothy")) {
                displayBar(TimmothyBar, event.getEntity(), 20, player);

            } else if (event.getEntity().getCustomName().equals(ChatColor.BLACK + "Bartholomew")) {
                displayBar(BartholomewBar, event.getEntity(), 20, player);

            }
        }
    }
    }

    private void disableBar(BossBar Bar) {
        Bar.setProgress(0);
        Bar.setVisible(false);
        Bar.removeAll();
    }

    @EventHandler
    public void OnEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getCustomName() == null) {
            assert true;
        } else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eAlbert"))) {
            disableBar(AlbertBar);
        } else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
            disableBar(OswaldoBar);
        } else if (event.getEntity().getCustomName().equals(ChatColor.BOLD + "Big Boy")) {
            disableBar(BigBoyBar);
        } else if (event.getEntity().getCustomName().equals(ChatColor.AQUA + "Timmothy")) {
            disableBar(TimmothyBar);
        } else if (event.getEntity().getCustomName().equals(ChatColor.BLACK + "Bartholomew")) {
            disableBar(BartholomewBar);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            BossBar[] BossBars = {
                AlbertBar,
                OswaldoBar,
                BigBoyBar,
                TimmothyBar,
                BartholomewBar
            };
            for (BossBar Bar: BossBars) {
                disableBar(Bar);
            }
            player.sendMessage(ChatColor.GREEN + "All boss bars removed, please re-log.");
        }
        return (true);
    }

}