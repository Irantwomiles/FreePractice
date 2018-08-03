package listeners.duel;

import duel.solo.SoloDuel;
import kit.KitManager;
import listeners.duel.custom.DuelStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import practice.FreePractice;
import utils.profile.PlayerState;
import utils.profile.Profile;
import utils.profile.ProfileManager;

public class DuelStartListener implements Listener {

    @EventHandler
    public void onStart(DuelStartEvent event) {

        final SoloDuel duel = event.getDuel();

        final Player player1 = Bukkit.getPlayer(duel.getPlayer1());
        final Player player2 = Bukkit.getPlayer(duel.getPlayer2());

        Profile profile1 = ProfileManager.getInstance().getProfile(player1);
        Profile profile2 = ProfileManager.getInstance().getProfile(player2);

        //TODO: End match if any of the profiles are null, or if one or both of the players are null
        if(profile1 == null || profile2 == null) return;

        profile1.setState(PlayerState.GAME);
        profile2.setState(PlayerState.GAME);

        /*
        Clear all potion effects
         */
        for(PotionEffect effect : player2.getActivePotionEffects())
        {
            player1.removePotionEffect(effect.getType());
        }

        for(PotionEffect effect : player2.getActivePotionEffects())
        {
            player2.removePotionEffect(effect.getType());
        }

        /*
        Set them to max health
         */

        player1.setHealth(20.0);
        player2.setHealth(20.0);

        player1.setFoodLevel(20);
        player2.setFoodLevel(20);

        Bukkit.getScheduler().scheduleSyncDelayedTask(FreePractice.getInstance(), new Runnable() {
            public void run() {

                if(KitManager.getManager().getKitSize(player1, duel.getGame(), true) > 0) {

                    KitManager.getManager().loadKits(player1, duel.getGame(), true);
                } else {
                    player1.getInventory().clear();

                    player1.getInventory().setContents(duel.getGame().getInv());
                    player1.getInventory().setArmorContents(duel.getGame().getArmor());
                }

                if(KitManager.getManager().getKitSize(player2, duel.getGame(), true) > 0) {

                    KitManager.getManager().loadKits(player2, duel.getGame(), true);
                } else {
                    player2.getInventory().clear();

                    player2.getInventory().setContents(duel.getGame().getInv());
                    player2.getInventory().setArmorContents(duel.getGame().getArmor());
                }

            }
        }, 5);

    }

}
