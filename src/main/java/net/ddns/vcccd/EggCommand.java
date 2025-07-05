package net.ddns.vcccd;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class EggCommand implements CommandExecutor {

    private final Main main;

    public EggCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory inventory = player.getInventory();
            int firstEmptySlot = inventory.firstEmpty();
            
            
            if (firstEmptySlot == -1) {
                player.sendMessage(main.getPluginPrefix() + "Your inventory is full. Please make space to receive the boss egg.");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(main.getPluginPrefix() + "Please specify a boss type to spawn.");
                return false;
            }

            switch (args[0].toLowerCase()) {
                case "oswaldo": {
                    ItemStack egg = new ItemStack(Material.EGG);
                    ItemMeta eggMeta = egg.getItemMeta();
                    eggMeta.setDisplayName(ChatColor.GREEN + "Oswaldo Egg");
                    egg.setItemMeta(eggMeta);
                    player.sendMessage(main.getPluginPrefix() + "You have received an Oswaldo egg!");
                    inventory.setItem(firstEmptySlot, egg);
                    break;
                }
                case "bigboy": {
                    ItemStack egg = new ItemStack(Material.EGG);
                    ItemMeta eggMeta = egg.getItemMeta();
                    eggMeta.setDisplayName(ChatColor.GREEN + "BigBoy Egg");
                    egg.setItemMeta(eggMeta);
                    player.sendMessage(main.getPluginPrefix() + "You have received a Big Boy egg!");
                    inventory.setItem(firstEmptySlot, egg);
                    break;
                }
                case "timmothy": {
                    ItemStack egg = new ItemStack(Material.EGG);
                    ItemMeta eggMeta = egg.getItemMeta();
                    eggMeta.setDisplayName(ChatColor.GREEN + "Timmothy Egg");
                    egg.setItemMeta(eggMeta);
                    player.sendMessage(main.getPluginPrefix() + "You have received a Timmothy egg!");
                    inventory.setItem(firstEmptySlot, egg);
                    break;
                }
                case "albert": {
                    ItemStack egg = new ItemStack(Material.EGG);
                    ItemMeta eggMeta = egg.getItemMeta();
                    eggMeta.setDisplayName(ChatColor.GREEN + "Albert Egg");
                    egg.setItemMeta(eggMeta);
                    player.sendMessage(main.getPluginPrefix() + "You have received an Albert egg!");
                    inventory.setItem(firstEmptySlot, egg);
                    break;
                }
                case "bartholomew": {
                    ItemStack egg = new ItemStack(Material.EGG);
                    ItemMeta eggMeta = egg.getItemMeta();
                    eggMeta.setDisplayName(ChatColor.GREEN + "Bartholomew Egg");
                    egg.setItemMeta(eggMeta);
                    player.sendMessage(main.getPluginPrefix() + "You have received a Bartholomew egg!");
                    inventory.setItem(firstEmptySlot, egg);
                    break;
                }
                case "drstrange": {
                    ItemStack egg = new ItemStack(Material.EGG);
                    ItemMeta eggMeta = egg.getItemMeta();
                    eggMeta.setDisplayName(ChatColor.GREEN + "Dr. Strange Egg");
                    egg.setItemMeta(eggMeta);
                    player.sendMessage(main.getPluginPrefix() + "You have received a Dr Strange egg!");
                    inventory.setItem(firstEmptySlot, egg);
                    break;
                }
                case "gort": {
                    ItemStack egg = new ItemStack(Material.EGG);
                    ItemMeta eggMeta = egg.getItemMeta();
                    eggMeta.setDisplayName(ChatColor.GREEN + "Gort Egg");
                    egg.setItemMeta(eggMeta);
                    player.sendMessage(main.getPluginPrefix() + "You have received a Gort egg!");
                    inventory.setItem(firstEmptySlot, egg);
                    break;
                }
                case "piggy": {
                    ItemStack egg = new ItemStack(Material.EGG);
                    ItemMeta eggMeta = egg.getItemMeta();
                    eggMeta.setDisplayName(ChatColor.GREEN + "Piggy Egg");
                    egg.setItemMeta(eggMeta);
                    player.sendMessage(main.getPluginPrefix() + "You have received a Piggy egg!");
                    inventory.setItem(firstEmptySlot, egg);
                    break;
                }
                default:
                    player.sendMessage(main.getPluginPrefix() + "Unknown boss type.");
                    break;
            }

            return true;
        } else {
            main.getConsole().sendMessage(main.getPluginPrefix() + "This command can only be used by players.");
            return false;
        }
    }
    
}
