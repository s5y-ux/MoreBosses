package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class OswaldoEntity {
	
	@SuppressWarnings("deprecation")
	public OswaldoEntity(int health, Location local, World world, Main main) {
		Zombie oswaldo = (Zombie) world.spawnEntity(local, EntityType.ZOMBIE);
        EntityEquipment equipment = oswaldo.getEquipment();

        ItemStack[] zombieArmor = {
                new ItemStack(Material.NETHERITE_BOOTS),
                new ItemStack(Material.NETHERITE_LEGGINGS),
                new ItemStack(Material.NETHERITE_CHESTPLATE),
                new ItemStack(Material.NETHERITE_HELMET)
        };

        equipment.setArmorContents(zombieArmor);
        equipment.setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));

        oswaldo.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"));
        oswaldo.setCustomNameVisible(true);
        oswaldo.setAI(true);
        
        oswaldo.setAdult();
        oswaldo.setMaxHealth(health);
        oswaldo.setHealth(health);
        oswaldo.setRemoveWhenFarAway(main.getConfig().getBoolean("OswaldoDeSpawn"));
	}

}
