package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	//Gets the server console
	ConsoleCommandSender console = getServer().getConsoleSender();
	
	public void message() {
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n&c  __  __ ____  \n"
				+ " |  \\/  |  _ \\ \n"
				+ " | \\  / | |_) |\n"
				+ " | |\\/| |  _ < \n"
				+ " | |  | | |_) |\n"
				+ " |_|  |_|____/ "));
	}
	
	//Code to run when the server is enabled
	@Override
	public void onEnable() {
		
		FileConfiguration config = this.getConfig();
		
		config.addDefault("OswaldoHealth", 300);
		config.addDefault("BigBoyHealth", 300);
		config.addDefault("TimmothyHealth", 300);
		config.addDefault("BartholomewHealth", 300);
		config.addDefault("SpawnRNG", 500);
		config.addDefault("PiggyHealth", 300);
		config.addDefault("GortHealth", 300);
		config.addDefault("DrStrangeHealth",  300);
		config.addDefault("SpawnInWorld", false);
		config.addDefault("OswaldoSpawn", true);
		config.addDefault("BigBoySpawn", false);
		config.addDefault("TimmothySpawn", false);
		config.addDefault("BartholomewSpawn", false);
		config.addDefault("DisplayAscii", true);
		config.addDefault("PiggySpawn", true);
		config.addDefault("GortSpawn", true);
		config.addDefault("DrStrangeSpawn", true);
		config.addDefault("Worlds", "world,city");
		
		this.saveDefaultConfig();
		
		this.getCommand("albertremover").setExecutor(new AlbertStick());
		this.getCommand("morebosses").setExecutor(new BossSpawnGUI(this));
		this.getCommand("removebars").setExecutor(new BossBars(this));
		this.getCommand("adminsword").setExecutor(new AdminSword());
		this.getCommand("spawnboss").setExecutor(new SpawnBossCommand(this));
		//Registers the class for the damaged entity
		getServer().getPluginManager().registerEvents(new EventHandlerClass(), this);
		getServer().getPluginManager().registerEvents(new BossSpawnGUI(this), this);
		getServer().getPluginManager().registerEvents(new EntitiesDeathListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerItemsEvent(), this);
		getServer().getPluginManager().registerEvents(new BossBars(this), this);
		getServer().getPluginManager().registerEvents(new bartholomewListener(), this);
		getServer().getPluginManager().registerEvents(new UpdateChecker(), this);
		getServer().getPluginManager().registerEvents(new AdminSword(), this);
		getServer().getPluginManager().registerEvents(new TridentListener(), this);
		getServer().getPluginManager().registerEvents(new BossSpawn(this), this);
		getServer().getPluginManager().registerEvents(new ListenToAxe(), this);
		
		if(this.getConfig().getBoolean("DisplayAscii") == true) {
			//Sends a message to let you know that the plugin was enabled
			message();
		}
		
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lMoreBosses Plugin Enabled..."));
		
	}
	
	@Override
	public void onDisable() {
		
	}
}
