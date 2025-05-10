package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminSwordItem implements CommandExecutor {
	
	private final Main main;
	
	public AdminSwordItem(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ItemStack AdminSword = new ItemStack(Material.WOODEN_SWORD);
		ItemMeta AdminSwordMeta = AdminSword.getItemMeta();
		AdminSwordMeta.setDisplayName(ChatColor.RED + "Admin Sword");
		AdminSword.setItemMeta(AdminSwordMeta);
		if(sender instanceof Player) {
			Player player = (Player) sender;
			player.getInventory().setItem(player.getInventory().firstEmpty(), AdminSword);
			player.sendMessage(main.getPluginPrefix() + "You have recieved the Admin Sword...");
		}
		return(true);
	}

}
