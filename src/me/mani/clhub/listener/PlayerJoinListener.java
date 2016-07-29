package me.mani.clhub.listener;

import me.mani.clcore.ClickManager;
import me.mani.clcore.Core;
import me.mani.clcore.locale.LocaleManager;
import me.mani.clcore.util.CachedServerInfo;
import me.mani.clcore.util.InventoryUtils;
import me.mani.clhub.util.ItemBuilders;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		event.setJoinMessage(Core.getLocaleManager().translate("join", event.getPlayer().getName()));
		
		event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
		
		ClickManager.register(event.getPlayer());
		
		Inventory inventory = event.getPlayer().getInventory();
		LocaleManager localeManager = Core.getLocaleManager();
		inventory.clear();
		inventory.setItem(2, ItemBuilders.normal(Material.EYE_OF_ENDER).name(localeManager.translate("item-switch-server")).build());
		ClickManager.getClickManager(event.getPlayer()).addHotbarClickListener(2, (interactEvent) -> {
			System.out.println("Debug 2");
			Inventory menu = Bukkit.createInventory(null, 27);
			for (Map.Entry<Integer, String> entry : Arrays.asList(
					new AbstractMap.SimpleEntry<>(11, "drecksteam"),
					new AbstractMap.SimpleEntry<>(13, "build"),
					new AbstractMap.SimpleEntry<>(15, "lobby")
			)) {
				CachedServerInfo serverInfo = Core.getServerManager().getServerInfo(entry.getValue());
				if (serverInfo == null || serverInfo.isOffline()) {
					menu.setItem(entry.getKey(), ItemBuilders.firework().effect(FireworkEffect.builder().withColor(Color.BLUE).build()).name("§cOffline").lore(Arrays.asList(
							"§7Spieler§8:§7 - §8/§7 -"
					)).hide(ItemFlag.values()).build());
				}
				else {
					String motd = serverInfo.getMotd().split("\n")[0];
					menu.setItem(entry.getKey(), ItemBuilders.firework().effect(FireworkEffect.builder().withColor(Color.BLUE).build()).name(motd).lore(Arrays.asList(
							"§7Spieler§8:§7 " + serverInfo.getOnlinePlayers() + " §8/§7 " + serverInfo.getMaxPlayers()
					)).hide(ItemFlag.values()).build());
					ClickManager.getClickManager(event.getPlayer()).addInventoryClickListener(entry.getKey(), (clickEvent) -> {
						Core.getServerManager().connect(event.getPlayer(), entry.getValue());
					});
				}
			}
			InventoryUtils.fillUp(menu, ItemBuilders.normal(Material.STAINED_GLASS_PANE).durability((short) 7).name(" ").build());
			ClickManager.getClickManager(event.getPlayer()).setInventory(menu);
			event.getPlayer().openInventory(menu);
		});
		inventory.setItem(4, ItemBuilders.normal(Material.BLAZE_ROD).name(localeManager.translate("item-hide-players")).build());
		inventory.setItem(6, ItemBuilders.normal(Material.NETHER_STAR).name(localeManager.translate("item-effects")).build());
		
	}
	
}
