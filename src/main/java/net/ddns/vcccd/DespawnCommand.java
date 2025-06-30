package net.ddns.vcccd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DespawnCommand implements CommandExecutor {
    private final Main main;
    
    public DespawnCommand(Main main){
        this.main = main;
    }

    @Override
    public boolean  onCommand(CommandSender sender, Command command, String label, String[] args){
        if(sender instanceof Player){
            Player player = (Player) sender;
            for(Entity entity: player.getNearbyEntities(500, 500, 500)){
                if(!(entity instanceof Player)) {
                    entity.remove();
                }
            }
            player.sendMessage(main.getPluginPrefix() + "All nearby entities have been despawned.");
            
        }
        return true;
    }
}
