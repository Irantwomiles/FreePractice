package duel.solo;

import arena.Arena;
import arena.ArenaManager;
import arena.ArenaType;
import duel.Duel;
import duel.DuelType;
import gametype.GameMode;
import listeners.duel.custom.DuelStartEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class SoloDuelManager {

    @Getter
    private static ArrayList<SoloDuel> duels = new ArrayList<SoloDuel>();

    private static SoloDuelManager manager;

    private SoloDuelManager() {}

    public static SoloDuelManager getManager() {
        if(manager == null) {
            manager = new SoloDuelManager();
        }

        return manager;
    }

    public void createDuel(UUID uuid1, UUID uuid2, DuelType type, GameMode game) {

        SoloDuel duel = new SoloDuel(uuid1, uuid2);

        ArenaType arenaType = ArenaType.valueOf(type.toString());

        duel.setGame(game);

        Arena arena = ArenaManager.getManager().getRandomArena(arenaType);

        if(arena != null) {

            duel.setArena(arena);

            Player player1 = Bukkit.getPlayer(duel.getPlayer1());
            Player player2 = Bukkit.getPlayer(duel.getPlayer2());

            duel.getPlayers().add(uuid1);
            duel.getPlayers().add(uuid2);

            if(player1 != null && player2 != null) {

                player1.teleport(arena.getLoc1());
                player2.teleport(arena.getLoc2());

                player1.sendMessage(ChatColor.YELLOW + "You are fighting " + ChatColor.RED + player2.getName());
                player2.sendMessage(ChatColor.YELLOW + "You are fighting " + ChatColor.RED + player1.getName());

                ArenaManager.getManager().getAvailable().remove(arena);

                duels.add(duel);

                Bukkit.getServer().getPluginManager().callEvent(new DuelStartEvent(duel));

            }

        }
    }

    public boolean isPlayerFighting(Player player) {

        for(SoloDuel duel : duels) {

            if(duel.getPlayer1().toString().equalsIgnoreCase(player.getUniqueId().toString()) || duel.getPlayer2().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                return true;
            }

        }

        return false;

    }

    public Duel getPlayerDuel(Player player) {

        for(SoloDuel duel : duels) {

            if(duel.getPlayer1().toString().equalsIgnoreCase(player.getUniqueId().toString()) || duel.getPlayer2().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                return duel;
            }

        }

        return null;

    }

}
