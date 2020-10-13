package Flight.listeners;

import Flight.events.ClaimEnter;
import Flight.events.ClaimLeave;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CrossedClaim implements Listener {

    private void tpPlayerToGround(Player player) {
        Location loc = player.getLocation();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        World world = player.getWorld();

        for (int y = loc.getBlockY(); y > 1; y--) {
            if (!world.getBlockAt(x, y, z).isEmpty()) {
                Location newLoc = new Location(world, loc.getX(), Math.min(y + 2, loc.getY()), loc.getZ());
                newLoc.setPitch(loc.getPitch());
                newLoc.setYaw(loc.getYaw());
                player.teleport(newLoc);
                break;
            }
        }
    }

    @EventHandler
    public void onEnter(ClaimEnter event) {
        Player player = event.getPlayer();
        Claim claim = event.getClaim();
        GameMode gameMode = player.getGameMode();

        if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
            if (player.hasPermission("claimflight.restrictedfly") && !player.hasPermission("claimflight.unrestrictedfly")) {
                if (claim.ownerID.equals(player.getUniqueId()) || claim.getPermission(player.getUniqueId().toString()).isGrantedBy(ClaimPermission.Build)) {
                    player.setAllowFlight(true);
                }
            }
        }
    }

    @EventHandler
    public void onLeave(ClaimLeave event) {
        Player player = event.getPlayer();
        Claim claim = event.getClaim();
        GameMode gameMode = player.getGameMode();

        if (gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE) {
            if (player.hasPermission("claimflight.restrictedfly") && !player.hasPermission("claimflight.unrestrictedfly")) {
                if (player.isFlying()) {
                    tpPlayerToGround(player);
                }
                player.setAllowFlight(false);
            }
        }
    }
}
