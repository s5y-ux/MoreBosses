package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.md_5.bungee.api.ChatColor;

public class SpawnBossCommand implements CommandExecutor, Listener {
	
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
			
			if(arg3.length == 0) {
				arg0.sendMessage("Usage: /spawnboss {boss}");
				return true;
			}
			
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
			
		} else {
			if(arg3.length < 2) {
				arg0.sendMessage("Usage: /spawnboss {player} {boss}");
			} else {
				try {
					Player player = Bukkit.getPlayer(arg3[0]);
					World chunkWorld = player.getWorld();
					Location spawnLocation = player.getLocation();
					
					switch(arg3[1]) {
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
					
					arg0.sendMessage(ChatColor.GREEN + "Spawned boss at player location successfully!" + ChatColor.RESET);
				} catch (Exception e) {
					arg0.sendMessage(ChatColor.RED + "Something weird happened in SpawnBossCommand class...");
					arg0.sendMessage("open up an issue here and i'll fix it -s5y" + ChatColor.RESET);
					arg0.sendMessage(ChatColor.YELLOW + "https://github.com/s5y-ux/MoreBosses/issues" + ChatColor.RESET);
				}
			}
		}
		return true;
	}

}
