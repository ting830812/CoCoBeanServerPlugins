package com.Ting.ResidenceSafariNet;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import de.Linus122.SafariNet.API.Listener;
import de.Linus122.SafariNet.API.Status;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import com.Ting.ResidenceSafariNet.Main;

public class SNListener implements Listener {


    @Override
    public void playerCatchEntity(Player player, Entity entity, Status status) {

        final Location loc = player.getTargetBlock(null, 100).getLocation();
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);
        if (res != null) {
            ResidencePermissions perms = res.getPermissions();
            boolean hasPermission = perms.playerHas(player, "spawn-egg", true);
            if (!hasPermission && Main.getInstance().getConfig().getBoolean("res-spawn-egg")){
                status.setCancelled(true);
                player.sendMessage(Main.getInstance().getConfig().getString("no-claim-spawn-egg-permission-msg") + "");
            }

        }
    }

    @Override
    public void playerReleaseEntity(Player player, Entity entity, Status status) {

        final Location loc = player.getTargetBlock(null, 100).getLocation();
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);
        if (res != null) {
            ResidencePermissions perms = res.getPermissions();
            boolean hasPermission = perms.playerHas(player, "spawn-egg", true);
            if (!hasPermission && Main.getInstance().getConfig().getBoolean("res-spawn-egg")){
                status.setCancelled(true);
                player.sendMessage(Main.getInstance().getConfig().getString("no-claim-spawn-egg-permission-msg") + "");
            }

        }
    }
}