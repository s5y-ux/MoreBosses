package net.ddns.vcccd;

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

    private Inventory bossMenu = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Spawn Boss");

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
        ItemStack albertHead, oswaldoHead, bigBoyHead, timmothyHead;
        albertHead = createHead(ChatColor.translateAlternateColorCodes('&', "&e&lAlbert"), Material.SLIME_BLOCK);
        oswaldoHead = createHead(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"), Material.ZOMBIE_HEAD);
        bigBoyHead = createHead(ChatColor.BOLD + "Big Boy", Material.GOLD_BLOCK);
        timmothyHead = createHead(ChatColor.AQUA + "Timmothy", Material.SKELETON_SKULL);

        ItemStack[] selection = {albertHead, oswaldoHead, bigBoyHead, timmothyHead};
        int accumulator = 0;
        for (ItemStack item : selection) {
            bossMenu.setItem(accumulator, item);
            accumulator += 2;
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
            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&e&lAlbert"))) {
                player.closeInventory();
                Slime slime = (Slime) player.getWorld().spawnEntity(playerLocation, EntityType.SLIME);
                slime.setCustomName(ChatColor.YELLOW + "Albert");
                slime.setCustomNameVisible(true);
                slime.setAI(true);
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lOswaldo"))) {
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
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BOLD + "Big Boy")) {
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
                
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Timmothy")) {
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
            }
        }
    }
}