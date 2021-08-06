package com.Ting.SnowballPortalFreezeAddon;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

import com.Ting.SnowballPortalFreezeAddon.Main;

public class EventListener implements Listener {

    @EventHandler
    public void onJoin(EntityPortalEvent e) {
        EntityType entityType = e.getEntityType();
        if (entityType == EntityType.SNOWBALL){
            e.setCancelled(true);
        }

    }
}