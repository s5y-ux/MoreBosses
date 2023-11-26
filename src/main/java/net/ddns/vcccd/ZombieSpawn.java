package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class ZombieSpawn implements CommandExecutor{
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Location PlayerLocation = player.getLocation();
        Zombie slime = (Zombie) player.getWorld().spawnEntity(PlayerLocation, EntityType.ZOMBIE);
        Zombie rider = (Zombie) player.getWorld().spawnEntity(PlayerLocation, EntityType.ZOMBIE);
        EntityEquipment equipment = slime.getEquipment();
        EntityEquipment equipmentTwo = rider.getEquipment();
        
        ItemStack Boots, Pants, Shirt, Hat, SwordOf;
        
        Boots = new ItemStack(Material.NETHERITE_BOOTS);
        Pants = new ItemStack(Material.NETHERITE_LEGGINGS);
        Shirt = new ItemStack(Material.NETHERITE_CHESTPLATE);
        Hat = new ItemStack(Material.NETHERITE_HELMET);
        SwordOf = new ItemStack(Material.NETHERITE_SWORD);
        
        equipment.setBoots(Boots);
        equipment.setLeggings(Pants);
        equipment.setChestplate(Shirt);
        equipment.setHelmet(Hat);
        equipment.setItemInMainHand(SwordOf);
        
        equipmentTwo.setBoots(Boots);
        equipmentTwo.setLeggings(Pants);
        equipmentTwo.setChestplate(Shirt);
        equipmentTwo.setHelmet(Hat);
        equipmentTwo.setItemInMainHand(SwordOf);
        
        rider.setBaby();
        rider.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"));
        rider.setCustomNameVisible(true);
        slime.setCustomName("501ab6");
        slime.setCustomNameVisible(false);
        slime.setAI(true);
        slime.setAdult();
        slime.addPassenger(rider);
                
		return false;
    }
}
