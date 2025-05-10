package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AlbertRemoverItem implements CommandExecutor {
	
	private final Main main;
	
	public AlbertRemoverItem(Main main) {
		this.main = main;
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			ItemStack AlbertRemover = new ItemStack(Material.STICK);
			ItemMeta AlbertRemoverData = AlbertRemover.getItemMeta();
			AlbertRemoverData.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eAlbert Remover"));
			AlbertRemover.setItemMeta(AlbertRemoverData);
			
			Player player = (Player) sender;
			player.sendMessage(main.getPluginPrefix() + "You have recieved the albert remover stick.");
			player.getInventory().setItem(player.getInventory().firstEmpty(), AlbertRemover);
		}
		return true;
	}

}
