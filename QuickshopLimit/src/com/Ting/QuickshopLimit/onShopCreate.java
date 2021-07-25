package com.Ting.QuickshopLimit;

import com.Ting.QuickshopLimit.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.event.ShopCreateEvent;

public class onShopCreate implements Listener {

    public onShopCreate() {
    }

    @EventHandler
    public void onCreate(ShopCreateEvent e) {
        String key = e.getShop().getLocation().getBlockX() + "," + e.getShop().getLocation().getBlockY() + "," + e.getShop().getLocation().getBlockZ();
        Main.getdataConfig().set(key + ".limit", "nolimit");
        Main.save();
    }
}
