package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class BigBoy {
	
	@SuppressWarnings("deprecation")
	public BigBoy(int health, Location local, World world) {
		Giant bigBoy = (Giant) world.spawnEntity(local, EntityType.GIANT);
		EntityEquipment equipment = bigBoy.getEquipment();
		ItemStack[] zombieArmor = {
                new ItemStack(Material.GOLDEN_BOOTS),
                new ItemStack(Material.GOLDEN_LEGGINGS),
                new ItemStack(Material.GOLDEN_CHESTPLATE),
                new ItemStack(Material.GOLDEN_HELMET)
        };
        equipment.setArmorContents(zombieArmor);
        equipment.setItemInMainHand(new ItemStack(Material.TRIDENT));

        bigBoy.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"));
        bigBoy.setAI(true);
        bigBoy.setMaxHealth(health);
        bigBoy.setHealth(health);
	}

}
