package com.Ting.QuickshopLimit;

import java.util.Set;
import com.Ting.QuickshopLimit.Main;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.maxgamer.quickshop.api.QuickShopAPI;
import org.maxgamer.quickshop.shop.Shop;

public class PlayerCommand implements Listener {

    FileConfiguration config = Main.getdataConfig();

    public PlayerCommand() {
    }

    @EventHandler
    public void PreCommand(PlayerCommandPreprocessEvent e) {
        String[] a = e.getMessage().split(" ");
        if (a[0].equals("/qs")) {
            if (a.length >= 2) {
                Player p = e.getPlayer();
                Block b;
                Shop shop;
                String key;
                String message;
                if (a[1].equals("setmax") && p.hasPermission("cocobeenserveraddon.setmax") && a.length == 3 && Main.getInstance().getConfig().getBoolean("setmax")) {
                    b = p.getTargetBlock((Set)null, 5);
                    if (QuickShopAPI.getShopAPI().getShop(b).isPresent()) {
                        shop = (Shop)QuickShopAPI.getShopAPI().getShop(b).get();
                        if (shop.getOwner().equals(p.getUniqueId())) {
                            key = b.getLocation().getBlockX() + "," + b.getLocation().getBlockY() + "," + b.getLocation().getBlockZ();
                            this.config.set(key + ".limit", Integer.valueOf(a[2]));
                            Main.save();
                            message = Main.getInstance().getConfig().getString("setmax-msg").replace("{number}", a[2]);
                            p.sendMessage(message);
                        } else {
                            p.sendMessage(Main.getInstance().getConfig().getString("notowner-msg") + "");
                        }
                    }

                    e.setCancelled(true);
                } else if (a[1].equals("setremove") && p.hasPermission("cocobeenserveraddon.setremove") && Main.getInstance().getConfig().getBoolean("setremove")) {
                    b = p.getTargetBlock((Set)null, 5);
                    if (QuickShopAPI.getShopAPI().getShop(b).isPresent()) {
                        shop = (Shop)QuickShopAPI.getShopAPI().getShop(b).get();
                        if (shop.getOwner().equals(p.getUniqueId())) {
                            key = b.getLocation().getBlockX() + "," + b.getLocation().getBlockY() + "," + b.getLocation().getBlockZ();
                            this.config.set(key, (Object)null);
                            Main.save();
                            message = Main.getInstance().getConfig().getString("setremove-msg");
                            p.sendMessage(message);
                        }
                    }

                    e.setCancelled(true);
                }

            }
        }
    }

}
