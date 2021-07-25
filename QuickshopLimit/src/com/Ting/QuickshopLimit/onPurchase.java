package com.Ting.QuickshopLimit;

import java.util.UUID;
import com.Ting.QuickshopLimit.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.maxgamer.quickshop.event.ShopPurchaseEvent;

public class onPurchase implements Listener {
    public onPurchase() {
    }

    @EventHandler
    public void SuccessPurchase(ShopPurchaseEvent e) {
        UUID uuid = e.getPurchaser();
        String key = e.getShop().getLocation().getBlockX() + "," + e.getShop().getLocation().getBlockY() + "," + e.getShop().getLocation().getBlockZ();
        if (Main.getdataConfig().getConfigurationSection(key).getKeys(false).contains("limit")) {
            if (!Main.getdataConfig().getString(key + ".limit").equals("nolimit")) {
                int numberofpurchase;
                if (Main.getdataConfig().getConfigurationSection(key).contains(String.valueOf(uuid))) {
                    numberofpurchase = e.getAmount();
                    int chance = Main.getdataConfig().getInt(key + "." + uuid);
                    if (chance - numberofpurchase < 0) {
                        Player p = Bukkit.getPlayer(uuid);
                        if (p != null) {
                            p.sendMessage(Main.getInstance().getConfig().getString("overlimit-msg") + "");
                        }

                        e.setCancelled(true);
                    } else {
                        int left = chance - numberofpurchase;
                        Main.getdataConfig().set(key + "." + uuid, left);
                        Main.save();
                    }
                } else if (Main.getdataConfig().getInt(key + ".limit") - e.getAmount() < 0) {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p != null) {
                        p.sendMessage(Main.getInstance().getConfig().getString("overlimit-msg") + "");
                    }

                    e.setCancelled(true);
                } else {
                    numberofpurchase = Main.getdataConfig().getInt(key + ".limit") - e.getAmount();
                    Main.getdataConfig().set(key + "." + uuid, numberofpurchase);
                    Main.save();
                }
            }
        } else {
            Main.getdataConfig().set(key + ".limit", "nolimit");
            Main.save();
        }

    }
}