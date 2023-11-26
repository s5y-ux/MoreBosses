package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

public class MobSpawn implements CommandExecutor{
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Location PlayerLocation = player.getLocation();
        Slime slime = (Slime) player.getWorld().spawnEntity(PlayerLocation, EntityType.SLIME);
        slime.setCustomName(ChatColor.YELLOW + "Albert");
        slime.setCustomNameVisible(true);
        slime.setAI(true);
                
		return false;
    }
}
