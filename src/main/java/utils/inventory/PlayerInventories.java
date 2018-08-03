package utils.inventory;

import gametype.GameMode;
import gametype.GameModeManager;
import kit.KitManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventories {

    public void duelReqest(Player player) {

        Inventory inv = null;

        if(GameModeManager.getGames().size() <= 9) {
            inv = Bukkit.createInventory(null, 9, ChatColor.RED + "Game Modes");
        } else if(GameModeManager.getGames().size() > 9 && GameModeManager.getGames().size() <= 18) {
            inv = Bukkit.createInventory(null, 18, ChatColor.RED + "Game Modes");
        } else if(GameModeManager.getGames().size() > 18 && GameModeManager.getGames().size() <= 27) {
            inv = Bukkit.createInventory(null, 27, ChatColor.RED + "Game Modes");
        } else if(GameModeManager.getGames().size() > 27 && GameModeManager.getGames().size() <= 36) {
            inv = Bukkit.createInventory(null, 36, ChatColor.RED + "Game Modes");
        } else if(GameModeManager.getGames().size() > 36 && GameModeManager.getGames().size() <= 45) {
            inv = Bukkit.createInventory(null, 45, ChatColor.RED + "Game Modes");
        } else if(GameModeManager.getGames().size() > 45 && GameModeManager.getGames().size() <= 54) {
            inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Game Modes");
        }

        for(int i = 0; i < GameModeManager.getGames().size(); i++) {

            GameMode game = GameModeManager.getGames().get(i);

            ItemStack item = game.getDisplay();
            ItemMeta dmeta = item.getItemMeta();

            dmeta.setDisplayName(ChatColor.GREEN + game.getName());

            item.setItemMeta(dmeta);
            inv.setItem(i, item);
        }

        player.openInventory(inv);
    }

    public void kitEditorGameModes(Player player) {

        Inventory inv = null;

        if(GameModeManager.getGames().size() <= 9) {
            inv = Bukkit.createInventory(null, 9, ChatColor.RED + "Select Game Mode");
        } else if(GameModeManager.getGames().size() > 9 && GameModeManager.getGames().size() <= 18) {
            inv = Bukkit.createInventory(null, 18, ChatColor.RED + "Select Game Mode");
        } else if(GameModeManager.getGames().size() > 18 && GameModeManager.getGames().size() <= 27) {
            inv = Bukkit.createInventory(null, 27, ChatColor.RED + "Select Game Mode");
        } else if(GameModeManager.getGames().size() > 27 && GameModeManager.getGames().size() <= 36) {
            inv = Bukkit.createInventory(null, 36, ChatColor.RED + "Select Game Mode");
        } else if(GameModeManager.getGames().size() > 36 && GameModeManager.getGames().size() <= 45) {
            inv = Bukkit.createInventory(null, 45, ChatColor.RED + "Select Game Mode");
        } else if(GameModeManager.getGames().size() > 45 && GameModeManager.getGames().size() <= 54) {
            inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Select Game Mode");
        }

        for(int i = 0; i < GameModeManager.getGames().size(); i++) {

            GameMode game = GameModeManager.getGames().get(i);

            if(game.isEditable()) {
                ItemStack item = game.getDisplay();
                ItemMeta dmeta = item.getItemMeta();

                dmeta.setDisplayName(ChatColor.GREEN + game.getName());

                item.setItemMeta(dmeta);
                inv.setItem(i, item);
            }

        }

        player.openInventory(inv);
    }

    public void editKits(Player player, GameMode game) {

        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.BLUE + "Edit Kits");

        ItemStack normal = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack bard = new ItemStack(Material.GOLD_CHESTPLATE);

        ItemMeta nmeta = normal.getItemMeta();
        ItemMeta bmeta = bard.getItemMeta();

        nmeta.setDisplayName(ChatColor.BLUE + "Diamond Kit");
        bmeta.setDisplayName(ChatColor.GOLD + "Bard Kit");

        normal.setItemMeta(nmeta);
        bard.setItemMeta(bmeta);

        inv.setItem(4, normal);
        inv.setItem(22, bard);

        for(int i = 0; i < KitManager.getManager().getKits(player, game, true).size(); i++) {

            String str = KitManager.getManager().getKits(player, game, true).get(i);

            ItemStack kit = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta kmeta = kit.getItemMeta();

            kmeta.setDisplayName(ChatColor.GREEN + str);

            kit.setItemMeta(kmeta);

            inv.setItem(i + 9, kit);

        }

        for(int i = 0; i < KitManager.getManager().getKits(player, game, false).size(); i++) {

            String str = KitManager.getManager().getKits(player, game, false).get(i);

            ItemStack kit = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta kmeta = kit.getItemMeta();

            kmeta.setDisplayName(ChatColor.GREEN + str);

            kit.setItemMeta(kmeta);

            inv.setItem(i + 27, kit);
        }

        //DIAMOND KIT

        for(int i = 0; i < 4; i++) {

            ItemStack fill = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            ItemMeta fmeta = fill.getItemMeta();

            fmeta.setDisplayName(ChatColor.BLUE + "Save Diamond Kit");

            fill.setItemMeta(fmeta);

            inv.setItem(i, fill);
        }

        for(int i = 0; i < 4; i++) {

            ItemStack fill = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta fmeta = fill.getItemMeta();

            fmeta.setDisplayName(ChatColor.RED + "Delete Diamond Kit");

            fill.setItemMeta(fmeta);

            inv.setItem(i + 5, fill);
        }

        //BARD KIT

        for(int i = 0; i < 4; i++) {

            ItemStack fill = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            ItemMeta fmeta = fill.getItemMeta();

            fmeta.setDisplayName(ChatColor.BLUE + "Save Bard Kit");

            fill.setItemMeta(fmeta);

            inv.setItem(i + 18, fill);
        }

        for(int i = 0; i < 4; i++) {

            ItemStack fill = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta fmeta = fill.getItemMeta();

            fmeta.setDisplayName(ChatColor.RED + "Delete Bard Kit");

            fill.setItemMeta(fmeta);

            inv.setItem(i + 23, fill);
        }

        player.openInventory(inv);

    }

}
