package de.quodix.jumpandrun.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.quodix.jumpandrun.handlers.JumpAndRun;

public class PlayerQuitListener implements Listener {
	@EventHandler
	public void onPlayerQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final JumpAndRun jumpAndRun = JumpAndRun.getInstance(player);
		
		if(jumpAndRun != null) {
			jumpAndRun.stop();
		}
	}
}
