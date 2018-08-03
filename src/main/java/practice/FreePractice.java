package practice;

import arena.ArenaManager;
import commands.GameModeCommands;
import commands.SetEditorLocationCommand;
import commands.SetSpawnLocationCommand;
import commands.arena.ArenaCommands;
import commands.duel.AcceptCommand;
import commands.duel.DuelCommand;
import gametype.GameModeManager;
import listeners.duel.DuelRequestListeners;
import listeners.duel.DuelStartListener;
import listeners.lobby.ItemListeners;
import listeners.lobby.JoinListener;
import listeners.lobby.KitEditorListeners;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import run.DuelRunnable;
import utils.profile.ProfileManager;

public class FreePractice extends JavaPlugin {

    @Getter
    public static FreePractice instance;

    private DuelRunnable duelRunnable = new DuelRunnable();

    public void onEnable() {

        instance = this;

        registerCommands();
        registerListeners();

        ArenaManager.getManager().loadArenas();
        GameModeManager.getManager().loadGameModes();

        duelRunnable.runTaskTimer(this, 20L, 20L);

    }

    public void onDisable() {

        ArenaManager.getManager().saveArenas();
        GameModeManager.getManager().saveGameModes();

    }

    private void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new ProfileManager(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DuelRequestListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new KitEditorListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DuelStartListener(), this);
    }

    private void registerCommands() {
        getCommand("arena").setExecutor(new ArenaCommands());
        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
        getCommand("gamemode").setExecutor(new GameModeCommands());
        getCommand("seteditor").setExecutor(new SetEditorLocationCommand());
        getCommand("setspawn").setExecutor(new SetSpawnLocationCommand());
    }


}
