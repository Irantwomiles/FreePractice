package commands.arena;

import arena.Arena;
import arena.ArenaManager;
import arena.ArenaType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("arena")) {

            if(!player.hasPermission("freepractice.arena")) {
                player.sendMessage("No Permission.");
                return true;
            }

            if(args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/arena create <name>");
                player.sendMessage(ChatColor.YELLOW + "/arena setloc1 <name>");
                player.sendMessage(ChatColor.YELLOW + "/arena setloc2 <name>");
                player.sendMessage(ChatColor.YELLOW + "/arena setkothloc1 <name>");
                player.sendMessage(ChatColor.YELLOW + "/arena setkothloc2 <name>");
                player.sendMessage(ChatColor.YELLOW + "/arena settype <name> <type> (NORMAL, KOTH, SUMO)");
                player.sendMessage(ChatColor.YELLOW + "/arena delete <name>");
                return true;
            }

            if(args[0].equalsIgnoreCase("create")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/arena create <name>");
                    return true;
                }

               if(!ArenaManager.getManager().doesArenaExist(args[1])) {

                    Arena arena = new Arena(args[1]);

                    ArenaManager.getArenas().add(arena);

                    player.sendMessage(ChatColor.GREEN + "You have just created an arena named " + arena.getName());

               } else {
                   player.sendMessage(ChatColor.RED + "A arena by that name already exists.");
               }


            }

            if(args[0].equalsIgnoreCase("setloc1")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/arena setloc1 <name>");
                    return true;
                }

                if(ArenaManager.getManager().doesArenaExist(args[1])) {

                    Arena arena =  ArenaManager.getManager().getArenaByName(args[1]);

                    if(arena != null) {

                        arena.setLoc1(player.getLocation());

                        player.sendMessage(ChatColor.GOLD + "You have just set location 1 for " + arena.getName());
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "A arena by that name does not exists.");
                }

            }

            if(args[0].equalsIgnoreCase("setloc2")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/arena setloc2 <name>");
                    return true;
                }

                if(ArenaManager.getManager().doesArenaExist(args[1])) {

                    Arena arena =  ArenaManager.getManager().getArenaByName(args[1]);

                    if(arena != null) {

                        arena.setLoc2(player.getLocation());

                        player.sendMessage(ChatColor.GOLD + "You have just set location 2 for " + arena.getName());
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "A arena by that name does not exists.");
                }

            }

            if(args[0].equalsIgnoreCase("setkothloc1")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/arena setkothloc1 <name>");
                    return true;
                }

                if(ArenaManager.getManager().doesArenaExist(args[1])) {

                    Arena arena =  ArenaManager.getManager().getArenaByName(args[1]);

                    if(arena != null) {

                        arena.setKoth1(player.getLocation());

                        player.sendMessage(ChatColor.GOLD + "You have just set koth location 1 for " + arena.getName());
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "A arena by that name does not exists.");
                }

            }

            if(args[0].equalsIgnoreCase("setkothloc2")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.YELLOW + "/arena setloc2 <name>");
                    return true;
                }

                if(ArenaManager.getManager().doesArenaExist(args[1])) {

                    Arena arena =  ArenaManager.getManager().getArenaByName(args[1]);

                    if(arena != null) {

                        arena.setKoth2(player.getLocation());

                        player.sendMessage(ChatColor.GOLD + "You have just set koth location 2 for " + arena.getName());
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "A arena by that name does not exists.");
                }

            }

            if(args[0].equalsIgnoreCase("settype")) {

                if(args.length < 3) {
                    player.sendMessage(ChatColor.YELLOW + "/arena settype <name> <type>");
                    return true;
                }

                if(ArenaManager.getManager().doesArenaExist(args[1])) {

                    Arena arena =  ArenaManager.getManager().getArenaByName(args[1]);

                    if(arena != null) {

                        try {
                            arena.setType(ArenaType.valueOf(args[2]));
                            player.sendMessage(ChatColor.GOLD + "Arena type set to " + arena.getType().toString());
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "Error while setting arena type");
                        }

                    }

                } else {
                    player.sendMessage(ChatColor.RED + "A arena by that name does not exists.");
                }

            }


        }

        return true;
    }
}
