package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class TridentListener implements Listener{
	
	private void particles(Player player) {
		World PlayerWorld = player.getWorld();
		
		//We get the location and direction of the player from various methods
		Location PlayerLocation = player.getLocation();
		Vector PlayerFacingDirection = PlayerLocation.getDirection();	
		
		//We also want to spawn a stream of particles in the face of the player
		
		Particle Lazer = Particle.VILLAGER_HAPPY;
		
		for (int i = 1; i < 400; i++) {
			
			//Ill come back and optimize this again later...
			double[][] VectorTuples = {{0, 1.35, 0}, {0, 1.5, 0}, {0, 1.65, 0},
					{-0.15, 1.5, 0}, {0, 1.5, -0.15}};
			
			for(int j = 0; j < VectorTuples.length; j++) {
				particle(PlayerLocation, Lazer, 
						PlayerFacingDirection, PlayerWorld, i, VectorTuples[j]);
			}
			
		}
	}
	
	private Mob getTarget(Player player, int BlockArea) {
		
		//Value to be returned from the Function
		Mob returnVal = null;
		
		//Iterates over the array of entities retrieved form the area
		for(Entity IterativeEntity: player.getNearbyEntities(BlockArea, BlockArea, BlockArea)) {
			
			//We want to make sure the target is a Mob
			if(IterativeEntity instanceof Mob) {
				
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
				Mob target = (Mob) IterativeEntity;
				
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

	
	@EventHandler
	public void onLeftClickWithTrident(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
		if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) {
			assert true;
		}
			else if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&e&lBig Boy\'s Trident"))) {
				if(event.getPlayer().getLevel() >= 3) {
				event.getPlayer().setLevel(event.getPlayer().getLevel() - 3);
				Mob mob = getTarget(event.getPlayer(), 20);
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
	}
	
	//Function for summoning Particles in player direction
		private void particle(Location PlayerLocation, Particle particle, 
				Vector PlayerFacingDirection, World PlayerWorld, int i, double[] offset) {
			
			//Inits doubles for the facing Direction
			double[] Direction = {PlayerFacingDirection.getX(), 
					PlayerFacingDirection.getY(), PlayerFacingDirection.getZ()};
			

			//Spawns the particles with the axis offsets 
			PlayerWorld.spawnParticle(particle, PlayerLocation.getX() + offset[0] + i*Direction[0], 
					PlayerLocation.getY()+ offset[1] +  i*Direction[1], PlayerLocation.getZ() + offset[2] + i*Direction[2], 3);
		}

}
