package de.quodix.jumpandrun.helpers;

import java.util.Random;

import org.bukkit.Location;

public class RandomLocation {
	private static final Random RANDOM = new Random();
	
	private Location location, randomLocation;
	private Integer minBoundsX, minBoundsZ;
	private Integer maxBoundsX, maxBoundsZ, maxBoundsY;
	private Boolean canZero;
	
	public RandomLocation(final Location location, final Boolean canZero,
			final Integer minBoundsX, final Integer minBoundsZ,
			final Integer maxBoundsX, final Integer maxBoundsY, final Integer maxBoundsZ) {
		this.location = location;
		this.minBoundsX = minBoundsX;
		this.minBoundsZ = minBoundsZ;
		this.maxBoundsX = maxBoundsX;
		this.maxBoundsZ = maxBoundsZ;
		this.maxBoundsY = maxBoundsY;
		this.canZero = canZero;
	}
	
	private void generate() {
		this.randomLocation = this.location.clone();
		
		Integer rX = canZero ? RANDOM.nextInt(maxBoundsX) : RANDOM.nextInt(maxBoundsX - minBoundsX) + minBoundsX;
		final Integer rY = RANDOM.nextInt(maxBoundsY);
		Integer rZ = canZero ? RANDOM.nextInt(maxBoundsZ) : RANDOM.nextInt(maxBoundsZ - minBoundsZ) + minBoundsZ;
		
		if(canZero) {
			if(rZ == 0 || rX < maxBoundsX) {
				if(rX == 0 || rZ < maxBoundsX) {
					rX = maxBoundsX / 2;
					rZ = maxBoundsZ;
				}
			}
		}
		
		this.randomLocation.add(rX, rY, rZ);
	}
	
	public Location getRandomLocation() {
		generate();
		return randomLocation;
	}
}
