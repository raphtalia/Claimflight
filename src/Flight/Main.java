package Flight;

import Flight.listeners.CrossedClaim;
import Flight.listeners.PlayerMove;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        this.getServer().getPluginManager().registerEvents(new CrossedClaim(), this);
    }

    @Override
    public void onDisable() {

    }
}