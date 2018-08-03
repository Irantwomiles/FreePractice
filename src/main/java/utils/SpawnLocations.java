package utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import practice.FreePractice;

public class SpawnLocations {

    private PlayerItems items = new PlayerItems();

    public void teleportSpawn(final Player player) {

        double x = FreePractice.getInstance().getConfig().getDouble("spawn.x");
        double y = FreePractice.getInstance().getConfig().getDouble("spawn.y");
        double z = FreePractice.getInstance().getConfig().getDouble("spawn.z");
        float pitch = (float) FreePractice.getInstance().getConfig().getDouble("spawn.pitch");
        float yaw = (float) FreePractice.getInstance().getConfig().getDouble("spawn.yaw");
        String world = FreePractice.getInstance().getConfig().getString("spawn.world");

        final Location loc = new Location(Bukkit.getWorld(world), x, y, z);
        loc.setPitch(pitch);
        loc.setYaw(yaw);

        Bukkit.getScheduler().scheduleSyncDelayedTask(FreePractice.getInstance(), new Runnable() {
            public void run() {
                player.teleport(loc);
            }

        }, 3);

        for(PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }


        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            player.showPlayer(p);
            p.showPlayer(player);
        }

        player.setHealth(20.0);
        player.setFoodLevel(20);

        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);

        items.spawnItems(player);
    }

    public void teleportEditor(Player player) {

        double x = FreePractice.getInstance().getConfig().getDouble("editor.x");
        double y = FreePractice.getInstance().getConfig().getDouble("editor.y");
        double z = FreePractice.getInstance().getConfig().getDouble("editor.z");
        float pitch = (float) FreePractice.getInstance().getConfig().getDouble("editor.pitch");
        float yaw = (float) FreePractice.getInstance().getConfig().getDouble("editor.yaw");
        String world = FreePractice.getInstance().getConfig().getString("editor.world");

        Location loc = new Location(Bukkit.getWorld(world), x, y, z);
        loc.setPitch(pitch);
        loc.setYaw(yaw);

        player.teleport(loc);

        player.setHealth(20.0);
        player.setFoodLevel(20);
    }


}
