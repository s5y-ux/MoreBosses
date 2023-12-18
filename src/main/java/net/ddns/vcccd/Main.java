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
		this.getCommand("albertremover").setExecutor(new AlbertStick());
		this.getCommand("morebosses").setExecutor(new BossSpawnGUI());
		this.getCommand("removebars").setExecutor(new BossBars());
		//Registers the class for the damaged entity
		getServer().getPluginManager().registerEvents(new EventHandlerClass(), this);
		getServer().getPluginManager().registerEvents(new BossSpawnGUI(), this);
		getServer().getPluginManager().registerEvents(new EntitiesDeathListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerItemsEvent(), this);
		getServer().getPluginManager().registerEvents(new BossBars(), this);
		getServer().getPluginManager().registerEvents(new bartholomewListener(), this);
		getServer().getPluginManager().registerEvents(new UpdateChecker(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
