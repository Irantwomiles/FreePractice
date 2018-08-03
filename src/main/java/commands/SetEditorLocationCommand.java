package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import practice.FreePractice;

public class SetEditorLocationCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String args, String[] label) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("seteditor")) {

            if(!player.hasPermission("freepractice.seteditor")) return true;

            double x = player.getLocation().getX();
            double y = player.getLocation().getY();
            double z = player.getLocation().getZ();

            float pitch = player.getLocation().getPitch();
            float yaw = player.getLocation().getYaw();

            String world = player.getLocation().getWorld().getName();

            FreePractice.getInstance().getConfig().set("editor.x", x);
            FreePractice.getInstance().getConfig().set("editor.y", y);
            FreePractice.getInstance().getConfig().set("editor.z", z);
            FreePractice.getInstance().getConfig().set("editor.pitch", pitch);
            FreePractice.getInstance().getConfig().set("editor.yaw", yaw);
            FreePractice.getInstance().getConfig().set("editor.world", world);

            FreePractice.getInstance().saveConfig();

            player.sendMessage(ChatColor.YELLOW + "Editor Spawn has been set!");
        }

        return true;
    }


}
