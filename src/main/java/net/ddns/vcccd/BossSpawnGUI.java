package net.ddns.vcccd;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BossSpawnGUI implements CommandExecutor, Listener {

    private Inventory bossMenu = Bukkit.createInventory(null, 9, "Spawn Boss");
    
    @SuppressWarnings("deprecation")
	private void healthme(Mob mob, int health) {
    	mob.setMaxHealth(health);
    	mob.setHealth(health);
    }
    
    private void itemLore(ArrayList<String> Lore, ItemStack item) {
    	ItemMeta temp = item.getItemMeta();
    	temp.setLore(Lore);
    	item.setItemMeta(temp);
    }
    
    private ItemStack createHead(String name, Material headMaterial) {
        ItemStack returnHead = new ItemStack(headMaterial);
        ItemMeta returnHeadMeta = returnHead.getItemMeta();
        returnHeadMeta.setDisplayName(name);
        returnHead.setItemMeta(returnHeadMeta);
        return returnHead;
    }

    private ItemStack createEnchantedItem(Material armor, Enchantment enchant, int level) {
        ItemStack returnArmor = new ItemStack(armor);
        ItemMeta armorMeta = returnArmor.getItemMeta();
        armorMeta.addEnchant(enchant, level, true);
        returnArmor.setItemMeta(armorMeta);
        return returnArmor;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ItemStack albertHead, oswaldoHead, bigBoyHead, timmothyHead, bartholomewHead;
        albertHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lAlbert&4&ki"), Material.SLIME_BLOCK);
        oswaldoHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lOswaldo&4&ki"), Material.ZOMBIE_HEAD);
        bigBoyHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lBig Boy&4&ki"), Material.GOLD_BLOCK);
        timmothyHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lTimmothy&4&ki"), Material.SKELETON_SKULL);
        bartholomewHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lBartholomew&4&ki"), Material.WITHER_SKELETON_SKULL);

        // Define lore for each head
        ArrayList<String> albertLore = new ArrayList<>();
        albertLore.add(ChatColor.WHITE + "Never-ending slime that");
        albertLore.add(ChatColor.WHITE + "has to be removed with the Albert Remover...");

        ArrayList<String> oswaldoLore = new ArrayList<>();
        oswaldoLore.add(ChatColor.WHITE + "Fully netherite zombie that");
        oswaldoLore.add(ChatColor.WHITE + "summons baby zombie minions...");

        ArrayList<String> timmothyLore = new ArrayList<>();
        timmothyLore.add(ChatColor.WHITE + "Diamond skeleton that shoots");
        timmothyLore.add(ChatColor.WHITE + "exploding arrows...");

        ArrayList<String> bartholomewLore = new ArrayList<>();
        bartholomewLore.add(ChatColor.WHITE + "Wither skeleton that likes");
        bartholomewLore.add(ChatColor.WHITE + "to fight with effects...");

        ArrayList<String> bigBoyLore = new ArrayList<>();
        bigBoyLore.add(ChatColor.WHITE + "Big boy that likes");
        bigBoyLore.add(ChatColor.WHITE + "spawning bigger minions...");

        // Set lore for each head
        itemLore(albertLore, albertHead);
        itemLore(oswaldoLore, oswaldoHead);
        itemLore(bigBoyLore, bigBoyHead);
        itemLore(timmothyLore, timmothyHead);
        itemLore(bartholomewLore, bartholomewHead);

        ItemStack[] selection = {albertHead, oswaldoHead, bigBoyHead, timmothyHead, bartholomewHead};
        int accumulator = 0;
        for (ItemStack item : selection) {
            bossMenu.setItem(accumulator, item);
            accumulator += 2;
        }
        accumulator = 1;
        
        ItemStack blank = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta a = blank.getItemMeta();
        a.setDisplayName(".");
        blank.setItemMeta(a);
        
        for(int i = 0; i < 4; i++) {
        	bossMenu.setItem(accumulator, blank);
        	accumulator = accumulator + 2;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(bossMenu);
        }

        return true;
    }

	@EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Location playerLocation = player.getLocation();
        if (event.getCurrentItem() == null) {
            assert true;

        } else if (event.getClickedInventory().getSize() == 9) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lAlbert&4&ki"))) {
                player.closeInventory();
                Slime slime = (Slime) player.getWorld().spawnEntity(playerLocation, EntityType.SLIME);
                slime.setCustomName(ChatColor.YELLOW + "Albert");
                slime.setCustomNameVisible(true);
                slime.setAI(true);
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lOswaldo&4&ki"))) {
                player.closeInventory();
                Zombie oswaldo = (Zombie) player.getWorld().spawnEntity(playerLocation, EntityType.ZOMBIE);
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
                healthme(oswaldo, 400);
                
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lBig Boy&4&ki"))) {
                player.closeInventory();
                Giant bigBoy = (Giant) player.getWorld().spawnEntity(playerLocation, EntityType.GIANT);
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
                healthme(bigBoy, 400);
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lTimmothy&4&ki"))) {
                player.closeInventory();
                ItemStack bombBow = new ItemStack(Material.BOW);
                ItemMeta bowDes = bombBow.getItemMeta();
                bowDes.setDisplayName(ChatColor.RED + "BomBow");
                bombBow.setItemMeta(bowDes);

                Skeleton timmothy = (Skeleton) player.getWorld().spawnEntity(playerLocation, EntityType.SKELETON);
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
                healthme(timmothy, 400);
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lBartholomew&4&ki"))) {
            	player.closeInventory();
            	new bartholomew(player);
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(".")) {
            	player.closeInventory();
            }
        }
    }
}