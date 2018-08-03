package commands.duel;

import duel.DuelType;
import duel.solo.SoloDuelManager;
import gametype.GameMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.profile.PlayerState;
import utils.profile.Profile;
import utils.profile.ProfileManager;

public class AcceptCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("accept")) {

            if(args.length < 1) {
                player.sendMessage(ChatColor.RED + "/accept <player>");
                return true;
            }

            Profile profile = ProfileManager.getInstance().getProfile(player);

            if(profile == null) return true;

            if(profile.getState() != PlayerState.LOBBY) {
                player.sendMessage(ChatColor.RED + "You are not in the correct state to perform this command.");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null) {
                player.sendMessage(ChatColor.RED + "Could not find that player.");
                return true;
            }

            Profile targetProfile = ProfileManager.getInstance().getProfile(target);

            if(targetProfile == null) {
                player.sendMessage(ChatColor.RED + "Could not find that player.");
                return true;
            }

            if(targetProfile.getState() != PlayerState.LOBBY) {
                player.sendMessage(ChatColor.RED + "That player is currently busy.");
                return true;
            }

            if(!profile.getDuelRequest().containsKey(target.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "That player has not dueled you.");
                return true;
            }

            GameMode game = profile.getDuelRequest().get(target.getUniqueId()).getGame();

            DuelType type = DuelType.valueOf(game.getType().toString());

            SoloDuelManager.getManager().createDuel(player.getUniqueId(), target.getUniqueId(), type, game);

            profile.getDuelRequest().remove(target.getUniqueId());

        }

        return true;
    }
}
