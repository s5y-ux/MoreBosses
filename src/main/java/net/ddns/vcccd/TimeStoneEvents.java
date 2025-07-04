package net.ddns.vcccd;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TimeStoneEvents implements Listener {

    private final Main main;
    private int radius;
    private int duration;
    private int numParticles;

    public TimeStoneEvents(Main main) {
        this.main = main;
        this.radius = main.getConfig().getInt("radius");
        this.duration = main.getConfig().getInt("duration");
        this.numParticles = main.getConfig().getInt("numParticles");
    }

    // Handles general Time Stone interaction (Right-click on block or air)
    @EventHandler
    public void generalTimeStoneUse(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            PlayerInventory inventory = player.getInventory();
            Action eventAction = event.getAction();

            boolean isHoldingTimeStone = inventory.getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Time Stone");
            boolean cooldownComplete = player.getCooldown(Material.EMERALD) == 0;
            boolean isRightClick = eventAction == Action.RIGHT_CLICK_BLOCK || eventAction == Action.RIGHT_CLICK_AIR;

            if (cooldownComplete && isHoldingTimeStone && isRightClick) {
                slowTime(player);

                Block clickedBlock = event.getClickedBlock();
                try {
                    // Grow crops instantly
                    org.bukkit.block.data.Ageable blockData = (org.bukkit.block.data.Ageable) clickedBlock.getBlockData();
                    blockData.setAge(7);
                    clickedBlock.setBlockData(blockData);

                    // Visual particles
                    Location blockLocation = clickedBlock.getLocation();
                    for (int i = 0; i < 10; i++) {
                        player.getWorld().spawnParticle(
                            Particle.HAPPY_VILLAGER,
                            blockLocation.getX() + Math.random(),
                            blockLocation.getY() + Math.random(),
                            blockLocation.getZ() + Math.random(),
                            3
                        );
                    }

                    // Short cooldown for crop use
                    player.setCooldown(Material.EMERALD, 100);
                } catch (Exception e) {
                    // Ignored
                }
            }
        } catch (Exception e) {
            // Ignored
        }
    }

    // Handles Time Stone use when right-clicking an entity while sneaking
    @EventHandler
    public void entityInteract(PlayerInteractEntityEvent event) {
        try{
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        boolean isHoldingTimeStone = inventory.getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Time Stone");
        boolean cooldownComplete = player.getCooldown(Material.EMERALD) == 0;
        boolean isTargetAgeable = event.getRightClicked() instanceof Ageable;

        if (player.isSneaking() && isHoldingTimeStone && cooldownComplete && isTargetAgeable) {
            Ageable target = (Ageable) event.getRightClicked();

            // Toggle between baby and adult
            if (target.isAdult()) {
                target.setBaby();
            } else {
                target.setAdult();
            }

            // Visual particles
            Location loc = target.getLocation();
            for (int i = 0; i < 10; i++) {
                player.getWorld().spawnParticle(
                    Particle.HAPPY_VILLAGER,
                    loc.getX() + (Math.random() - 0.5),
                    loc.getY() + Math.random(),
                    loc.getZ() + (Math.random() - 0.5),
                    3
                );
            }

            // Short cooldown
            player.setCooldown(Material.EMERALD, 100);
        }
    } catch (Exception e) {
            // Ignore any errors
        }
    }

    // Core logic for slowing down time and applying effects to nearby entities
    private void slowTime(Player player) {
        double angle = (2 * Math.PI) / numParticles;
        Location playerLoc = player.getLocation();

        // Spawn circular particle ring around the player
        for (int i = 0; i < numParticles; i++) {
            double offsetX = radius * Math.cos(angle * i);
            double offsetZ = radius * Math.sin(angle * i);
            player.getWorld().spawnParticle(
                Particle.HAPPY_VILLAGER,
                playerLoc.getX() + offsetX,
                playerLoc.getY(),
                playerLoc.getZ() + offsetZ,
                3
            );
        }

        // Affect nearby entities
        Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(playerLoc, radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            try {
                if (entity instanceof LivingEntity && !entity.equals(player)) {
                    LivingEntity target = (LivingEntity) entity;
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, duration, 3));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration, 1));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, duration, 3));
                }
            } catch (Exception e) {
                // Ignore failed casts or errors
            }
        }

        // General cooldown for main time-slowing effect
        player.setCooldown(Material.EMERALD, 100);
    }
}
