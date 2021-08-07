package com.Ting.SnowballPortalFreezeAddon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

import com.Ting.SnowballPortalFreezeAddon.Main;
import org.bukkit.plugin.Plugin;

public class EventListener implements Listener {

    @EventHandler
    public void onJoin(EntityPortalEvent e) {
        EntityType entityType = e.getEntityType();
        Entity entity = e.getEntity();
        if (entityType == EntityType.SNOWBALL){
            e.setCancelled(true);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    entity.remove();
                }
            }, 10L);

        }

    }
}