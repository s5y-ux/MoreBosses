package net.ddns.vcccd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class BigBoyTridentUse implements Listener {

    private final FileConfiguration config;

    public BigBoyTridentUse() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("MoreBosses");
        if (plugin == null) {
            throw new IllegalStateException("MoreBosses plugin not found!");
        }
        this.config = plugin.getConfig();
    }

    // Particle helper
    private void particle(Location playerLocation, Particle particle,
                          Vector playerFacingDirection, World playerWorld, int i, double[] offset) {
        double[] dir = {playerFacingDirection.getX(),
                playerFacingDirection.getY(), playerFacingDirection.getZ()};

        playerWorld.spawnParticle(particle,
                playerLocation.getX() + offset[0] + i * dir[0],
                playerLocation.getY() + offset[1] + i * dir[1],
                playerLocation.getZ() + offset[2] + i * dir[2], 3);
    }

    // Particle stream
    private void particles(Player player) {
        World playerWorld = player.getWorld();
        Location playerLocation = player.getLocation();
        Vector playerFacingDirection = playerLocation.getDirection();
        Particle lazer = Particle.HAPPY_VILLAGER;

        for (int i = 1; i < 100; i++) {
            double[][] vectorTuples = {
                    {0, 1.35, 0}, {0, 1.5, 0}, {0, 1.65, 0},
                    {-0.15, 1.5, 0}, {0, 1.5, -0.15}
            };
            for (double[] tuple : vectorTuples) {
                particle(playerLocation, lazer, playerFacingDirection, playerWorld, i, tuple);
            }
        }
    }

    private LivingEntity getTarget(Player player, int BlockArea) {
		
		//Value to be returned from the Function
		LivingEntity returnVal = null;

		//Iterates over the array of entities retrieved form the area
		for(Entity IterativeEntity: player.getNearbyEntities(BlockArea, BlockArea, BlockArea)) {
			
			//We want to make sure the target is a Mob
			if(IterativeEntity instanceof LivingEntity) {
				
			//If so, we want to get the directional vector of the eye location
			Vector PlayerDirectionalVector = player.getEyeLocation().getDirection();
			
			//And we also want to get the Local Targets location
			Location TargetLocal = IterativeEntity.getLocation();
			
			//TargetLocal is located at the base of the entity, we want the body
			//I know all mobs aren't two blocks tall, will change this later
			TargetLocal.setY(TargetLocal.getY()+1);
			
			//We then want to subtract the new target location from the eye location
			Vector TargetPositionalVector = TargetLocal.toVector().subtract(player.getEyeLocation().toVector());
			
			//And we take angle Theta of the direction the player is facing and the position of the target
			double Theta = PlayerDirectionalVector.angle(TargetPositionalVector);
			
			//If that Theta value is within a certain threshold, we can assume targeting
			if(Theta < 0.130) {
				
				//We then cast the entity to type mob (Note instanceof keyword...)
				LivingEntity target = (LivingEntity) IterativeEntity;
				
				//We set the return value to the first enemy in that threshold
				returnVal = target;
				
				//And break the loop...
				break;
			}
			}
			
		}
		
		//We then return the value stored in the return value
		return(returnVal);
		
		/*
		 * Please note that if there is no targeted enemy, 
		 * the function will return null. Bukkit does not
		 * like this very much. So any implementation must
		 * be put in a try, except structure.
		 */
	}

    // When player right-clicks to throw the trident
    @EventHandler
    public void onBigBoyTridentThrow(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;

        String name = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        if (!name.equals(ChatColor.translateAlternateColorCodes('&', "&e&lBig Boy's Trident"))) return;

        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            // Play throw sound & particles
            if(event.getPlayer().getLevel() >= 3) {
					event.getPlayer().setLevel(event.getPlayer().getLevel() - 3);
					event.getPlayer().playSound(event.getPlayer(), Sound.BLOCK_AMETHYST_BLOCK_FALL, 500, 0);
					LivingEntity mob = getTarget(event.getPlayer(), 20);
					if(mob == null) {
						particles(event.getPlayer());
						assert true;
					} else {
					mob.getWorld().createExplosion(mob.getLocation(), 5);
					particles(event.getPlayer());
					}
				} else {
						event.getPlayer().sendMessage(ChatColor.RED + "You don't have enough EXP to use this weapon...");
					}
				}
        }
    

    // When the trident actually lands
    @EventHandler
    public void onBigBoyTridentHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Trident trident)) return;
        if (!(trident.getShooter() instanceof Player player)) return;

        // Check the actual thrown trident item
        ItemStack tridentItem = trident.getItem();
        if (tridentItem == null || !tridentItem.hasItemMeta()) return;

        String name = tridentItem.getItemMeta().getDisplayName();
        if (!name.equals(ChatColor.translateAlternateColorCodes('&', "&e&lBig Boy's Trident"))) return;

        int expCost = config.getInt("bigboytrident.exp-cost", 3);
        float explosionPower = (float) config.getDouble("bigboytrident.explosion-power", 5);
        boolean griefs = config.getBoolean("bigboytrident.griefs", true);

        // Only deduct EXP in survival/adventure
        if (player.getGameMode() != GameMode.CREATIVE) {
            if (player.getLevel() < expCost) {
                player.sendMessage(ChatColor.RED + "You don't have enough EXP to use this weapon...");
                return;
            }
            player.setLevel(player.getLevel() - expCost);
        }

        // Boom at exact impact location
        Location hitLocation = trident.getLocation();
        hitLocation.getWorld().createExplosion(hitLocation, explosionPower, griefs);
    }
}
