package com.Ting.QuickshopLimit;

import com.Ting.QuickshopLimit.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.event.ShopDeleteEvent;

public class onShopDelete implements Listener {

    public onShopDelete() {
    }

    @EventHandler
    public void onCreate(ShopDeleteEvent e) {
        String key = e.getShop().getLocation().getBlockX() + "," + e.getShop().getLocation().getBlockY() + "," + e.getShop().getLocation().getBlockZ();
        Main.getdataConfig().set(key, (Object)null);
        Main.save();
    }

}
