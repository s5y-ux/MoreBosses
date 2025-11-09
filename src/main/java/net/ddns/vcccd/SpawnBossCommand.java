package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class SpawnBossCommand implements CommandExecutor, Listener {

    private final Main main;
    private final FileConfiguration config;

    public SpawnBossCommand(Main main) {
        this.main = main;
        this.config = main.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(main.getPluginPrefix() + "Usage: /spawnboss <boss> [world] [x y z]");
            sender.sendMessage(main.getPluginPrefix() + "Usage (Player): /spawnboss <boss> [x y z]");
            return true;
        }

        String bossName = args[0];
        Location spawnLocation = null;
        World world = null;

        //  Universal Command: /spawnboss {boss} {world} {x} {y} {z}
        if (args.length == 5) {

            String worldName = args[1];
            world = Bukkit.getWorld(worldName);

            if (world == null) {
                sender.sendMessage(main.getPluginPrefix() + "World '" + worldName + "' not found.");
                return true;
            }

            try {
                double x = Double.parseDouble(args[2]);
                double y = Double.parseDouble(args[3]);
                double z = Double.parseDouble(args[4]);
                spawnLocation = new Location(world, x, y, z);
            } catch (NumberFormatException e) {
                sender.sendMessage(main.getPluginPrefix() + "Invalid coordinates. Usage: /spawnboss <boss> <world> <x> <y> <z>");
                return true;
            }


        } else if (sender instanceof Player) {

            Player player = (Player) sender;
            world = player.getWorld();

            if (args.length == 1) {
                // /spawnboss {boss} (Spawn at player's location)
                spawnLocation = player.getLocation();

            } else if (args.length == 4) {
                // /spawnboss {boss} {x} {y} {z}
                try {
                    double x = Double.parseDouble(args[1]);
                    double y = Double.parseDouble(args[2]);
                    double z = Double.parseDouble(args[3]);
                    spawnLocation = new Location(world, x, y, z);
                } catch (NumberFormatException e) {
                    sender.sendMessage(main.getPluginPrefix() + "Invalid coordinates.");
                    return true;
                }

            } else {
                sender.sendMessage(main.getPluginPrefix() + "Usage: /spawnboss <boss> [x y z]");
                return true;
            }

        } else {
            // --- Console Restriction ---
            sender.sendMessage(main.getPluginPrefix() + "Console must use: /spawnboss <boss> <world> <x> <y> <z>");
            return true;
        }

        if (spawnLocation == null || world == null) {
            sender.sendMessage(main.getPluginPrefix() + "Could not determine a valid location.");
            return true;
        }

        boolean success = spawnBoss(bossName.toLowerCase(), spawnLocation, world);

        if (success) {
            sender.sendMessage(main.getPluginPrefix() + "Spawned " + bossName + " successfully.");
        } else {
            sender.sendMessage(main.getPluginPrefix() + "Unknown boss: " + bossName);
        }

        return true;
    }

    private boolean spawnBoss(String name, Location location, World world) {
        switch (name) {
            case "oswaldo":
                new OswaldoEntity(config.getInt("OswaldoHealth"), location, world, main);
                break;
            case "bigboy":
                new BigBoyEntity(config.getInt("BigBoyHealth"), location, world, main);
                break;
            case "timmothy":
                new TimmothyEntity(config.getInt("TimmothySpawn"), location, world, main);
                break;
            case "bartholomew":
                new BartholomewEntity(config.getInt("BartholomewHealth"), location, world, main);
                break;
            case "piggy":
                new PiggyEntity(config.getInt("PiggyHealth"), location, world, main);
                break;
            case "gort":
                new GortEntity(config.getInt("GortHealth"), location, world, main);
                break;
            case "drstrange":
                new DrStrangeEntity(config.getInt("DrStrangeHealth"), location, world, main);
                break;
            default:
                return false;
        }
        return true;
    }
}
