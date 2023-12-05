package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	//Gets the server console
	ConsoleCommandSender console = getServer().getConsoleSender();
	
	//Code to run when the server is enabled
	@Override
	public void onEnable() {
		
		//Sends a message to let you know that the plugin was enabled
		console.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lMoreBosses Plugin Enabled..."));
		this.getCommand("albert").setExecutor(new MobSpawn());
		this.getCommand("oswaldo").setExecutor(new ZombieSpawn());
		this.getCommand("tim").setExecutor(new TimmothySpawn());
		this.getCommand("albertremover").setExecutor(new AlbertStick());
		this.getCommand("bigboy").setExecutor(new BigBoy());
		//Registers the class for the damaged entity
		getServer().getPluginManager().registerEvents(new EventHandlerClass(), this);
		
	}
	
	@Override
	public void onDisable() {
		
	}
}
