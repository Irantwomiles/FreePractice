package utils.profile;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import practice.FreePractice;

import java.util.HashSet;
import java.util.Iterator;

public class ProfileManager implements Listener {

    private static HashSet<Profile> profiles = new HashSet<Profile>();

    @Getter
    private static ProfileManager instance;

    public ProfileManager() {
        instance = this;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FreePractice.getInstance(), new Runnable() {
            public void run() {
                profiles.add(new Profile(player.getUniqueId()));
            }
        }, 5L);


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        final Player player = event.getPlayer();

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FreePractice.getInstance(), new Runnable() {
            public void run() {
                if(getProfile(player) != null) {
                    profiles.remove(getProfile(player));
                }
            }
        }, 5L);

    }

    public Profile getProfile(Player player) {

        Iterator iterator = profiles.iterator();

        while(iterator.hasNext()) {

            Profile profile = (Profile) iterator.next();

            if(profile.getPlayer().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                return profile;
            }

        }

        return null;

    }

}
