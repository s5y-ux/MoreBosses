package net.ddns.vcccd;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SpawnBossCommand implements CommandExecutor {
	
	private final Main main;
	private FileConfiguration config;
	
	public SpawnBossCommand(Main main) {
		this.main = main;
		this.config = main.getConfig();
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		if(arg0 instanceof Player) {
			Player player  = (Player) arg0;
			Location spawnLocation = player.getLocation();
			World chunkWorld = player.getWorld();
			
			switch(arg3[0]) {
			case "oswaldo":
				player.sendMessage(ChatColor.GREEN + "Spawning Oswaldo");
				new Oswaldo(this.config.getInt("OswaldoHealth"), spawnLocation, chunkWorld);
				break;
			case "bigboy":
				player.sendMessage(ChatColor.GREEN + "Spawning Big Boy");
				new BigBoy(this.config.getInt("BigBoyHealth"), spawnLocation, chunkWorld);
				break;
			case "timmothy":
				player.sendMessage(ChatColor.GREEN + "Spawning Timmothy");
				new Timmothy(this.config.getInt("TimmothySpawn"), spawnLocation, chunkWorld);
				break;
			case "bartholomew":
				player.sendMessage(ChatColor.GREEN + "Spawning Bartholomew");
				new bartholomew(this.config.getInt("BartholomewHealth"), spawnLocation, chunkWorld);
				break;
			case "piggy":
				player.sendMessage(ChatColor.GREEN + "Spawning Piggy");
				new Piggy(this.config.getInt("PiggyHealth"), spawnLocation, chunkWorld);
				break;
			case "gort":
				player.sendMessage(ChatColor.GREEN + "Spawning Gort");
				new VinNumber(this.config.getInt("GortHealth"), spawnLocation, chunkWorld);
				break;
			case "drstrange":
				player.sendMessage(ChatColor.GREEN + "Spawning Dr. Strange");
				new DrStrange(this.config.getInt("DrStrangeHealth"), spawnLocation, chunkWorld);
				break;
			default:
				return(false);
			}
		}
		return true;
	}

}
