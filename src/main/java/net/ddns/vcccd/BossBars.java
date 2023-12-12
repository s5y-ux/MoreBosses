package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
@EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getCustomName() == null) {
            assert true;
        } else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eAlbert"))) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                Mob Albert = (Mob) event.getEntity();
                AlbertBar.addPlayer(player);
                AlbertBar.setProgress(Albert.getHealth() / 16);
                AlbertBar.setVisible(true);
            }
        }else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                Mob Oswaldo = (Mob) event.getEntity();
                OswaldoBar.addPlayer(player);
                OswaldoBar.setProgress(Oswaldo.getHealth() / 20);
                OswaldoBar.setVisible(true);
            }
        }else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"))) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                Mob BigBoy = (Mob) event.getEntity();
                BigBoyBar.addPlayer(player);
                BigBoyBar.setProgress(BigBoy.getHealth() / 100);
                BigBoyBar.setVisible(true);
            }
        }else if (event.getEntity().getCustomName().equals(ChatColor.AQUA + "Timmothy")) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                Mob Timmothy = (Mob) event.getEntity();
                TimmothyBar.addPlayer(player);
                TimmothyBar.setProgress(Timmothy.getHealth() / 20);
                TimmothyBar.setVisible(true);
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
        }else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
        	disableBar(OswaldoBar);
        }else if (event.getEntity().getCustomName().equals(ChatColor.BOLD + "Big Boy")) {
        	disableBar(BigBoyBar);
        }else if (event.getEntity().getCustomName().equals(ChatColor.AQUA + "Timmothy")) {
        	disableBar(TimmothyBar);
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
                TimmothyBar
            };
            for (BossBar Bar: BossBars) {
            	disableBar(Bar);
            }
            player.sendMessage(ChatColor.GREEN + "All boss bars removed, please re-log.");
        }
        return (true);
    }

}