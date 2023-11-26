package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TimmothySpawn implements CommandExecutor{
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			ItemStack BombBow = new ItemStack(Material.BOW);
			ItemMeta BowDes = BombBow.getItemMeta();
			BowDes.setDisplayName(ChatColor.RED + "BomBow");
			BombBow.setItemMeta(BowDes);
			
			Player player = (Player) sender;
			SkeletonHorse Horse = (SkeletonHorse) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON_HORSE);
			Skeleton Timmothy = (Skeleton) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
			
			EntityEquipment equipment = Timmothy.getEquipment();
			equipment.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			equipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			equipment.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			equipment.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			equipment.setItemInMainHand(BombBow);
			
			Horse.addPassenger(Timmothy);
			Timmothy.setCustomName(ChatColor.BLUE + "Timmothy");
			
		}
		return(true);
	}
}
