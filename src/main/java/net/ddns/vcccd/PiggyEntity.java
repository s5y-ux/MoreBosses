package net.ddns.vcccd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PiggyEntity {
	
	private ItemStack PiggySword = new ItemStack(Material.GOLDEN_AXE);
	private List<String> PiggyLore = new ArrayList<String>();
	
	private ItemStack[] apperal = {
			new ItemStack(Material.DIAMOND_BOOTS),
            new ItemStack(Material.DIAMOND_LEGGINGS),
            new ItemStack(Material.DIAMOND_CHESTPLATE)
	};
	
	@SuppressWarnings("deprecation")
	public PiggyEntity(int health, Location location, World world, Main main) {
		PigZombie OurPiggy = (PigZombie) world.spawnEntity(location, EntityType.ZOMBIFIED_PIGLIN);
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
		OurPiggy.setRemoveWhenFarAway(main.getConfig().getBoolean("PiggyDeSpawn"));
		
	}

}
