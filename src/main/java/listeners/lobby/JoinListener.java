package listeners.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import utils.SpawnLocations;

public class JoinListener implements Listener {

    private SpawnLocations locations = new SpawnLocations();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        locations.teleportSpawn(player);

    }
}
