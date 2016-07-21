package me.mani.clhub.listener;

import me.mani.clcore.Core;
import me.mani.clhub.Hub;
import me.mani.clhub.portal.Portal;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		if (event.getPlayer().getItemInHand() != null) {
			if (event.getPlayer().getItemInHand().getType() == Material.STICK && event.getBlock().getType() == Portal.PORTAL_MATERIAL) {
				Hub.getInstance().getPortals().add(Portal.createPortal(event.getBlock().getLocation(), "build")); // TODO: Add some server input
				event.getPlayer().sendMessage(Core.getLocaleManager().translate("portal-create"));
				if (Hub.getInstance().getDataManager().savePortals(Hub.getInstance().getPortals()) == 0)
					event.getPlayer().sendMessage(Core.getLocaleManager().translate("portal-save-error"));
				event.setCancelled(true);
			}
		}
		
	}

}
