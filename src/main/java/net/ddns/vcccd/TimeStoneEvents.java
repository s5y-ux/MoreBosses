package net.ddns.vcccd;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Objects;

public class TimeStoneEvents implements Listener {
    @EventHandler
    public void worldInteract(PlayerInteractEvent event) {
        // Player triggering event
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        // Check if player is holding the Time Stone in either hand and if the cooldown is at zero
        if((Objects.requireNonNull(inventory.getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Time Stone")
                || Objects.requireNonNull(inventory.getItemInOffHand().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Time Stone"))
                && player.getCooldown(Material.EMERALD) == 0) {
            // Check if the player is right-clicking (and NOT sneaking)
            if(!player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
                // Sort of hyperparameters for the effect
                int radius = 10;
                int duration = 200;
                int numParticles = 200;
                // Angle to space particles around circle
                double angle = (2 * Math.PI) / numParticles;

                // Get the nearby entities within given radius of the player
                Collection<Entity> entities = player.getWorld().getNearbyEntities(player.getLocation(), radius, radius, radius);

                // Get the player location for circle spawning
                Location loc = player.getLocation();

                for (int i = 0; i < numParticles; i++) {
                    // Calculate the x and z offset
                    double x = radius * Math.cos(angle * i);
                    double z = radius * Math.sin(angle * i);

                    // Spawn a ring of particles with the radius of the time stone effect
                    player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, loc.getX() + x, loc.getY(), loc.getZ() + z, 3);
                }

                for (Entity entity : entities) {
                    try {
                        LivingEntity livingEntity = (LivingEntity) entity;

                        // Add the potion effect to the entities in range
                        if (!livingEntity.equals(player)) {
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, duration, 3));
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration, 1));
                            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, duration, 3));
                        }
                    }catch (Exception e) {
                        assert true;
                    }
                }

                // Start the cooldown timer to prevent spamming of the ability
                player.setCooldown(Material.EMERALD, 100);
            }else if(player.isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // This is for crops
                Block block = event.getClickedBlock();

                try {
                    // Set the crop to fully grown
                    org.bukkit.block.data.Ageable blockData = (org.bukkit.block.data.Ageable) block.getBlockData();
                    blockData.setAge(7);
                    block.setBlockData(blockData);

                    // Spawn effect particles
                    Location loc = block.getLocation();
                    for(int i = 0; i < 10; i++) {
                        player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, loc.getX() + Math.random(), loc.getY() + Math.random(), loc.getZ() + Math.random(), 3);
                    }

                    // Shorter cooldown for this effect
                    player.setCooldown(Material.EMERALD, 10);
                }catch (Exception e) {
                    assert true;
                }
            }
        }
    }

    @EventHandler
    public void entityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        // Prevent double event trigger
        if(event.getHand() != EquipmentSlot.HAND) return;

        // If player is sneaking and right-clicking entity...
        if(player.isSneaking() && (Objects.requireNonNull(inventory.getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Time Stone")
                || Objects.requireNonNull(inventory.getItemInOffHand().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Time Stone"))
                && player.getCooldown(Material.EMERALD) == 0) {
            if(event.getRightClicked() instanceof Ageable) {
                Ageable ageable = (Ageable) event.getRightClicked();

                // Toggle the age between baby and adult
                if(ageable.isAdult()) {
                    ageable.setBaby();
                }else {
                    ageable.setAdult();
                }

                Location loc = ageable.getLocation();

                // Spawn effect particles
                for(int i = 0; i < 10; i++) {
                    player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, loc.getX() + (Math.random() - 0.5), loc.getY() + Math.random(), loc.getZ() + (Math.random() - 0.5), 3);
                }

                // Shorter cooldown
                player.setCooldown(Material.EMERALD, 10);
            }
        }
    }
}
