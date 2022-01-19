package de.quodix.jumpandrun.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.quodix.jumpandrun.handlers.JumpAndRun;

public class PlayerMoveListener implements Listener {
	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		final JumpAndRun jumpAndRun = JumpAndRun.getCheckedInstance(player);
		
		if(jumpAndRun.isActive()) {
			jumpAndRun.move(event.getTo().clone());
			return;
		}
		
		if(event.getTo().getBlock().getType() == Material.IRON_PLATE) {
			jumpAndRun.setActive(true);
		}
	}
}
