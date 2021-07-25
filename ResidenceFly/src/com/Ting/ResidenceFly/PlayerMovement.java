package com.Ting.ResidenceFly;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.Ting.ResidenceFly.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovement implements Listener {

    public PlayerMovement() {
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        if (e.getPlayer().isFlying()) {
            final Location loc = e.getPlayer().getLocation().getBlock().getLocation();
            final Location ploc = e.getPlayer().getLocation();
            ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);
            if (res != null) {
                FlagPermissions perms = Residence.getInstance().getPermsByLoc(loc);
                if (perms.listFlags().contains("§4fly") || !e.getPlayer().hasPermission("cocobeenserveraddon.fly")) {
                    e.setCancelled(true);
                    e.getPlayer().setFlying(false);
                    Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
                        public void run() {
                            e.getPlayer().teleport(PlayerMovement.this.nearestground(loc, ploc));
                        }
                    });
                    e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("no-claim-fly-permission-msg").replaceAll("&", "§"));
                }
            }
        }

    }

    public Location nearestground(Location loc, Location ploc) {
        while(loc.getBlock().getType().equals(Material.AIR)) {
            loc.setY(loc.getY() - 1.0D);
        }

        ploc.setY(loc.getY() + 1.0D);
        return ploc;
    }

}
