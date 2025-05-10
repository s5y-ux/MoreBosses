package net.ddns.vcccd;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BossSpawnGUI implements CommandExecutor {

    private Inventory bossMenu = Bukkit.createInventory(null, 18, "Spawn Boss");
    
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
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ItemStack albertHead, oswaldoHead, bigBoyHead, timmothyHead, bartholomewHead, piggyHead, vinHead, strangeHead;
        albertHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lAlbert&4&ki"), Material.SLIME_BLOCK);
        oswaldoHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lOswaldo&4&ki"), Material.ZOMBIE_HEAD);
        bigBoyHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lBig Boy&4&ki"), Material.GOLD_BLOCK);
        timmothyHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lTimmothy&4&ki"), Material.SKELETON_SKULL);
        bartholomewHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lBartholomew&4&ki"), Material.WITHER_SKELETON_SKULL);
        piggyHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lPiggy&4&ki"), Material.PIG_SPAWN_EGG);
        vinHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lGort&4&ki"), Material.VINDICATOR_SPAWN_EGG);
        strangeHead = createHead(ChatColor.translateAlternateColorCodes('&', "&4&ki&c&lDr. Strange&4&ki"), Material.ENDER_PEARL);
        
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
        
        ArrayList<String> piggyLore = new ArrayList<>();
        piggyLore.add(ChatColor.WHITE + "Big bad piggy");
        piggyLore.add(ChatColor.WHITE + "Will throw you around...");
        
        ArrayList<String> vinLore = new ArrayList<>();
        vinLore.add(ChatColor.WHITE + "Rude serf who no longer");
        vinLore.add(ChatColor.WHITE + "Puts up with his lord...");
        
        ArrayList<String> strangeLore = new ArrayList<>();
        strangeLore.add(ChatColor.WHITE + "An Enderman");
        strangeLore.add(ChatColor.WHITE + "Who mastered space travel...");

        // Set lore for each head
        itemLore(albertLore, albertHead);
        itemLore(oswaldoLore, oswaldoHead);
        itemLore(bigBoyLore, bigBoyHead);
        itemLore(timmothyLore, timmothyHead);
        itemLore(bartholomewLore, bartholomewHead);
        itemLore(piggyLore, piggyHead);
        itemLore(vinLore, vinHead);
        itemLore(strangeLore, strangeHead);

        ItemStack[] selection = {albertHead, oswaldoHead, bigBoyHead, timmothyHead, bartholomewHead};
        int accumulator = 0;
        for (ItemStack item : selection) {
            bossMenu.setItem(accumulator, item);
            accumulator += 2;
        }
        accumulator = 1;
        
        ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta a = blank.getItemMeta();
        a.setDisplayName(ChatColor.BLACK + ".");
        blank.setItemMeta(a);
        
        for(int i = 0; i < 4; i++) {
        	bossMenu.setItem(accumulator, blank);
        	accumulator = accumulator + 2;
        }
        
        
        // TODO: Make into loop
        bossMenu.setItem(10, piggyHead);
        bossMenu.setItem(13, vinHead);
        bossMenu.setItem(16, strangeHead);
        bossMenu.setItem(9, blank);
        bossMenu.setItem(11, blank);
        bossMenu.setItem(12, blank);
        bossMenu.setItem(14, blank);
        bossMenu.setItem(15, blank);
        bossMenu.setItem(17, blank);
        
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(bossMenu);
        }

        return true;
    }

}