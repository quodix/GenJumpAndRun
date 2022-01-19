package de.quodix.jumpandrun.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.quodix.jumpandrun.helpers.Particle;
import de.quodix.jumpandrun.helpers.RandomLocation;
import de.quodix.jumpandrun.helpers.SoundCloud;
import de.quodix.jumpandrun.helpers.Titles;
import de.quodix.jumpandrun.main.GenJumpAndRun;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class JumpAndRun {
	private static final Map<Player, JumpAndRun> INSTANCES = new HashMap<>();
	
	public static JumpAndRun getInstance(final Player player) {
		return INSTANCES.get(player);
	}
	
	public static JumpAndRun getCheckedInstance(final Player player) {
		if(INSTANCES.containsKey(player)) {
			return INSTANCES.get(player);
		}
		return new JumpAndRun(player);
	}
	
	static {
		GenJumpAndRun.getInstance().getServer().getScheduler().runTaskTimer(GenJumpAndRun.getInstance(), new Runnable() {
			@Override
			public void run() {
				synchronized (INSTANCES) {
					INSTANCES.keySet().forEach(player ->{
						final JumpAndRun jumpAndRun = INSTANCES.get(player);
						if(jumpAndRun.isActive()) {
							jumpAndRun.spawnParticle();
							jumpAndRun.sendInfoText();
						}
					});
				}
			}
		}, 0, 5);
	}
	
	/*
	 * 
	 */
	
	private Player player;
	private Location lastBlockLocation, nowBlockLocation;
	private Integer jumps = 0;
	private boolean active = false, finished = false;
	
	private final List<Location> finishBlockLocations = new ArrayList<>();
	
	public JumpAndRun(final Player player) {
		INSTANCES.put(player, this);
		this.player = player;
	}
	
	public void spawnParticle() {
		if(nowBlockLocation == null)
			return;
		//Particle.spawnParticleCircle(player, nowBlockLocation.getBlock().getLocation().add(0.5, 1.1, 0.5), EnumParticle.REDSTONE, 0.8F, 1);
		Particle.spawnParticleAlongLine(player, lastBlockLocation == null ? player.getLocation() : getMiddle(lastBlockLocation), getMiddle(nowBlockLocation), EnumParticle.REDSTONE, 10, 1);
	}
	
	private Location getMiddle(final Location location) {
		return location.getBlock().getLocation().add(0.5, 0.5, 0.5);
	}
	
	public void spawnNextBlock() {
		final RandomLocation randomLocation = new RandomLocation(player.getLocation(), false, 2, 2, 4, 1, 4);
		int failCount = 0;
		
		if(finished)
			return;

		if(jumps >= 50 - 1) {
			if(!(finishBlockLocations.size() > 0)) {
				spawnFinish();
			} else {
				finished = true;
				playSound(SoundCloud.WIN);
			}
			return;
		}
		
		Location location;
		while(isInBlock(location = randomLocation.getRandomLocation())) {
			failCount++;
			
			if(failCount > 4) {
				player.sendMessage("Jump and run Block konnte nicht gespawnt werden!");
				return;
			}
		}
		
		if(lastBlockLocation != null) {
			lastBlockLocation.getBlock().setType(Material.AIR);
		}
		if(nowBlockLocation != null) {
			lastBlockLocation = nowBlockLocation;
			jumps++;
		}
		
		nowBlockLocation = location;
		location.getBlock().setType(Material.DIRT);
		playSound(SoundCloud.NEXT);
	}
	
	public void spawnFinish() {
		final RandomLocation randomLocation = new RandomLocation(player.getLocation(), true, 4, 4, 5, 1, 5);
		int failCount = 0;
		
		Location location;
		while(isInBlock(location = randomLocation.getRandomLocation())) {
			failCount++;
			
			if(failCount > 4) {
				player.sendMessage("Jump and run Block konnte nicht gespawnt werden!");
				return;
			}
		}
		
		if(lastBlockLocation != null) {
			lastBlockLocation.getBlock().setType(Material.AIR);
		}
		if(nowBlockLocation != null) {
			lastBlockLocation = nowBlockLocation;
			jumps++;
		}
		nowBlockLocation = location;
		
		final Location loc1 = location.clone().add(-1, 0, -1); // * # #
		final Location loc2 = location.clone().add(-1, 0, 0); // # * #
		final Location loc3 = location.clone().add(-1, 0, 1); // # # *
		final Location loc4 = location.clone().add(0, 0, -1);
		final Location loc5 = location.clone().add(0, 0, 1);
		final Location loc6 = location.clone().add(1, 0, -1);
		final Location loc7 = location.clone().add(1, 0, 0);
		final Location loc8 = location.clone().add(1, 0, 1);
		
		final Material material = Material.WOOL;
		final int byteData = 14;
		
		setBlock(loc1, material, byteData);
		setBlock(loc2, material, byteData);
		setBlock(loc3, material, byteData);
		setBlock(loc4, material, byteData);
		setBlock(loc5, material, byteData);
		setBlock(loc6, material, byteData);
		setBlock(loc7, material, byteData);
		setBlock(loc8, material, byteData);
		setBlock(location, Material.GOLD_BLOCK, 0);
	}
	
	private void setBlock(final Location location, final Material material, final int id) {
		finishBlockLocations.add(location);
		
		location.getBlock().setType(material);
		location.getBlock().setData((byte) id);
	}
	
	public void stop() {
		INSTANCES.remove(player);
		
		for(final Location location : finishBlockLocations) {
			if(location != null) {
				location.getBlock().setType(Material.AIR);
			}
		}
		
		if(nowBlockLocation != null)
			nowBlockLocation.getBlock().setType(Material.AIR);
		if(lastBlockLocation != null)
			lastBlockLocation.getBlock().setType(Material.AIR);
	}
	
	public void move(final Location toLocation) {
		final Block underBlock = toLocation.subtract(0.0D, 1.0D, 0.0D).getBlock();
		if(nowBlockLocation != null && toLocation.getY() < nowBlockLocation.getY() - 2.5) {
			playSound(SoundCloud.LOSE);
			stop();
			return;
		}
		if(nowBlockLocation != null && !underBlock.getLocation().equals(nowBlockLocation.getBlock().getLocation())) {
			return;
		}
		//player.sendMessage("Yes!");
		spawnNextBlock();
	}
	
	public void playSound(final SoundCloud sound) {
		player.playSound(player.getLocation(), sound.getSound(), sound.getA(), sound.getB());
	}
	
	public void sendInfoText() {
		Titles.sendActionBar(player, "§e§lJump: §c" + jumps + "§8§l/§a50");
	}
	
	/**
	 * Check if in the location is a block
	 * @param location
	 * @return location#type != AIR
	 */
	public boolean isInBlock(final Location location) {
		return location.getBlock().getType() != Material.AIR;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public Location getNowBlockLocation() {
		return nowBlockLocation;
	}
	
	public Location getLastBlockLocation() {
		return lastBlockLocation;
	}
	
	public Integer getJumps() {
		return jumps;
	}
	
	public Player getPlayer() {
		return player;
	}
}
