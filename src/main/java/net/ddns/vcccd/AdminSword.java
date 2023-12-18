package net.ddns.vcccd;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminSword implements CommandExecutor, Listener{
	ItemStack AdminSword = new ItemStack(Material.WOODEN_SWORD);
	ItemMeta AdminSwordMeta = AdminSword.getItemMeta();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
		}
		return(true);
	}
	

}
