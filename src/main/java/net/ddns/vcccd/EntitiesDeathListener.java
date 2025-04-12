package net.ddns.vcccd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntitiesDeathListener implements Listener{
	
	private final Main main;

	public EntitiesDeathListener(Main main) {
		this.main = main;
	}
	
	private ItemStack CustomItem(Material item, String name) {
		ItemStack ReturnItem = new ItemStack(item);
		ItemMeta ReturnItemData = ReturnItem.getItemMeta();
		ReturnItemData.setDisplayName(name);
		ReturnItem.setItemMeta(ReturnItemData);
		return(ReturnItem);
	}
	
	private void DropItemAt(LivingEntity entity, ItemStack item) {
		Location location = entity.getLocation();
		World world = entity.getWorld();
		world.dropItem(location, item);
	}
	
	@EventHandler
	public void onBossDeath(EntityDeathEvent event) {
	
		ItemStack PiggyAxe = new ItemStack(Material.GOLDEN_AXE);
		List<String> PiggyLore = new ArrayList<String>();
		ItemMeta PiggySwordMeta = PiggyAxe.getItemMeta();
		PiggySwordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lPiggy&4&lAxe"));
		PiggyLore.add(ChatColor.translateAlternateColorCodes('&', "&cThe power of the piggy"));
		PiggySwordMeta.setLore(PiggyLore);
		PiggyAxe.setItemMeta(PiggySwordMeta);
		
		ItemStack GortsHoe = new ItemStack(Material.IRON_HOE);
		ItemMeta a = GortsHoe.getItemMeta();
		a.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eGorf's Hoe"));
		List<String> b = new ArrayList<String>();
		b.add(ChatColor.translateAlternateColorCodes('&', "&6Gives you the power of gort"));
		a.setLore(b);
		GortsHoe.setItemMeta(a);
		
		
		if(event.getEntity().getCustomName() == null) {
			assert true;
		}
	else if(event.getEntity().getCustomName().equals(ChatColor.AQUA + "Timmothy")) {

			DropItemAt(event.getEntity(), CustomItem(Material.BOW, ChatColor.RED + "BomBow"));
			
			event.getEntity().getWorld().spawnParticle(Particle.LAVA, event.getEntity().getLocation(), 30);
			
		} else if(event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {

			DropItemAt(event.getEntity(), CustomItem(Material.GOLDEN_HELMET, ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo\'s Helmet")));
			
			event.getEntity().getWorld().spawnParticle(Particle.LAVA, event.getEntity().getLocation(), 30);
			
		} else if(event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"))) {

			DropItemAt(event.getEntity(), CustomItem(Material.TRIDENT, ChatColor.translateAlternateColorCodes('&', "&e&lBig Boy\'s Trident")));
			
			event.getEntity().getWorld().spawnParticle(Particle.LAVA, event.getEntity().getLocation(), 30);
			
		} else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lPiGgY"))) {

			DropItemAt(event.getEntity(), PiggyAxe);
			
			event.getEntity().getWorld().spawnParticle(Particle.LAVA, event.getEntity().getLocation(), 30);
			
		} else if (event.getEntity().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&eGort The Serf"))) {

			DropItemAt(event.getEntity(), GortsHoe);
			
			event.getEntity().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, event.getEntity().getLocation(), 30);
	}

}
}
