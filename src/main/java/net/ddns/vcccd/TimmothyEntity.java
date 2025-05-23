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

public class TimmothyEntity {
	
	private ItemStack createEnchantedItem(Material armor, Enchantment enchant, int level) {
        ItemStack returnArmor = new ItemStack(armor);
        ItemMeta armorMeta = returnArmor.getItemMeta();
        armorMeta.addEnchant(enchant, level, true);
        returnArmor.setItemMeta(armorMeta);
        return returnArmor;
    }
	
	public TimmothyEntity(int health, Location local, World world, Main main) {
		ItemStack bombBow = new ItemStack(Material.BOW);
        ItemMeta bombBowMeta = bombBow.getItemMeta();
        bombBowMeta.setDisplayName(ChatColor.RED + "BomBow");
        bombBow.setItemMeta(bombBowMeta);

        Skeleton timmothy = (Skeleton) world.spawnEntity(local, EntityType.SKELETON);
        EntityEquipment equipment = timmothy.getEquipment();
        
        // TODO: Create an event that cancels the event on the event of an explosion.
        
        ItemStack[] skeletonArmor = {
        		createEnchantedItem(Material.DIAMOND_BOOTS, Enchantment.BLAST_PROTECTION, 5),
                createEnchantedItem(Material.DIAMOND_LEGGINGS, Enchantment.BLAST_PROTECTION, 5),
                createEnchantedItem(Material.DIAMOND_CHESTPLATE, Enchantment.BLAST_PROTECTION, 5),
                createEnchantedItem(Material.DIAMOND_HELMET, Enchantment.BLAST_PROTECTION, 5)
        };
        
        equipment.setArmorContents(skeletonArmor);
        equipment.setItemInMainHand(bombBow);
        timmothy.setCustomName(ChatColor.AQUA + "Timmothy");
        timmothy.setRemoveWhenFarAway(main.getConfig().getBoolean("TimmothyDeSpawn"));
	}

}
