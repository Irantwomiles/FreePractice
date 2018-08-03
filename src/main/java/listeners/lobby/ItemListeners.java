package listeners.lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import utils.profile.PlayerState;
import utils.profile.Profile;
import utils.profile.ProfileManager;

public class ItemListeners implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        Profile profile = ProfileManager.getInstance().getProfile(event.getPlayer());

        if(profile == null) return;

        if(profile.getState() == PlayerState.LOBBY) event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {

        Profile profile = ProfileManager.getInstance().getProfile(event.getPlayer());

        if(profile == null) return;

        if(profile.getState() == PlayerState.LOBBY) event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Profile profile = ProfileManager.getInstance().getProfile(event.getPlayer());

        if(profile == null) return;

        if(profile.getState() == PlayerState.LOBBY) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Profile profile = ProfileManager.getInstance().getProfile(event.getPlayer());

        if(profile == null) return;

        if(profile.getState() == PlayerState.LOBBY) event.setCancelled(true);
    }
}
