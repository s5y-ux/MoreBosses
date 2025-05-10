package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Vindicator;
import org.bukkit.inventory.ItemStack;

public class GortEntity {
	
	private ItemStack[] armor = {
			new ItemStack(Material.IRON_BOOTS),
			new ItemStack(Material.IRON_CHESTPLATE),
			new ItemStack(Material.IRON_LEGGINGS)
	};
	
	@SuppressWarnings("deprecation")
	public GortEntity(int health, Location local, World world, Main main) {
		Vindicator VinNumber = (Vindicator) world.spawnEntity(local, EntityType.VINDICATOR);
		VinNumber.getEquipment().setArmorContents(armor);
		VinNumber.setCustomName(ChatColor.translateAlternateColorCodes('&', "&eGort The Serf"));
		VinNumber.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_HOE));
		VinNumber.setCustomNameVisible(true);
		VinNumber.setMaxHealth(health);
		VinNumber.setHealth(health);
		VinNumber.setRemoveWhenFarAway(main.getConfig().getBoolean("GortDeSpawn"));
	}

}
