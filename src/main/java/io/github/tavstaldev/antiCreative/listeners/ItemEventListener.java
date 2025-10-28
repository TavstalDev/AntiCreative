package io.github.tavstaldev.antiCreative.listeners;

import io.github.tavstaldev.antiCreative.AntiCreative;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Listener class to handle item-related events, such as item drops and projectile launches,
 * with restrictions based on the plugin's anti-abuse configuration.
 */
public class ItemEventListener implements Listener {

    /**
     * Handles the PlayerDropItemEvent to prevent players from throwing items if the anti-abuse
     * feature is enabled and the restriction is configured.
     * <br/>
     * This method cancels the event and removes the dropped item if the player does not have
     * the bypass permission.
     *
     * @param event The PlayerDropItemEvent triggered when a player drops an item.
     */
    @EventHandler
    public void onItemThrown(PlayerDropItemEvent event) {
        var config = AntiCreative.config();
        if (!config.antiAbuseEnabled || !config.antiAbusePreventThrownItems) {
            return;
        }

        if (event.getPlayer().hasPermission(config.antiAbuseBypassPermission)) {
            return;
        }

        event.setCancelled(true);
        event.getItemDrop().remove();
    }

    /**
     * Handles the ProjectileLaunchEvent to prevent players from launching projectiles if the
     * anti-abuse feature is enabled and the restriction is configured.
     * <br/>
     * This method cancels the event and removes the projectile if the shooter is a player
     * without the bypass permission.
     *
     * @param event The ProjectileLaunchEvent triggered when a projectile is launched.
     */
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        var config = AntiCreative.config();
        if (!config.antiAbuseEnabled || !config.antiAbusePreventProjectiles) {
            return;
        }

        if (event.getEntity().getShooter() != null && event.getEntity().getShooter() instanceof Player player) {
            if (player.hasPermission(config.antiAbuseBypassPermission)) {
                return;
            }
        }

        event.setCancelled(true);
        event.getEntity().remove();
    }
}