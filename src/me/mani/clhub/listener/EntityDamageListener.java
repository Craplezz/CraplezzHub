package me.mani.clhub.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Overload
 * @version 1.0
 */
public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            event.getEntity().teleport(Bukkit.getWorld("world").getSpawnLocation());
        }
    }

}
