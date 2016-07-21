package me.mani.clhub.listener;

import me.mani.clcore.Core;
import me.mani.clhub.Hub;
import me.mani.clhub.portal.Portal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		if (event.getTo().getBlock().getType() == Portal.PORTAL_MATERIAL) {
			for (Portal portal : Hub.getInstance().getPortals())
				if (!portal.contains(event.getFrom().getBlock().getLocation()) && portal.contains(event.getTo().getBlock().getLocation()))
					Core.getServerManager().connect(event.getPlayer(), portal.getTargetServerName());
		}
		
	}

}
