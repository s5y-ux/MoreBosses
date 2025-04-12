package net.ddns.vcccd;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class Oswaldo implements Listener {
	public HashMap<Player, Double> playerList;
	
	
	@SuppressWarnings("deprecation")
	public Oswaldo(int health, Location local, World world) {
		Zombie oswaldo = (Zombie) world.spawnEntity(local, EntityType.ZOMBIE);
        EntityEquipment equipment = oswaldo.getEquipment();

        ItemStack[] zombieArmor = {
                new ItemStack(Material.GOLDEN_BOOTS),
                new ItemStack(Material.GOLDEN_LEGGINGS),
                new ItemStack(Material.GOLDEN_CHESTPLATE),
                new ItemStack(Material.GOLDEN_HELMET)
        };

        equipment.setArmorContents(zombieArmor);
        equipment.setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));

        oswaldo.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"));
        oswaldo.setCustomNameVisible(true);
        oswaldo.setAI(true);
        oswaldo.setBaby(false);
        oswaldo.setMaxHealth(health);
        oswaldo.setHealth(health);
	}
	
}
