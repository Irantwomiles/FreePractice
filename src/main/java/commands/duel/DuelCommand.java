package commands.duel;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import utils.inventory.PlayerInventories;
import utils.profile.PlayerState;
import utils.profile.Profile;
import utils.profile.ProfileManager;

import java.util.HashMap;
import java.util.UUID;

public class DuelCommand implements CommandExecutor {

    @Getter
    private static HashMap<UUID, UUID> duelRequest = new HashMap<UUID, UUID>();

    private PlayerInventories inventories = new PlayerInventories();

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("duel")) {

            if(args.length < 1) {
                player.sendMessage(ChatColor.RED + "/duel <player>");
                return true;
            }

            Profile profile = ProfileManager.getInstance().getProfile(player);

            if(profile == null) return true;

            if(profile.getState() != PlayerState.LOBBY) {
                player.sendMessage(ChatColor.RED + "You are not in the correct state to perform this command.");
                return true;
            }

            if(args[0].equalsIgnoreCase(player.getName())) {
                player.sendMessage(ChatColor.RED + "You can't duel yourself.");
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

            duelRequest.put(player.getUniqueId(), target.getUniqueId());

            inventories.duelReqest(player);

        }

        return true;
    }
}
