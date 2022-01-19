package de.quodix.jumpandrun.main;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.quodix.jumpandrun.listeners.PlayerMoveListener;
import de.quodix.jumpandrun.listeners.PlayerQuitListener;

public class GenJumpAndRun extends JavaPlugin {
	private static GenJumpAndRun instance;
	
	@Override
	public void onLoad() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		final PluginManager pluginManager = this.getServer().getPluginManager();
		
		pluginManager.registerEvents(new PlayerMoveListener(), instance);
		pluginManager.registerEvents(new PlayerQuitListener(), instance);
	}
	
	public static GenJumpAndRun getInstance() {
		return instance;
	}
}
