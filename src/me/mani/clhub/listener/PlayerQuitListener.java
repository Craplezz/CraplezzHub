package me.mani.clhub.listener;

import me.mani.clcore.ClickManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		handleQuit(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		handleQuit(event.getPlayer());
	}
	
	private void handleQuit(Player player) {
		ClickManager.unregister(player);
	}
	
}
