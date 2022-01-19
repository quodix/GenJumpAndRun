package de.quodix.jumpandrun.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class Particle {
	public static void spawnParticle(final Player player, final Location location, final EnumParticle particle,
			final int menge) {
		final float x = (float) location.getX();
		final float y = (float) location.getY();
		final float z = (float) location.getZ();

		final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, x, y, z, 0, 0, 0, 1,
				menge);

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public static void spawnParticleCircle(final Player player, final Location loc, final EnumParticle particle,
			final float radius) {
		for (double t = 0; t < 50; t += 0.5) {
			final float x = radius * (float) Math.sin(t);
			final float z = radius * (float) Math.cos(t);

			final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true,
					(float) loc.getX() + x, (float) loc.getY(), (float) loc.getZ() + z, 0, 0, 0, 0, 1);

			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public static void spawnParticleCircle(final Player player, final Location loc, final EnumParticle particle,
			final float radius, final int menge) {
		for (double t = 0; t < 50; t += 0.5) {
			final float x = radius * (float) Math.sin(t);
			final float z = radius * (float) Math.cos(t);

			final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true,
					(float) loc.getX() + x, (float) loc.getY(), (float) loc.getZ() + z, 0, 0, 0, 0, menge);

			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public static void spawnParticleCircle(final Location loc, final EnumParticle particle, final float radius) {
		for (double t = 0; t < 50; t += 0.5) {
			final float x = radius * (float) Math.sin(t);
			final float z = radius * (float) Math.cos(t);

			final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true,
					(float) loc.getX() + x, (float) loc.getY(), (float) loc.getZ() + z, 0, 0, 0, 0, 1);

			for (final Player allPlayers : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) allPlayers).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	public static void spawnParticleCircle(final Location loc, final EnumParticle particle, final float radius,
			final int menge) {
		for (double t = 0; t < 50; t += 0.5) {
			final float x = radius * (float) Math.sin(t);
			final float z = radius * (float) Math.cos(t);

			final PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true,
					(float) loc.getX() + x, (float) loc.getY(), (float) loc.getZ() + z, 0, 0, 0, 0, menge);

			for (final Player allPlayers : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) allPlayers).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	public static void spawnParticleAlongLine(final Player player, final Location startLocation, final Location endLocation, final EnumParticle particle, final int points, final int menge) {
		final double distance = startLocation.distance(endLocation) / points;
		for (int i = 0; i < points; i++) {
			final Location location = startLocation.clone();
			final Vector direction = endLocation.toVector().subtract(startLocation.toVector()).normalize();
			final Vector vector = direction.multiply(i * distance);
			location.add(vector.getX(), vector.getY(), vector.getZ());
			spawnParticle(player, location, particle, menge);
		}
	}
}
