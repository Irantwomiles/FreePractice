package listeners.duel;

import commands.duel.DuelCommand;
import duel.solo.SendDuel;
import gametype.GameMode;
import gametype.GameModeManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import utils.profile.PlayerState;
import utils.profile.Profile;
import utils.profile.ProfileManager;

public class DuelRequestListeners implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if(!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        Profile profile = ProfileManager.getInstance().getProfile(player);

        if(profile == null) {
            return;
        }

        if(profile.getState() != PlayerState.LOBBY) {
            return;
        }

        if(event.getClickedInventory() == null) {
            return;
        }

        if(event.getClickedInventory().getTitle() == null) {
            return;
        }

        if(event.getCurrentItem() == null) {
            return;
        }

        if(!event.getCurrentItem().hasItemMeta()) {
            return;
        }

        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return;
        }

        if(event.getInventory().getTitle().equals(ChatColor.RED + "Game Modes")) {

            Player target = Bukkit.getPlayer(DuelCommand.getDuelRequest().get(player.getUniqueId()));

            if(target == null) {
                player.sendMessage(org.bukkit.ChatColor.RED + "Could not find that player.");
                return;
            }

            Profile targetProfile = ProfileManager.getInstance().getProfile(target);

            if(targetProfile == null) {
                player.sendMessage(org.bukkit.ChatColor.RED + "Could not find that player.");
                return;
            }

            if(targetProfile.getState() != PlayerState.LOBBY) {
                player.sendMessage(org.bukkit.ChatColor.RED + "That player is currently busy.");
                return;
            }

            if(targetProfile.getDuelRequest().containsKey(player.getUniqueId())) {
                player.sendMessage(org.bukkit.ChatColor.RED + "You have already sent a duel request to that person.");
                return;
            }

            String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

            GameMode game = GameModeManager.getManager().getGameMode(name);

            if(game == null) return;

            targetProfile.getDuelRequest().put(player.getUniqueId(), new SendDuel(player.getUniqueId(), target.getUniqueId(), game));

            event.setCancelled(true);

            player.closeInventory();
        }

    }


}
