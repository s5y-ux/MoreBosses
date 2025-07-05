package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	// Server console attribute
	private ConsoleCommandSender console = getServer().getConsoleSender();
	private String pluginPrefix = ChatColor.translateAlternateColorCodes('&', "&f[&6MoreBosses&f] - ");
	
	// Get server console in a safe way,
	public ConsoleCommandSender getConsole() {
		return this.console;
	}
	
	public String getPluginPrefix() {
		return this.pluginPrefix;
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
		config.addDefault("AnnounceBossKill", true);
		
		config.addDefault("OswaldoSpawn", true);
		config.addDefault("BigBoySpawn", false);
		config.addDefault("TimmothySpawn", false);
		config.addDefault("BartholomewSpawn", false);
		config.addDefault("PiggySpawn", true);
		config.addDefault("GortSpawn", true);
		config.addDefault("DrStrangeSpawn", true);
		
		config.addDefault("OswaldoDeSpawn", true);
		config.addDefault("BigBoyDeSpawn", false);
		config.addDefault("TimmothyDeSpawn", false);
		config.addDefault("BartholomewDeSpawn", false);
		config.addDefault("PiggyDeSpawn", true);
		config.addDefault("GortDeSpawn", true);
		config.addDefault("DrStrangeDeSpawn", true);
		
		config.addDefault("Worlds", "world,city");
		
		this.saveDefaultConfig();
		
		this.getCommand("morebosses").setExecutor(new BossSpawnGUI());
		this.getCommand("adminsword").setExecutor(new AdminSwordItem(this));
		this.getCommand("albertremover").setExecutor(new AlbertRemoverItem(this));
		this.getCommand("spawnboss").setExecutor(new SpawnBossCommand(this));
		this.getCommand("removebars").setExecutor(new BossBars(this));
		this.getCommand("despawnentities").setExecutor(new DespawnCommand(this));
		this.getCommand("bossegg").setExecutor(new EggCommand(this));
		
		//Registers the class for the damaged entity
		getServer().getPluginManager().registerEvents(new BossSpawnGUIEvents(this), this);
		getServer().getPluginManager().registerEvents(new AdminSwordListener(), this);	
		getServer().getPluginManager().registerEvents(new AlbertEvents(), this);
		getServer().getPluginManager().registerEvents(new OswaldoEvents(this), this);
		getServer().getPluginManager().registerEvents(new OswaldoHelmetEvents(), this);
		getServer().getPluginManager().registerEvents(new BigBoyEvents(), this);
		getServer().getPluginManager().registerEvents(new BigBoyTridentUse(), this);
		getServer().getPluginManager().registerEvents(new TimmothyEvents(), this);
		getServer().getPluginManager().registerEvents(new BombowEvents(), this);
		getServer().getPluginManager().registerEvents(new BartholomewListener(), this);
		getServer().getPluginManager().registerEvents(new LeveSwordEvent(), this);
		getServer().getPluginManager().registerEvents(new PiggyEvents(), this);
		getServer().getPluginManager().registerEvents(new PiggyAxeEvents(), this);
		getServer().getPluginManager().registerEvents(new GortEvents(), this);
		getServer().getPluginManager().registerEvents(new GortsHoeEvents(), this);
		getServer().getPluginManager().registerEvents(new DrStrangeEvents(), this);
		getServer().getPluginManager().registerEvents(new SpawnInWorld(this), this);
		getServer().getPluginManager().registerEvents(new UpdateChecker(this), this);
		getServer().getPluginManager().registerEvents(new BossBars(this), this);
		getServer().getPluginManager().registerEvents(new TimeStoneEvents(this), this);
		getServer().getPluginManager().registerEvents(new EggEvents(this), this);
		
		console.sendMessage(getPluginPrefix() + "The MoreBosses Plugin has beed Loaded...");
		console.sendMessage(getPluginPrefix() + "Please note that this does not mean all features will work");
		console.sendMessage(getPluginPrefix() + "If issues are found with the plugin, report the issue to:");
		console.sendMessage(getPluginPrefix() + ChatColor.GREEN + "https://github.com/s5y-ux/MoreBosses/issues");
		console.sendMessage(getPluginPrefix() + "Trust me, I WILL see it");
		
	}
	
	@Override
	public void onDisable() {
		
	}
}
