package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AlbertStick implements CommandExecutor{
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			ItemStack AlbertRemover = new ItemStack(Material.STICK);
			ItemMeta AlbertRemoverData = AlbertRemover.getItemMeta();
			AlbertRemoverData.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eAlbert Remover"));
			AlbertRemover.setItemMeta(AlbertRemoverData);
			
			Player player = (Player) sender;
			player.getInventory().setItemInMainHand(AlbertRemover);
		}
		return(true);
	}

}
