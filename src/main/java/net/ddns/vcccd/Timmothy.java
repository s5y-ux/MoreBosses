package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Timmothy {
	
	private ItemStack createEnchantedItem(Material armor, Enchantment enchant, int level) {
        ItemStack returnArmor = new ItemStack(armor);
        ItemMeta armorMeta = returnArmor.getItemMeta();
        armorMeta.addEnchant(enchant, level, true);
        returnArmor.setItemMeta(armorMeta);
        return returnArmor;
    }
	
	public Timmothy(int health, Location local, World world) {
		ItemStack bombBow = new ItemStack(Material.BOW);
        ItemMeta bowDes = bombBow.getItemMeta();
        bowDes.setDisplayName(ChatColor.RED + "BomBow");
        bombBow.setItemMeta(bowDes);

        Skeleton timmothy = (Skeleton) world.spawnEntity(local, EntityType.SKELETON);
        EntityEquipment equipment = timmothy.getEquipment();
        
        ItemStack[] skeletonArmor = {
        		createEnchantedItem(Material.DIAMOND_BOOTS, Enchantment.PROTECTION_EXPLOSIONS, 999),
                createEnchantedItem(Material.DIAMOND_LEGGINGS, Enchantment.PROTECTION_EXPLOSIONS, 999),
                createEnchantedItem(Material.DIAMOND_CHESTPLATE, Enchantment.PROTECTION_EXPLOSIONS, 999),
                createEnchantedItem(Material.DIAMOND_HELMET, Enchantment.PROTECTION_EXPLOSIONS, 999)
        };
        
        equipment.setArmorContents(skeletonArmor);
        equipment.setItemInMainHand(bombBow);
        timmothy.setCustomName(ChatColor.AQUA + "Timmothy");
	}
}
