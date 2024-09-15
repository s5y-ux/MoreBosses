package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI implements CommandExecutor, Listener {
	private Inventory test = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Spawn Boss");
	
	private ItemStack Head(String name, Material head) {
		ItemStack returnHead = new ItemStack(head);
		ItemMeta returnHeadMeta = returnHead.getItemMeta();
		returnHeadMeta.setDisplayName(name);
		returnHead.setItemMeta(returnHeadMeta);
		return(returnHead);
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ItemStack AlbertHead = Head(ChatColor.translateAlternateColorCodes('&', "&e&lAlbert"), Material.SLIME_BLOCK);
		ItemStack OswaldoHead = Head(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"), Material.ZOMBIE_HEAD);
		ItemStack BigBoyHead = Head(ChatColor.BOLD + "Big Boy", Material.GOLD_BLOCK);
		ItemStack TimmothyHead = Head(ChatColor.AQUA + "Timmothy", Material.SKELETON_SKULL);
		
		//test.addItem(AlbertHead);
		test.setItem(0, AlbertHead);
		test.setItem(2, OswaldoHead);
		test.setItem(4, TimmothyHead);
		test.setItem(6, BigBoyHead);
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			player.openInventory(test);
		}
		
		return(true);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Location PlayerLocation = player.getLocation();
		if(event.getCurrentItem() == null) {
			assert true;
		} else if(event.getClickedInventory().getSize() == 9) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&e&lAlbert"))) {
			player.closeInventory();
			Slime slime = (Slime) player.getWorld().spawnEntity(PlayerLocation, EntityType.SLIME);
	        slime.setCustomName(ChatColor.YELLOW + "Albert");
	        slime.setCustomNameVisible(true);
	        slime.setAI(true);
		} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
			player.closeInventory();
			Zombie slime = (Zombie) player.getWorld().spawnEntity(PlayerLocation, EntityType.ZOMBIE);
	        Zombie rider = (Zombie) player.getWorld().spawnEntity(PlayerLocation, EntityType.ZOMBIE);
	        EntityEquipment equipment = slime.getEquipment();
	        EntityEquipment equipmentTwo = rider.getEquipment();
	        
	        ItemStack Boots, Pants, Shirt, Hat, SwordOf;
	        
	        Boots = new ItemStack(Material.NETHERITE_BOOTS);
	        Pants = new ItemStack(Material.NETHERITE_LEGGINGS);
	        Shirt = new ItemStack(Material.NETHERITE_CHESTPLATE);
	        Hat = new ItemStack(Material.NETHERITE_HELMET);
	        SwordOf = new ItemStack(Material.NETHERITE_SWORD);
	        
	        equipment.setBoots(Boots);
	        equipment.setLeggings(Pants);
	        equipment.setChestplate(Shirt);
	        equipment.setHelmet(Hat);
	        equipment.setItemInMainHand(SwordOf);
	        
	        equipmentTwo.setBoots(Boots);
	        equipmentTwo.setLeggings(Pants);
	        equipmentTwo.setChestplate(Shirt);
	        equipmentTwo.setHelmet(Hat);
	        equipmentTwo.setItemInMainHand(SwordOf);
	        
	        rider.setBaby();
	        rider.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"));
	        rider.setCustomNameVisible(true);
	        slime.setCustomName("501ab6");
	        slime.setCustomNameVisible(false);
	        slime.setAI(true);
	        slime.setAdult();
	        slime.addPassenger(rider);
		}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BOLD + "Big Boy")) {
			player.closeInventory();
			Giant BigBoy = (Giant) player.getWorld().spawnEntity(player.getLocation(), EntityType.GIANT);
			
			EntityEquipment equipment = BigBoy.getEquipment();
			equipment.setBoots(new ItemStack(Material.GOLDEN_BOOTS));
			equipment.setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
			equipment.setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
			equipment.setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			equipment.setItemInMainHand(new ItemStack(Material.TRIDENT));
			
			BigBoy.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&lBig Boy"));
		} else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Timmothy")) {
			player.closeInventory();
			ItemStack BombBow = new ItemStack(Material.BOW);
			ItemMeta BowDes = BombBow.getItemMeta();
			BowDes.setDisplayName(ChatColor.RED + "BomBow");
			BombBow.setItemMeta(BowDes);
			
			SkeletonHorse Horse = (SkeletonHorse) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON_HORSE);
			Skeleton Timmothy = (Skeleton) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
			
			EntityEquipment equipment = Timmothy.getEquipment();
			equipment.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
			equipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
			equipment.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
			equipment.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			equipment.setItemInMainHand(BombBow);
			
			Horse.addPassenger(Timmothy);
			Timmothy.setCustomName(ChatColor.AQUA + "Timmothy");
		}
	}
	}
}
