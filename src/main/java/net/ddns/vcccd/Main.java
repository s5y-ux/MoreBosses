package net.ddns.vcccd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
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
		
		// Everything to the ==== at the bottom needs to be redone for readabillity.
		//TODO
		//=================================================================================================================

		FileConfiguration config = this.getConfig();

		File schemFolder = new File(getDataFolder(), "structures");
		if (!schemFolder.exists()) {
			schemFolder.mkdirs();
			ArrayList<File> outFile = new ArrayList<>();

			String[] structures = {"GeneratedTower.schem", "GortHouse.schem", "PiggyPin.schem", "StrangeLibrary.schem", "TimmothyHut.schem", "BartTower.schem"};
			for(String value: structures){
				outFile.add(new File(schemFolder, value));	
			}

			for(int i = 0; i < outFile.size(); i++){
				if(!outFile.get(i).exists()){
				try {
					InputStream in = getResource("structures/" + structures[i]);
					OutputStream out = new FileOutputStream(outFile.get(i));

					byte[] buffer = new byte[1024];
					int len;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}

				} catch (Exception e) {
					getConsole().sendMessage(getPluginPrefix() + ChatColor.RED + "File cannot be created... Please report this issue!");
				}
			}
			}
		}

		//=================================================================================================================
		
		config.addDefault("OswaldoHealth", 300);
		config.addDefault("BigBoyHealth", 300);
		config.addDefault("TimmothyHealth", 300);
		config.addDefault("BartholomewHealth", 300);
		config.addDefault("SpawnRNG", 50);
		config.addDefault("PiggyHealth", 300);
		config.addDefault("GortHealth", 300);
		config.addDefault("DrStrangeHealth",  300);
		config.addDefault("SpawnFrequency", 3);
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
		config.addDefault("EnableStructures", false);
		
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
		getServer().getPluginManager().registerEvents(new AlbertEvents(this), this);
		getServer().getPluginManager().registerEvents(new OswaldoEvents(this), this);
		getServer().getPluginManager().registerEvents(new OswaldoHelmetEvents(), this);
		getServer().getPluginManager().registerEvents(new BigBoyEvents(this), this);
		getServer().getPluginManager().registerEvents(new BigBoyTridentUse(), this);
		getServer().getPluginManager().registerEvents(new TimmothyEvents(this), this);
		getServer().getPluginManager().registerEvents(new BombowEvents(), this);
		getServer().getPluginManager().registerEvents(new BartholomewListener(this), this);
		getServer().getPluginManager().registerEvents(new LeveSwordEvent(), this);
		getServer().getPluginManager().registerEvents(new PiggyEvents(this), this);
		getServer().getPluginManager().registerEvents(new PiggyAxeEvents(), this);
		getServer().getPluginManager().registerEvents(new GortEvents(this), this);
		getServer().getPluginManager().registerEvents(new GortsHoeEvents(), this);
		getServer().getPluginManager().registerEvents(new DrStrangeEvents(this), this);
		getServer().getPluginManager().registerEvents(new SpawnInWorld(this), this);
		getServer().getPluginManager().registerEvents(new UpdateChecker(this), this);
		getServer().getPluginManager().registerEvents(new BossBars(this), this);
		getServer().getPluginManager().registerEvents(new TimeStoneEvents(this), this);
		getServer().getPluginManager().registerEvents(new EggEvents(this), this);
		getServer().getPluginManager().registerEvents(new GenerateStructures(this), this);
		
		console.sendMessage(getPluginPrefix() + "The MoreBosses Plugin has beed Loaded...");
		console.sendMessage(getPluginPrefix() + "Please note that this does not mean all features will work");
		console.sendMessage(getPluginPrefix() + "If issues are found with the plugin, report the issue to:");
		console.sendMessage(getPluginPrefix() + ChatColor.GREEN + "https://github.com/s5y-ux/MoreBosses/issues");
		console.sendMessage(getPluginPrefix() + "Trust me, I WILL see it");
		
	}
	
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
	}
}
