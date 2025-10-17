package io.github.tavstaldev.antiCreative.listeners;

import io.github.tavstaldev.antiCreative.AntiCreative;
import io.github.tavstaldev.antiCreative.utils.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Listener class for handling player-related events in the AntiCreative plugin.
 */
public class PlayerEventListener implements Listener {

    /**
     * Handles the PlayerJoinEvent to enforce game mode restrictions.
     *
     * @param event The PlayerJoinEvent triggered when a player joins the server.
     */
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        if (PlayerUtil.isAllowed(event.getPlayer()))
            return;

        if (PlayerUtil.isInDisabledWorld(event.getPlayer()))
            return;

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            AntiCreative.Logger().Warn("Player " + event.getPlayer().getName() + " was in creative mode on join. Changed to survival.");

            if (AntiCreative.Config().banOnJoin)
                PlayerUtil.banPlayer(event.getPlayer());
        }
    }

    /**
     * Handles the PlayerTeleportEvent to enforce game mode restrictions.
     *
     * @param event The PlayerTeleportEvent triggered when a player teleports.
     */
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (PlayerUtil.isAllowed(event.getPlayer()))
            return;

        if (PlayerUtil.isInDisabledWorld(event.getPlayer()))
            return;

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            AntiCreative.Logger().Warn("Player " + event.getPlayer().getName() + " was in creative mode on teleport. Changed to survival.");

            if (AntiCreative.Config().banOnTeleport)
                PlayerUtil.banPlayer(event.getPlayer());
        }
    }

    /**
     * Handles the PlayerChangedWorldEvent to enforce game mode restrictions.
     *
     * @param event The PlayerChangedWorldEvent triggered when a player changes worlds.
     */
    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        if (PlayerUtil.isAllowed(event.getPlayer()))
            return;

        if (PlayerUtil.isInDisabledWorld(event.getPlayer()))
            return;

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            AntiCreative.Logger().Warn("Player " + event.getPlayer().getName() + " was in creative mode on world change. Changed to survival.");

            if (AntiCreative.Config().banOnWorldChange)
                PlayerUtil.banPlayer(event.getPlayer());
        }
    }

    /**
     * Handles the PlayerGameModeChangeEvent to enforce game mode restrictions.
     *
     * @param event The PlayerGameModeChangeEvent triggered when a player attempts to change their game mode.
     */
    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() != GameMode.CREATIVE)
            return;

        if (PlayerUtil.isAllowed(event.getPlayer()))
            return;

        if (PlayerUtil.isInDisabledWorld(event.getPlayer()))
            return;

        event.setCancelled(true);
        // Make sure the player is in survival mode
        event.getPlayer().setGameMode(GameMode.SURVIVAL);

        if (AntiCreative.Config().banOnGamemodeChange)
            PlayerUtil.banPlayer(event.getPlayer());
    }
}
