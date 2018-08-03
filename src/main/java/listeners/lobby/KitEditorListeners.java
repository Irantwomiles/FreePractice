package listeners.lobby;
import gametype.GameMode;
import gametype.GameModeManager;
import kit.KitManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import utils.SpawnLocations;
import utils.inventory.PlayerInventories;
import utils.profile.PlayerState;
import utils.profile.Profile;
import utils.profile.ProfileManager;

import java.util.HashMap;
import java.util.UUID;

public class KitEditorListeners implements Listener {

    @Getter
    private HashMap<UUID, GameMode> editor = new HashMap<UUID, GameMode>();

    @Getter
    private HashMap<UUID, Boolean> save = new HashMap<UUID, Boolean>();

    @Getter
    private HashMap<UUID, Boolean> delete = new HashMap<UUID, Boolean>();

    private PlayerInventories inventories = new PlayerInventories();

    private SpawnLocations locations = new SpawnLocations();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        Profile profile = ProfileManager.getInstance().getProfile(player);

        if (event.getAction() == null) {
            return;
        }

        if (player.getItemInHand() == null) {
            return;
        }

        if (!player.getItemInHand().hasItemMeta()) {
            return;
        }

        if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
            return;
        }


        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if(player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Kit Editor")) {

                if(profile.getState() != PlayerState.LOBBY) {
                    return;
                }

                event.setCancelled(true);

                inventories.kitEditorGameModes(player);

            }

        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if(player.getItemInHand() != null && player.getItemInHand().getType() == Material.ENCHANTED_BOOK) {

                String name = ChatColor.stripColor(player.getItemInHand().getItemMeta().getDisplayName());



            }

        }

    }

    @EventHandler
    public void onEdit(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        Profile profile = ProfileManager.getInstance().getProfile(player);

        if (event.getAction() == null) {
            return;
        }

        if(event.getClickedBlock() == null) {
            return;
        }

        if(profile == null) return;

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.ANVIL) {

            if(profile.getState() == PlayerState.EDIT) {

                if(editor.containsKey(player.getUniqueId())) {

                    GameMode game = editor.get(player.getUniqueId());

                    event.setCancelled(true);

                    inventories.editKits(player, game);

                }
            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CHEST) {

            if(profile.getState() == PlayerState.EDIT) {

                if(editor.containsKey(player.getUniqueId())) {

                    GameMode game = editor.get(player.getUniqueId());

                    Inventory inv = Bukkit.createInventory(null, 54, game.getName());

                    for(int i = 0; i < game.getChest().length; i++) {
                        inv.setItem(i, game.getChest()[i]);
                    }

                    event.setCancelled(true);

                    player.openInventory(inv);

                }
            }
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Block b = event.getClickedBlock();

            if ((b.getType() == Material.SIGN_POST) || (b.getType() == Material.WALL_SIGN)) {

                Sign s = (Sign) b.getState();

                if(profile.getState() == PlayerState.EDIT) {

                    if (s.getLine(0).equalsIgnoreCase("[Right Click]")) {

                        if(save.containsKey(player.getUniqueId()) || delete.containsKey(player.getUniqueId())) {
                            player.sendMessage(ChatColor.RED + "You must finish editing before you can leave.");
                            return;
                        }

                        profile.setState(PlayerState.LOBBY);

                        locations.teleportSpawn(player);

                        if(editor.containsKey(player.getUniqueId())) editor.remove(player.getUniqueId());

                        if(save.containsKey(player.getUniqueId())) save.remove(player.getUniqueId());

                        if(delete.containsKey(player.getUniqueId())) delete.remove(player.getUniqueId());


                    }
                }
            }
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if(!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        Profile profile = ProfileManager.getInstance().getProfile(player);

        if(profile == null) return;

        if(event.getClickedInventory() == null) {
            return;
        }

        if(event.getClickedInventory().getTitle() == null) {
            return;
        }

        if(event.getCurrentItem() == null) {
            return;
        }

        if(!event.getCurrentItem().hasItemMeta()) {
            return;
        }

        if(event.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return;
        }

        if(event.getInventory().getTitle().equals(ChatColor.RED + "Select Game Mode")) {

            String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

            GameMode game = GameModeManager.getManager().getGameMode(name);

            if(game == null) return;

            if(!editor.containsKey(player.getUniqueId())) {

                editor.put(player.getUniqueId(), game);

                locations.teleportEditor(player);

                player.getInventory().clear();

                profile.setState(PlayerState.EDIT);

                event.setCancelled(true);

                player.closeInventory();
            }

        }

        if(event.getInventory().getTitle().equals(ChatColor.BLUE + "Edit Kits")) {

            if(!editor.containsKey(player.getUniqueId())) {
                player.closeInventory();
                return;
            }

            event.setCancelled(true);

            if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Save Diamond Kit")) {

                save.put(player.getUniqueId(), true);

                player.closeInventory();

                player.sendMessage(ChatColor.GREEN + "Type the name of your new kit in chat.");

            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Save Bard Kit")) {

                save.put(player.getUniqueId(), false);

                player.closeInventory();

                player.sendMessage(ChatColor.GREEN + "Type the name of your new kit in chat.");

            }  else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Delete Diamond Kit")) {

                delete.put(player.getUniqueId(), true);

                player.closeInventory();

                player.sendMessage(ChatColor.RED + "Type the name of the kit you want to delete in chat.");

            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Delete Bard Kit")) {

                delete.put(player.getUniqueId(), false);

                player.closeInventory();

                player.sendMessage(ChatColor.RED + "Type the name of the kit you want to delete in chat.");
            }

        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        if(event.getPlayer() instanceof Player) {

            Player player = (Player) event.getPlayer();

            if(!editor.containsKey(player.getUniqueId())) return;

            GameMode game = editor.get(player.getUniqueId());

            if(event.getInventory().getTitle().equals(game.getName() + " Update")) {

                game.setChest(event.getInventory().getContents());

                player.sendMessage(ChatColor.GREEN + "Updated Editor chest for " + game.getName());
            }

        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if(save.containsKey(player.getUniqueId()) && editor.containsKey(player.getUniqueId())) {

            boolean normal = save.get(player.getUniqueId());

            GameMode game = editor.get(player.getUniqueId());

            String name = event.getMessage().replace(" ", "_");

            event.setCancelled(true);

            if(normal) {
                KitManager.getManager().createKit(player, game, name, normal);
            } else {
                KitManager.getManager().createKit(player, game, name, normal);
            }

            save.remove(player.getUniqueId());

            return;

        }

        if(delete.containsKey(player.getUniqueId()) && editor.containsKey(player.getUniqueId())) {

            boolean normal = delete.get(player.getUniqueId());

            GameMode game = editor.get(player.getUniqueId());

            String name = event.getMessage().replace(" ", "_");

            event.setCancelled(true);

            if(normal) {
                KitManager.getManager().deleteKit(player, game, name, normal);
            } else {
                KitManager.getManager().deleteKit(player, game, name, normal);
            }

            delete.remove(player.getUniqueId());

        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if(editor.containsKey(player.getUniqueId())) editor.remove(player.getUniqueId());

        if(save.containsKey(player.getUniqueId())) save.remove(player.getUniqueId());

        if(delete.containsKey(player.getUniqueId())) delete.remove(player.getUniqueId());

    }

}
