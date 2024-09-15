package net.ddns.vcccd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Piggy {
	private ItemStack[] apperal = {
			new ItemStack(Material.NETHERITE_BOOTS),
            new ItemStack(Material.NETHERITE_LEGGINGS),
            new ItemStack(Material.NETHERITE_CHESTPLATE)
	};
	
	private ItemStack PiggySword = new ItemStack(Material.GOLDEN_AXE);
	private Piglin OurPiggy;
	private List<String> PiggyLore = new ArrayList<String>();
	
	public Piggy(Player player, int health) {
		Location location = player.getLocation();
		World world = player.getWorld();
		OurPiggy = (Piglin) world.spawnEntity(location, EntityType.PIGLIN);
		OurPiggy.setMaxHealth(health);
		OurPiggy.setHealth(health);
		OurPiggy.getEquipment().setArmorContents(apperal);
		ItemMeta PiggySwordMeta = PiggySword.getItemMeta();
		PiggySwordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lPiggy&4&lAxe"));
		this.PiggyLore.add(ChatColor.translateAlternateColorCodes('&', "&cThe power of the piggy"));
		PiggySwordMeta.setLore(PiggyLore);
		this.PiggySword.setItemMeta(PiggySwordMeta);
		OurPiggy.getEquipment().setItemInMainHand(PiggySword);
		OurPiggy.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lPiGgY"));
		OurPiggy.setCustomNameVisible(true);
		OurPiggy.setAdult();
		OurPiggy.setImmuneToZombification(true);
		
	}
	
	public Piggy(int health, Location location, World world) {
		OurPiggy = (Piglin) world.spawnEntity(location, EntityType.PIGLIN);
		OurPiggy.setMaxHealth(health);
		OurPiggy.setHealth(health);
		OurPiggy.getEquipment().setArmorContents(apperal);
		ItemMeta PiggySwordMeta = PiggySword.getItemMeta();
		PiggySwordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lPiggy&4&lAxe"));
		this.PiggyLore.add(ChatColor.translateAlternateColorCodes('&', "&cThe power of the piggy"));
		PiggySwordMeta.setLore(PiggyLore);
		this.PiggySword.setItemMeta(PiggySwordMeta);
		OurPiggy.getEquipment().setItemInMainHand(PiggySword);
		OurPiggy.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lPiGgY"));
		OurPiggy.setCustomNameVisible(true);
		OurPiggy.setAdult();
		OurPiggy.setImmuneToZombification(true);
		
	}
	
	public Piglin getPiggy() {
		return(this.OurPiggy);
	}

}
