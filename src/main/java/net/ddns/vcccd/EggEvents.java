package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EggEvents implements Listener {

    private final Main main;

    public EggEvents(Main main) {
        this.main = main;
    }

    private void spawnEffect(Location loc, World world) {
        world.spawnParticle(Particle.CLOUD, loc, 30);
        world.playSound(loc, Sound.ENTITY_VILLAGER_HURT, 1, 0);
    }

    @EventHandler
    public void onBossEggSpawn(PlayerEggThrowEvent event) {
        Egg eggInQuestion = event.getEgg();
        Player player = event.getPlayer();
        ItemStack eggItem = event.getEgg().getItem(); // The egg item used
        if (eggItem == null || !eggItem.hasItemMeta()) return;
        ItemMeta meta = eggItem.getItemMeta();
        if (!meta.hasDisplayName()) return;

        String displayName = ChatColor.stripColor(meta.getDisplayName());
        Location loc = eggInQuestion.getLocation();
        World world = eggInQuestion.getWorld();
        FileConfiguration config = main.getConfig();

        switch (displayName.toLowerCase()) {
            case "oswaldo egg":
                new OswaldoEntity(config.getInt("OswaldoHealth"), loc, world, main);
                player.sendMessage(main.getPluginPrefix() + "You have spawned Oswaldo!");
                spawnEffect(loc, world);
                event.setHatching(false);
                break;
            case "bigboy egg":
                new BigBoyEntity(config.getInt("BigBoyHealth"), loc, world, main);
                player.sendMessage(main.getPluginPrefix() + "You have spawned BigBoy!");
                spawnEffect(loc, world);
                event.setHatching(false);
                break;
            case "timmothy egg":
                new TimmothyEntity(config.getInt("TimmothyHealth"), loc, world, main);
                player.sendMessage(main.getPluginPrefix() + "You have spawned Timmothy!");
                spawnEffect(loc, world);
                event.setHatching(false);
                break;
            case "albert egg":
                new AlbertEntity(config.getInt("AlbertHealth"), loc, world);
                player.sendMessage(main.getPluginPrefix() + "You have spawned Albert!");
                spawnEffect(loc, world);
                event.setHatching(false);
                break;
            case "bartholomew egg":
                new BartholomewEntity(config.getInt("BartholomewHealth"), loc, world, main);
                player.sendMessage(main.getPluginPrefix() + "You have spawned Bartholomew!");
                spawnEffect(loc, world);
                event.setHatching(false);
                break;
            case "dr. strange egg":
                new DrStrangeEntity(config.getInt("DrStrangeHealth"), loc, world, main);
                player.sendMessage(main.getPluginPrefix() + "You have spawned Dr. Strange!");
                spawnEffect(loc, world);
                event.setHatching(false);
                break;
            case "gort egg":
                new GortEntity(config.getInt("GortHealth"), loc, world, main);
                player.sendMessage(main.getPluginPrefix() + "You have spawned Gort!");
                spawnEffect(loc, world);
                event.setHatching(false);
                break;
            case "piggy egg":
                new PiggyEntity(config.getInt("PiggyHealth"), loc, world, main);
                player.sendMessage(main.getPluginPrefix() + "You have spawned Piggy!");
                spawnEffect(loc, world);
                event.setHatching(false);
                break;
        }
    }
}
