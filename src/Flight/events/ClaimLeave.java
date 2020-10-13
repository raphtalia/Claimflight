package Flight.events;

import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class ClaimLeave extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Claim claim;

    public ClaimLeave(Player player, Claim claim) {
        this.player = player;
        this.claim = claim;
    }

    public Player getPlayer() {
        return player;
    }

    public Claim getClaim() {
        return claim;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return  handlers;
    }
}
