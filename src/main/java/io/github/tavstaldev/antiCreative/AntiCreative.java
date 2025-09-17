package io.github.tavstaldev.antiCreative;

import io.github.tavstaldev.antiCreative.listeners.PlayerEventListener;
import io.github.tavstaldev.minecorelib.PluginBase;
import io.github.tavstaldev.minecorelib.core.PluginLogger;
import io.github.tavstaldev.minecorelib.core.PluginTranslator;
import io.github.tavstaldev.minecorelib.utils.VersionUtils;
import org.bukkit.Bukkit;

public class AntiCreative extends PluginBase {
    public static AntiCreative Instance;
    public static PluginLogger Logger() {
        return Instance.getCustomLogger();
    }

    public static PluginTranslator Translator() {
        return Instance.getTranslator();
    }

    public static AntiCreativeConfig Config() {
        return (AntiCreativeConfig) Instance.getConfig();
    }

    public AntiCreative()  {
        super(false, "https://github.com/TavstalDev/AntiCreative/releases/latest");
    }

    @Override
    public void onEnable() {
        Instance = this;
        _logger.Info(String.format("Loading %s...", getProjectName()));
        _config = new AntiCreativeConfig();
        _config.load();
        _translator = new PluginTranslator(this, new String[]{ "hun"});

        if (VersionUtils.isLegacy()) {
            _logger.Error("The plugin is not compatible with legacy versions of Minecraft. Please use a newer version of the game.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Register event listeners
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);

        _logger.Ok(String.format("%s has been successfully loaded.", getProjectName()));
        if (Config().checkForUpdates) {
            isUpToDate().thenAccept(upToDate -> {
                if (upToDate) {
                    _logger.Ok("Plugin is up to date!");
                } else {
                    _logger.Warn("A new version of the plugin is available: " + getDownloadUrl());
                }
            }).exceptionally(e -> {
                _logger.Error("Failed to determine update status: " + e.getMessage());
                return null;
            });
        }
    }

    @Override
    public void onDisable() {
        _logger.Info(String.format("%s has been successfully unloaded.", getProjectName()));
    }

    public void reload() {
        _logger.Info(String.format("Reloading %s...", getProjectName()));
        _logger.Debug("Reloading localizations...");
        _translator.Load();
        _logger.Debug("Localizations reloaded.");
        _logger.Debug("Reloading configuration...");
        _config.load();
        _logger.Debug("Configuration reloaded.");
    }
}
