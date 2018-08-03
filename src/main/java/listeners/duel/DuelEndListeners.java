package listeners.duel;

import arena.ArenaManager;
import duel.solo.SoloDuel;
import duel.solo.SoloDuelManager;
import listeners.duel.custom.DuelEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import practice.FreePractice;
import utils.SpawnLocations;

import java.util.UUID;

public class DuelEndListeners implements Listener {

    private SpawnLocations locations = new SpawnLocations();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if(!SoloDuelManager.getManager().isPlayerFighting(player)) return;

        SoloDuel duel = SoloDuelManager.getManager().getPlayerDuel(player);

        Player player1 = Bukkit.getPlayer(duel.getPlayer1());
        Player player2 = Bukkit.getPlayer(duel.getPlayer2());

        if(player1.getName().equalsIgnoreCase(player.getName())) {

            Bukkit.getServer().getPluginManager().callEvent(new DuelEndEvent(duel, duel.getPlayer2(), duel.getPlayer1()));

        } else if(player2.getName().equalsIgnoreCase(player.getName())) {

            Bukkit.getServer().getPluginManager().callEvent(new DuelEndEvent(duel, duel.getPlayer1(), duel.getPlayer2()));

        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if(!SoloDuelManager.getManager().isPlayerFighting(player)) return;

        SoloDuel duel = SoloDuelManager.getManager().getPlayerDuel(player);

        Player player1 = Bukkit.getPlayer(duel.getPlayer1());
        Player player2 = Bukkit.getPlayer(duel.getPlayer2());

        if(player1.getName().equalsIgnoreCase(player.getName())) {

            Bukkit.getServer().getPluginManager().callEvent(new DuelEndEvent(duel, duel.getPlayer2(), duel.getPlayer1()));

        } else if(player2.getName().equalsIgnoreCase(player.getName())) {

            Bukkit.getServer().getPluginManager().callEvent(new DuelEndEvent(duel, duel.getPlayer1(), duel.getPlayer2()));

        }

    }

    @EventHandler
    public void onEnd(final DuelEndEvent event) {

        for(UUID uuid : event.getDuel().getPlayers()) {

            Player player = Bukkit.getPlayer(uuid);

            if(player == null) continue;

            OfflinePlayer winner = Bukkit.getOfflinePlayer(event.getWinner());

            player.sendMessage(ChatColor.GREEN + winner.getName() + ChatColor.YELLOW + " has won the match!");

        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FreePractice.getInstance(), new Runnable() {
            public void run() {
                for(UUID uuid : event.getDuel().getPlayers()) {

                    Player player = Bukkit.getPlayer(uuid);

                    if (player == null) continue;

                    locations.teleportSpawn(player);
                }

                ArenaManager.getManager().getAvailable().add(event.getDuel().getArena());
                SoloDuelManager.getDuels().remove(event.getDuel());

            }
        }, 20 * 3);

    }

}
