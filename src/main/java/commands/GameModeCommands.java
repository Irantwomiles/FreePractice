package commands;

import gametype.GameMode;
import gametype.GameModeManager;
import gametype.GameType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameModeCommands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("gamemode")) {

            if(!player.hasPermission("freepractice.gamemode")) {
                player.sendMessage("No Permission.");
                return true;
            }

            if(args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/gamemode create <name>");
                player.sendMessage(ChatColor.YELLOW + "/gamemode setgear <name>");
                player.sendMessage(ChatColor.YELLOW + "/gamemode setdisplay <name>");
                player.sendMessage(ChatColor.YELLOW + "/gamemode setranked <name>");
                player.sendMessage(ChatColor.YELLOW + "/gamemode seteditable <name>");
                player.sendMessage(ChatColor.YELLOW + "/gamemode settype <name> <type> (NORMAL, SUMO)");
                player.sendMessage(ChatColor.YELLOW + "/gamemode setchest <name>");
                player.sendMessage(ChatColor.YELLOW + "/gamemode delete <name>");
                return true;
            }

            if(args[0].equalsIgnoreCase("create")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/gamemode create <name>");
                    return true;
                }

                GameModeManager.getManager().createGameMode(player, args[1]);
            }

            if(args[0].equalsIgnoreCase("setgear")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/gamemode setgear <name>");
                    return true;
                }

                GameMode game =  GameModeManager.getManager().getGameMode(args[1]);

                if(game == null) {
                    player.sendMessage(ChatColor.RED + "Could not find a game mode by that name.");
                    return true;
                }

                game.setArmor(player.getInventory().getArmorContents());
                game.setInv(player.getInventory().getContents());

                player.sendMessage(ChatColor.GREEN + "Updated the inventory and armor of game mode " + game.getName());
            }

            if(args[0].equalsIgnoreCase("setdisplay")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/gamemode setdisplay <name>");
                    return true;
                }

                GameMode game =  GameModeManager.getManager().getGameMode(args[1]);

                if(game == null) {
                    player.sendMessage(ChatColor.RED + "Could not find a game mode by that name.");
                    return true;
                }

                if(player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
                    game.setDisplay(player.getItemInHand());
                } else {
                    player.sendMessage(ChatColor.RED + "You must be holding something in your hand.");
                }

                player.sendMessage(ChatColor.GREEN + "Updated the display item of game mode " + game.getName());
            }

            if(args[0].equalsIgnoreCase("setranked")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/gamemode setranked <name>");
                    return true;
                }

                GameMode game =  GameModeManager.getManager().getGameMode(args[1]);

                if(game == null) {
                    player.sendMessage(ChatColor.RED + "Could not find a game mode by that name.");
                    return true;
                }

                GameModeManager.getManager().setRanked(player, game.getName());
            }

            if(args[0].equalsIgnoreCase("seteditable")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/gamemode seteditable <name>");
                    return true;
                }

                GameMode game =  GameModeManager.getManager().getGameMode(args[1]);

                if(game == null) {
                    player.sendMessage(ChatColor.RED + "Could not find a game mode by that name.");
                    return true;
                }

                GameModeManager.getManager().setEditable(player, game.getName());
            }

            if(args[0].equalsIgnoreCase("settype")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/gamemode settype <name> <type>");
                    return true;
                }

                GameMode game =  GameModeManager.getManager().getGameMode(args[1]);

                if(game == null) {
                    player.sendMessage(ChatColor.RED + "Could not find a game mode by that name.");
                    return true;
                }

                try {
                    game.setType(GameType.valueOf(args[2]));
                    player.sendMessage(ChatColor.GOLD + "Game mode type set to " + game.getType().toString());
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Error while setting game mode type");
                }
            }

            if(args[0].equalsIgnoreCase("delete")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/gamemode delete <name>");
                    return true;
                }

                GameMode game =  GameModeManager.getManager().getGameMode(args[1]);

                if(game == null) {
                    player.sendMessage(ChatColor.RED + "Could not find a game mode by that name.");
                    return true;
                }

                GameModeManager.getManager().deleteGameMode(player, game.getName());
            }

            if(args[0].equalsIgnoreCase("setchest")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/gamemode setchest <name>");
                    return true;
                }

                GameMode game =  GameModeManager.getManager().getGameMode(args[1]);

                if(game == null) {
                    player.sendMessage(ChatColor.RED + "Could not find a game mode by that name.");
                    return true;
                }

                Inventory inv = Bukkit.createInventory(null, 54, game.getName() + " Update");

                for(int i = 0; i < game.getChest().length; i++) {
                    inv.setItem(i, game.getChest()[i]);
                }

                player.openInventory(inv);

            }

        }

        return true;
    }
}
