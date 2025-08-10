package net.ddns.vcccd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DespawnCommand implements CommandExecutor {
    private final Main main;

    public DespawnCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        int radius;

        if (args.length > 0) {
            try {
                radius = Integer.parseInt(args[0]);
                if (radius <= 0) {
                    player.sendMessage(main.getPluginPrefix() + "Please enter a positive number for radius.");
                    return true;
                }
            } catch (NumberFormatException e) {
                player.sendMessage(main.getPluginPrefix() + "Invalid number. Usage: /" + label + " [radius]");
                return true;
            }
        } else {

            radius = main.getConfig().getInt("despawn-radius");
            if (radius <= 0) {
                player.sendMessage(main.getPluginPrefix() + "Invalid despawn-radius in config.yml. Using default of 500.");
                radius = 500;
            }
        }

        int removedCount = 0;
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player)) {
                entity.remove();
                removedCount++;
            }
        }

        player.sendMessage(main.getPluginPrefix() +
                "Despawned " + removedCount + " entities within " + radius + " blocks.");
        return true;
    }
}
