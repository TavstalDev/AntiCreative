package io.github.tavstaldev.antiCreative.utils;

import io.github.tavstaldev.antiCreative.AntiCreative;
import org.bukkit.entity.Player;

/**
 * Utility class for player-related operations in the AntiCreative plugin.
 */
public class PlayerUtil {

    /**
     * Checks if a player is allowed based on the configuration.
     *
     * @param player The player to check.
     * @return True if the player is in the allowed players list, false otherwise.
     */
    public static boolean isAllowed(Player player) {
        return AntiCreative.config().antiCreativeAllowedPlayers.contains(player.getName());
    }

    /**
     * Checks if a player is in a disabled world based on the configuration.
     *
     * @param player The player to check.
     * @return True if the player's world is in the disabled worlds list, false otherwise.
     */
    public static boolean isInDisabledWorld(Player player) {
        return AntiCreative.config().antiCreativeDisabledWorlds.contains(player.getWorld().getName());
    }

    /**
     * Bans a player by executing the configured ban command.
     *
     * @param player The player to ban.
     */
    public static void banPlayer(Player player) {
        final String command = AntiCreative.config().antiCreativeBanCommand.replace("%player%", player.getName());
        AntiCreative.logger().info("Executing ban command: " + command);
        AntiCreative.Instance.getServer().dispatchCommand(AntiCreative.Instance.getServer().getConsoleSender(), command);
    }
}
