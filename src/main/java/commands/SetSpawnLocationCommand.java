package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import practice.FreePractice;

public class SetSpawnLocationCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String args, String[] label) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("setspawn")) {

            if(!player.hasPermission("practice.admin")) return true;

            double x = player.getLocation().getX();
            double y = player.getLocation().getY();
            double z = player.getLocation().getZ();

            float pitch = player.getLocation().getPitch();
            float yaw = player.getLocation().getYaw();

            String world = player.getLocation().getWorld().getName();

            FreePractice.getInstance().getConfig().set("spawn.x", x);
            FreePractice.getInstance().getConfig().set("spawn.y", y);
            FreePractice.getInstance().getConfig().set("spawn.z", z);
            FreePractice.getInstance().getConfig().set("spawn.pitch", pitch);
            FreePractice.getInstance().getConfig().set("spawn.yaw", yaw);
            FreePractice.getInstance().getConfig().set("spawn.world", world);

            FreePractice.getInstance().saveConfig();

            player.sendMessage(ChatColor.YELLOW + "Spawn has been set!");
        }

        return true;
    }

}
