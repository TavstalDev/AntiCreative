package io.github.tavstaldev.antiCreative.listeners;

import io.github.tavstaldev.antiCreative.AntiCreative;
import io.github.tavstaldev.antiCreative.utils.ItemUtil;
import io.github.tavstaldev.antiCreative.utils.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

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
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (PlayerUtil.isAllowed(event.getPlayer()))
            return;

        if (PlayerUtil.isInDisabledWorld(event.getPlayer()))
            return;

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            AntiCreative.logger().warn("Player " + event.getPlayer().getName() + " was in creative mode on join. Changed to survival.");

            if (AntiCreative.config().antiCreativeBanOnJoin)
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
            AntiCreative.logger().warn("Player " + event.getPlayer().getName() + " was in creative mode on teleport. Changed to survival.");

            if (AntiCreative.config().antiCreativeBanOnTeleport)
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
            AntiCreative.logger().warn("Player " + event.getPlayer().getName() + " was in creative mode on world change. Changed to survival.");

            if (AntiCreative.config().antiCreativeBanOnWorldChange)
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

        if (AntiCreative.config().antiCreativeBanOnGamemodeChange)
            PlayerUtil.banPlayer(event.getPlayer());
    }

    /**
     * Handles the PlayerInteractEvent to prevent certain interactions based on the plugin's anti-abuse configuration.
     * <br
     * This method enforces restrictions on player interactions, such as opening containers or using blacklisted items,
     * if the anti-abuse feature is enabled in the plugin's configuration. Players with the bypass permission are exempt
     * from these restrictions.
     *
     * @param event The PlayerInteractEvent triggered when a player interacts with an object or item.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Retrieve the plugin's configuration
        var config = AntiCreative.config();

        // Check if anti-abuse is disabled or if the player has the bypass permission
        if (!config.antiAbuseEnabled || event.getPlayer().hasPermission(config.antiAbuseBypassPermission))
            return;

        // Prevent players from opening containers if the feature is enabled and the clicked block is a container
        if (event.getClickedBlock() != null && config.antiAbusePreventOpeningContainers && ItemUtil.isContainer(event.getClickedBlock().getType())) {
            event.setCancelled(true);
            return;
        }

        // Prevent players from interacting with blacklisted items
        if (event.getItem() != null && config.antiAbuseInteractionBlacklist.contains(event.getItem().getType())) {
            event.setCancelled(true);
            return;
        }
    }

}
