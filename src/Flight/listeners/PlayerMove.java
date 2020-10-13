package Flight.listeners;

import Flight.events.ClaimEnter;
import Flight.events.ClaimLeave;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerMove implements Listener {
    private void moved(Player player, Location startLoc, Location endLoc) {
        Claim fromClaim = GriefPrevention.instance.dataStore.getClaimAt(endLoc, true, null);
        Claim toClaim = GriefPrevention.instance.dataStore.getClaimAt(startLoc, true, null);

        if (fromClaim != null && toClaim != null && !(fromClaim.getID().equals(toClaim.getID()))) {
            Bukkit.getPluginManager().callEvent(new ClaimEnter(player, toClaim));
        } else if (toClaim != null && fromClaim == null) {
            Bukkit.getPluginManager().callEvent(new ClaimEnter(player, toClaim));
        } else if (fromClaim != null && toClaim == null) {
            Bukkit.getPluginManager().callEvent(new ClaimLeave(player, fromClaim));
        }
    }

    private void playerSpawned(Player player) {
        Location loc = player.getLocation();
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, null);

        if (claim != null) {
            Bukkit.getPluginManager().callEvent(new ClaimEnter(player, claim));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        moved(event.getPlayer(), event.getTo(), event.getFrom());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        moved(event.getPlayer(), event.getTo(), event.getFrom());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        playerSpawned(event.getPlayer());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        playerSpawned(event.getPlayer());
    }
}