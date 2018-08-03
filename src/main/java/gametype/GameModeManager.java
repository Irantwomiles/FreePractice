package gametype;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import practice.FreePractice;

public class GameModeManager {

    @Getter
    private static ArrayList<GameMode> games = new ArrayList<GameMode>();

    private File file = null;

    private static GameModeManager gm;

    private GameModeManager() {}

    public static GameModeManager getManager() {
        if (gm == null)
            gm = new GameModeManager();

        return gm;
    }

    public void loadGameModes() {

        file = new File(FreePractice.getInstance().getDataFolder() + "/GameModes");

        if (file.isDirectory()) {

            File[] files = file.listFiles();

            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {

                    file = new File(FreePractice.getInstance().getDataFolder() + "/GameModes", files[i].getName());

                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    String name = config.getString("name");
                    ItemStack display = (ItemStack) config.get("display");

                    boolean edit = config.getBoolean("edit");
                    boolean ranked = config.getBoolean("ranked");

                    GameType type = GameType.valueOf(config.getString("type"));

                    List<?> inv = config.getList("inv");
                    List<?> armor = config.getList("armor");
                    List<?> chest = config.getList("chest");

                    ItemStack[] inventory = inv.toArray(new ItemStack[inv.size()]);
                    ItemStack[] gear = armor.toArray(new ItemStack[armor.size()]);
                    ItemStack[] editor = chest.toArray(new ItemStack[chest.size()]);

                    GameMode game = new GameMode(name, inventory, gear);

                    game.setChest(editor);
                    game.setEditable(edit);
                    game.setDisplay(display);
                    game.setRanked(ranked);
                    game.setType(type);

                    games.add(game);

                }

            }
        }
    }

    public void saveGameModes() {

        if(games.size() > 0) {

            for(GameMode game : games) {

                file = new File(FreePractice.getInstance().getDataFolder() + "/GameModes", game.getName() + ".yml");

                if(file.exists()) {
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    config.set("name", game.getName());
                    config.set("display", game.getDisplay());
                    config.set("edit", game.isEditable());
                    config.set("ranked", game.isRanked());
                    config.set("chest", game.getChest());
                    config.set("type", game.getType().toString());

                    config.set("inv", game.getInv());
                    config.set("armor", game.getArmor());

                    try {
                        config.save(file);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {

                    file = new File(FreePractice.getInstance().getDataFolder() + "/GameModes", game.getName() + ".yml");

                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    config.createSection("name");
                    config.createSection("display");
                    config.createSection("edit");
                    config.createSection("type");
                    config.createSection("ranked");
                    config.createSection("inv");
                    config.createSection("armor");
                    config.createSection("chest");

                    config.set("name", game.getName());
                    config.set("display", game.getDisplay());
                    config.set("edit", game.isEditable());
                    config.set("ranked", game.isRanked());
                    config.set("type", game.getType().toString());
                    config.set("chest", game.getChest());
                    config.set("inv", game.getInv());
                    config.set("armor", game.getArmor());

                    try {
                        config.save(file);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    public void createGameMode(Player player, String name) {

        if(gameModeExists(name)) {
            player.sendMessage(ChatColor.RED + "That game mode already exists.");
            return;
        }

        GameMode game = new GameMode(name, player.getInventory().getContents(), player.getInventory().getArmorContents());

        games.add(game);

        player.sendMessage(ChatColor.GREEN + "Game mode " + name + " has been created.");
    }

    public void deleteGameMode(Player player, String name) {

        if(!gameModeExists(name)) {
            player.sendMessage(ChatColor.RED + "That game mode does not exist");
            return;
        }

        GameMode game = getGameMode(name);

        file = new File(FreePractice.getInstance().getDataFolder() + "/GameModes", game.getName() + ".yml");

        file.delete();

        games.remove(game);

        player.sendMessage(ChatColor.RED + "Deleted game mode " + name);
    }

    public void setRanked(Player player, String name) {

        if(!gameModeExists(name)) {
            player.sendMessage(ChatColor.RED + "That game mode does not exist");
            return;
        }

        GameMode game = getGameMode(name);

        if(game.isRanked()) {
            game.setRanked(false);
        } else {
            game.setRanked(true);
        }

        player.sendMessage(ChatColor.GOLD + "Ranked Mode for game mode " + name + " is now " + ChatColor.GRAY + game.isRanked());
    }

    public void setEditable(Player player, String name) {

        if(!gameModeExists(name)) {
            player.sendMessage(ChatColor.RED + "That game mode does not exist");
            return;
        }

        GameMode game = getGameMode(name);

        if(game.isEditable()) {
            game.setEditable(false);
        } else {
            game.setEditable(true);
        }

        player.sendMessage(ChatColor.GOLD + "Edit mode for game mode " + name + " is now " + ChatColor.GRAY + game.isEditable());
    }

    public GameMode getGameMode(String name) {
        for(GameMode game : games) {
            if(game.getName().equalsIgnoreCase(name)) {
                return game;
            }
        }
        return null;
    }

    public boolean gameModeExists(String name) {
        for(GameMode game : games) {
            if(game.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}