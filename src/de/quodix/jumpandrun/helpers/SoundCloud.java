package de.quodix.jumpandrun.helpers;

import org.bukkit.Sound;

public enum SoundCloud {
	WIN(Sound.WITHER_DEATH, 1F, 1F),
	LOSE(Sound.LAVA_POP, 1F, 1F),
	NEXT(Sound.NOTE_PLING, 1F, 1F);
	
	private Sound sound;
	private Float a, b;
	
	SoundCloud(final Sound sound, final Float a, final Float b) {
		this.sound = sound;
		this.a = a;
		this.b = b;
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public Float getA() {
		return a;
	}
	
	public Float getB() {
		return b;
	}
}
