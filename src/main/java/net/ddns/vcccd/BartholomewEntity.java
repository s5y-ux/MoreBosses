package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BartholomewEntity {
	
	private ItemStack[] bartholomewArmor = {
            new ItemStack(Material.CHAINMAIL_BOOTS),
            new ItemStack(Material.CHAINMAIL_LEGGINGS),
            new ItemStack(Material.CHAINMAIL_CHESTPLATE)
            };
	
	@SuppressWarnings("deprecation")
	public BartholomewEntity(int health, Location local, World world, Main main) {
		WitherSkeleton Bartholomew = (WitherSkeleton) world.spawnEntity(local, EntityType.WITHER_SKELETON);
		Bartholomew.getEquipment().setArmorContents(bartholomewArmor);
		
		//Setting the name
		Bartholomew.setCustomName(ChatColor.BLACK + "Bartholomew");
		Bartholomew.setCustomNameVisible(true);
		
		//Setting the effects
		Bartholomew.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999999, 2));
		Bartholomew.setMaxHealth(health);
		Bartholomew.setHealth(health);
		Bartholomew.setRemoveWhenFarAway(main.getConfig().getBoolean("BartholomewDeSpawn"));
	}

}
