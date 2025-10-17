package io.github.tavstaldev.antiCreative;

import io.github.tavstaldev.minecorelib.config.ConfigurationBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AntiCreativeConfig extends ConfigurationBase {

    public AntiCreativeConfig() {
        super(AntiCreative.Instance, "config.yml", null);
    }

    public String prefix;
    public boolean checkForUpdates, debug;

    public String banCommand;
    public boolean banOnGamemodeChange, banOnWorldChange, banOnJoin, banOnTeleport;
    public Set<String> allowedPlayers;
    public Set<String> disabledWorlds;

    @Override
    protected void loadDefaults() {
        if (allowedPlayers == null)
            allowedPlayers = new HashSet<>();

        if (disabledWorlds == null)
            disabledWorlds = new HashSet<>();


        // General
        resolve("locale", "eng");
        resolve("usePlayerLocale", false);
        checkForUpdates = resolveGet("checkForUpdates", false);
        debug = resolveGet("debug", false);
        prefix = resolveGet("prefix", "&cAnti&eCreative &8»");

        banCommand = resolveGet("ban-command", "litebans:ban %player% 30d You were banned for being in creative mode.");

        banOnGamemodeChange = resolveGet("ban.on-gamemode-change", true);
        banOnWorldChange = resolveGet("ban.on-world-change", false);
        banOnJoin = resolveGet("ban.on-join", false);
        banOnTeleport = resolveGet("ban.on-teleport", false);

        var list = resolveGet("allowed-players", List.of("Steve", "Alex"));
        allowedPlayers.clear(); // on reload: clear the set first
        allowedPlayers.addAll(list);
        list = resolveGet("disabled-worlds", List.of("world_events"));
        disabledWorlds.clear(); // on reload: clear the set first
        disabledWorlds.addAll(list);
    }
}