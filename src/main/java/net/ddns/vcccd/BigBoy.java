package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class BigBoy implements CommandExecutor{
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Giant BigBoy = (Giant) player.getWorld().spawnEntity(player.getLocation(), EntityType.GIANT);
			
			EntityEquipment equipment = BigBoy.getEquipment();
			equipment.setBoots(new ItemStack(Material.GOLDEN_BOOTS));
			equipment.setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
			equipment.setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
			equipment.setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			equipment.setItemInMainHand(new ItemStack(Material.TRIDENT));
			
			BigBoy.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"));
			
		}
		
		return(true);
	}

}
