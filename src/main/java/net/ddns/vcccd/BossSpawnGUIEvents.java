package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BossSpawnGUIEvents implements Listener{
	
	private final Main main;
	public BossSpawnGUIEvents(Main main) {
		this.main = main;
	}
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        FileConfiguration config = main.getConfig();
        Location playerLocation = player.getLocation();
        if (event.getCurrentItem() == null) {
            assert true;

        } else if (event.getInventory().getSize() == 18) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lAlbert&4&ki"))) {
                player.closeInventory();
                Slime slime = (Slime) player.getWorld().spawnEntity(playerLocation, EntityType.SLIME);
                slime.setCustomName(ChatColor.YELLOW + "Albert");
                slime.setCustomNameVisible(true);
                slime.setAI(true);
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lOswaldo&4&ki"))) {
                player.closeInventory();
                new OswaldoEntity(config.getInt("OswaldoHealth"), player.getLocation(), player.getWorld(), main);      
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lBig Boy&4&ki"))) {
                player.closeInventory();
                new BigBoyEntity(config.getInt("BigBoyHealth"), player.getLocation(), player.getWorld(), main);
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lTimmothy&4&ki"))) {
                player.closeInventory();
                new TimmothyEntity(config.getInt("TimmothyHealth"), player.getLocation(), player.getWorld(), main);
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lBartholomew&4&ki"))) {
            	player.closeInventory();
            	new BartholomewEntity(config.getInt("BartholomewHealth"), player.getLocation(), player.getWorld(), main);
            	
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lPiggy&4&ki"))) {
            	player.closeInventory();
            	new PiggyEntity(config.getInt("PiggyHealth"), player.getLocation(), player.getWorld(), main);
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lGort&4&ki"))) {
            	player.closeInventory();
            	new GortEntity(config.getInt("GortHealth"), player.getLocation(), player.getWorld(), main);
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lDr. Strange&4&ki"))) {
            	player.closeInventory();
            	new DrStrangeEntity(config.getInt("DrStrangeHealth"), player.getLocation(), player.getWorld(), main);
            }
            else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLACK + ".")) {
            	player.closeInventory();
            }
        }
    }

}
