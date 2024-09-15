package net.ddns.vcccd;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class AdminSword implements CommandExecutor, Listener{
	
	@EventHandler
	public void useAdminSword(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if(player.getInventory().getItemInMainHand().getItemMeta() == null) {
				assert true;
			} else if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Admin Sword")) {
				if(event.getEntity() instanceof Mob) {
					Mob mob = (Mob) event.getEntity();
					mob.setHealth(0);
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ItemStack AdminSword = new ItemStack(Material.WOODEN_SWORD);
		ItemMeta AdminSwordMeta = AdminSword.getItemMeta();
		AdminSwordMeta.setDisplayName(ChatColor.RED + "Admin Sword");
		AdminSword.setItemMeta(AdminSwordMeta);
		if(sender instanceof Player) {
			Player player = (Player) sender;
			player.getInventory().setItemInMainHand(AdminSword);
			player.sendMessage(ChatColor.GREEN + "You have recieved the Admin Sword...");
		}
		return(true);
	}
	

}
