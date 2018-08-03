package utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import practice.FreePractice;

public class PlayerItems {

    public void spawnItems(final Player player) {

        final ItemStack diamond = new ItemStack(Material.DIAMOND_SWORD);
        final ItemStack emerald = new ItemStack(Material.EMERALD);
        final ItemStack party = new ItemStack(Material.NAME_TAG);
        final ItemStack edit = new ItemStack(Material.BOOK);
        final ItemStack options = new ItemStack(Material.SKULL_ITEM, 1);

        ItemMeta rmeta = diamond.getItemMeta();
        ItemMeta emMeta = emerald.getItemMeta();
        ItemMeta partyMeta = party.getItemMeta();
        ItemMeta emeta = edit.getItemMeta();
        ItemMeta ometa = options.getItemMeta();

        rmeta.setDisplayName(ChatColor.YELLOW + "Queues");
        emMeta.setDisplayName(ChatColor.AQUA + "Events");
        partyMeta.setDisplayName(ChatColor.RED + "Party");
        emeta.setDisplayName(ChatColor.BLUE + "Kit Editor");
        ometa.setDisplayName(ChatColor.GOLD + "Options");

        diamond.setItemMeta(rmeta);
        emerald.setItemMeta(emMeta);
        party.setItemMeta(partyMeta);
        edit.setItemMeta(emeta);
        options.setItemMeta(ometa);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FreePractice.getInstance(), new Runnable() {

            public void run() {

                player.getInventory().clear();
                player.getInventory().setArmorContents(null);

                player.getInventory().setItem(0, diamond);
                player.getInventory().setItem(1, emerald);
                player.getInventory().setItem(4, party);
                player.getInventory().setItem(8, edit);
                player.getInventory().setItem(7, options);

                player.updateInventory();

            }

        }, 2);

    }


}
