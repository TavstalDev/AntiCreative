package io.github.tavstaldev.antiCreative;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class AntiCreative extends JavaPlugin implements Listener {
    private String banCommand = "ban %player% You are not allowed to use creative mode!";
    private final Set<String> allowedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        allowedPlayers.addAll(this.getConfig().getStringList("allowed-players"));
        banCommand = this.getConfig().getString("ban-command", banCommand);
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() != GameMode.CREATIVE)
            return;

        if (allowedPlayers.isEmpty())
        {
            this.getLogger().warning("No players are allowed to use creative mode. Please add players to the config file.");
            return;
        }

        if (allowedPlayers.contains(event.getPlayer().getName()))
            return;

        String command = banCommand.replace("%player%", event.getPlayer().getName());
        this.getServer().dispatchCommand(this.getServer().getConsoleSender(), command);
    }
}
