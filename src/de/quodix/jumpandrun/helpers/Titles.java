package de.quodix.jumpandrun.helpers;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Titles {
	public static void sendActionBar(final Player player, final String text) {
		final IChatBaseComponent iChatBaseComponent = ChatSerializer.a("{\"text\": \"" + text + "\"}");
		final PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
