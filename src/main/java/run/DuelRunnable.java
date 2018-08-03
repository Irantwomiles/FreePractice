package run;

import duel.solo.SoloDuel;
import duel.solo.SoloDuelManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class DuelRunnable extends BukkitRunnable {

    public void run() {

        //Solo Duel

        for(SoloDuel duel : SoloDuelManager.getDuels()) {

            duel.setMatchDuration(duel.getMatchDuration() + 1);

            if(duel.getMatchDuration() <= 5) {

                for(UUID uuid : duel.getPlayers()) {

                    Player player = Bukkit.getPlayer(uuid);

                    if(player == null) continue;

                    player.sendMessage(ChatColor.YELLOW + "Match starting in " + ChatColor.GRAY + duel.getMatchDuration() + ChatColor.YELLOW + " second(s)");

                }

            }


        }
    }
}
