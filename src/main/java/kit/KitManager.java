package kit;

import gametype.GameMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.Yaml;
import practice.FreePractice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KitManager {

    private File file = null;

    private static KitManager km;

    private KitManager() {}

    public static KitManager getManager() {
        if (km == null)
            km = new KitManager();

        return km;
    }

    public void createKit(Player player, GameMode game, String name, boolean normal) {

        file = new File(FreePractice.getInstance().getDataFolder() + "/Kits/" + player.getUniqueId().toString(), game.getName() + ".yml");

        if(!file.exists()) {

            file = new File(FreePractice.getInstance().getDataFolder() + "/Kits/" + player.getUniqueId().toString(), game.getName() + ".yml");

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            config.createSection("normal.size");
            config.createSection("bard.size");

            config.set("normal.size", 0);
            config.set("bard.size", 0);

            try {
                config.save(file);
            } catch(Exception e) {
               e.printStackTrace();
            }


            if(normal) {

                config.createSection("normal." + name + ".inv");
                config.createSection("normal." + name + ".armor");

                config.set("normal." + name + ".inv", player.getInventory().getContents());
                config.set("normal." + name + ".armor", player.getInventory().getArmorContents());

                config.set("normal.size", 1);

                try {
                    config.save(file);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                player.sendMessage(ChatColor.GREEN + "New diamond kit created: " + net.md_5.bungee.api.ChatColor.YELLOW + name);

            } else {
                config.createSection("bard." + name + ".inv");
                config.createSection("bard." + name + ".armor");

                config.set("bard." + name + ".inv", player.getInventory().getContents());
                config.set("bard." + name + ".armor", player.getInventory().getArmorContents());

                config.set("bard.size", 1);

                try {
                    config.save(file);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                player.sendMessage(ChatColor.GREEN + "New bard kit created: " + net.md_5.bungee.api.ChatColor.YELLOW + name);

            }

        } else {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if( doesKitExist(name, game, player, normal)) {
                player.sendMessage(ChatColor.GOLD + "You already have a kit with that name.");
                return;
            }

            if(getKitSize(player, game, normal) >= 8) {
                player.sendMessage(ChatColor.GOLD + "You have reached the maximum amount of custom kits (8).");
                return;
            }

            if(normal) {

                config.createSection("normal." + name + ".inv");
                config.createSection("normal." + name + ".armor");

                config.set("normal." + name + ".inv", player.getInventory().getContents());
                config.set("normal." + name + ".armor", player.getInventory().getArmorContents());

                int size = getKitSize(player, game, normal) + 1;

                config.set("normal.size", size);

                try {
                    config.save(file);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                player.sendMessage(ChatColor.GREEN + "New diamond kit created: " + net.md_5.bungee.api.ChatColor.YELLOW + name);

            } else {
                config.createSection("bard." + name + ".inv");
                config.createSection("bard." + name + ".armor");

                config.set("bard." + name + ".inv", player.getInventory().getContents());
                config.set("bard." + name + ".armor", player.getInventory().getArmorContents());

                int size = getKitSize(player, game, normal) + 1;

                config.set("bard.size", size);

                try {
                    config.save(file);
                } catch(Exception e) {
                    e.printStackTrace();
                }

                player.sendMessage(ChatColor.GREEN + "New bard kit created: " + net.md_5.bungee.api.ChatColor.YELLOW + name);

            }

        }

    }

    public void deleteKit(Player player, GameMode game, String name, boolean normal) {

        file = new File(FreePractice.getInstance().getDataFolder() + "/Kits/" + player.getUniqueId().toString(), game.getName() + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(normal) {

                if(!doesKitExist(name, game, player, normal)) {
                    player.sendMessage(ChatColor.RED + "That kit does not exist.");
                    return;
                }

                config.set("normal." + name, null);
                config.set("normal.size", getKitSize(player, game, normal) - 1);

                try {
                    config.save(file);
                    player.sendMessage(ChatColor.RED + "Deleted a diamond kit named " + ChatColor.YELLOW + name);
                } catch(Exception e) {
                    e.printStackTrace();
                }


            } else {

                if(!doesKitExist(name, game, player, normal)) {
                    player.sendMessage(ChatColor.RED + "That kit does not exist.");
                    return;
                }

                config.set("bard." + name, null);
                config.set("bard.size", getKitSize(player, game, normal) - 1);

                try {
                    config.save(file);
                    player.sendMessage(ChatColor.RED + "Deleted a bard kit named " + ChatColor.YELLOW + name);
                } catch(Exception e) {
                    e.printStackTrace();
                }


            }
        }


    }

    public boolean doesKitExist(String name, GameMode game, Player player, boolean type) {

        for(String str : getKits(player, game, type)) {

            System.out.println(str);

            if(str.equalsIgnoreCase(name)) {

                return true;
            }
        }

        return false;

    }

    public int getKitSize(Player player, GameMode game, boolean type) {

        file = new File(FreePractice.getInstance().getDataFolder() + "/Kits/" + player.getUniqueId().toString(), game.getName() + ".yml");

        int size = 0;

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(type) {
                size = config.getInt("normal.size");
            } else {
                size = config.getInt("bard.size");
            }

        }

        return size;

    }

    public ArrayList<String> getKits(Player player, GameMode game, boolean type) {

        ArrayList<String> kits = new ArrayList<String>();

        file = new File(FreePractice.getInstance().getDataFolder() + "/Kits/" + player.getUniqueId().toString(), game.getName() + ".yml");

        if (file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(type) {

                for(String str : config.getConfigurationSection("normal").getKeys(false)) {

                    if(str.equalsIgnoreCase("size")) continue;

                    kits.add(str);

                }
            } else {

                for(String str : config.getConfigurationSection("bard").getKeys(false)) {

                    if(str.equalsIgnoreCase("size")) continue;

                    kits.add(str);

                }
            }
        }

        return kits;
    }

    public void loadKits(Player player, GameMode game, boolean type) {

        for(String str : getKits(player, game, type)) {

            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GOLD + str);

            item.setItemMeta(meta);

            player.getInventory().addItem(item);

        }

    }

    public void loadKit(final Player player, final GameMode game, boolean type, String name) {

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FreePractice.getInstance(), new Runnable() {
            public void run() {
                player.getInventory().setContents(game.getInv());
                player.getInventory().setArmorContents(game.getArmor());
            }

        }, 3);

        file = new File(FreePractice.getInstance().getDataFolder() + "/Kits/" + player.getUniqueId().toString(), game.getName() + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            if(type) {

                List<?> inv = config.getList("normal." + name + ".inv");
                List<?> armor = config.getList("normal." + name + ".armor");

                ItemStack[] inventory = inv.toArray(new ItemStack[inv.size()]);
                ItemStack[] gear = armor.toArray(new ItemStack[armor.size()]);

                player.getInventory().clear();
                player.getInventory().setArmorContents(null);

                player.getInventory().setContents(inventory);
                player.getInventory().setArmorContents(gear);

                player.sendMessage(ChatColor.YELLOW + "Loaded in diamond kit:" + ChatColor.BLUE + name);

            } else {

                List<?> inv = config.getList("bard." + name + ".inv");
                List<?> armor = config.getList("bard." + name + ".armor");

                ItemStack[] inventory = inv.toArray(new ItemStack[inv.size()]);
                ItemStack[] gear = armor.toArray(new ItemStack[armor.size()]);

                player.getInventory().clear();
                player.getInventory().setArmorContents(null);

                player.getInventory().setContents(inventory);
                player.getInventory().setArmorContents(gear);

                player.sendMessage(ChatColor.YELLOW + "Loaded in bard kit:" + ChatColor.GOLD + name);

            }
        } else {

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);

            player.getInventory().setContents(game.getInv());
            player.getInventory().setArmorContents(game.getArmor());

            player.sendMessage(ChatColor.YELLOW + "Couldn't find any kits for you, loaded the default kit.");
        }

    }

}
