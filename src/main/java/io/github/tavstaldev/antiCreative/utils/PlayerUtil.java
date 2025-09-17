package io.github.tavstaldev.antiCreative.utils;

import io.github.tavstaldev.antiCreative.AntiCreative;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static boolean isAllowed(Player player) {
        return AntiCreative.Config().allowedPlayers.contains(player.getName());
    }

    public static boolean isInDisabledWorld(Player player) {
        return AntiCreative.Config().disabledWorlds.contains(player.getWorld().getName());
    }

    public static void banPlayer(Player player) {
        final String command = AntiCreative.Config().banCommand.replace("%player%", player.getName());
        AntiCreative.Logger().Info("Executing ban command: " + command);
        AntiCreative.Instance.getServer().dispatchCommand(AntiCreative.Instance.getServer().getConsoleSender(), command);
    }
}
