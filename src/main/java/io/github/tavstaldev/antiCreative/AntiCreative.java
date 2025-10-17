package io.github.tavstaldev.antiCreative;

import io.github.tavstaldev.antiCreative.listeners.PlayerEventListener;
import io.github.tavstaldev.minecorelib.PluginBase;
import io.github.tavstaldev.minecorelib.core.PluginLogger;
import io.github.tavstaldev.minecorelib.core.PluginTranslator;
import io.github.tavstaldev.minecorelib.utils.VersionUtils;
import org.bukkit.Bukkit;

/**
 * Main class for the AntiCreative plugin.
 * Extends the PluginBase class to provide core plugin functionality.
 */
public class AntiCreative extends PluginBase {
    /**
     * Singleton instance of the AntiCreative plugin.
     */
    public static AntiCreative Instance;

    /**
     * Retrieves the custom logger for the plugin.
     *
     * @return The PluginLogger instance.
     */
    public static PluginLogger Logger() {
        return Instance.getCustomLogger();
    }

    /**
     * Retrieves the translator for the plugin.
     *
     * @return The PluginTranslator instance.
     */
    public static PluginTranslator Translator() {
        return Instance.getTranslator();
    }

    /**
     * Retrieves the configuration for the plugin.
     *
     * @return The AntiCreativeConfig instance.
     */
    public static AntiCreativeConfig Config() {
        return (AntiCreativeConfig) Instance.getConfig();
    }

    /**
     * Constructor for the AntiCreative plugin.
     * Initializes the plugin with the update URL.
     */
    public AntiCreative()  {
        super(false, "https://github.com/TavstalDev/AntiCreative/releases/latest");
    }

    /**
     * Called when the plugin is enabled.
     * Initializes the plugin, loads configuration, and registers event listeners.
     */
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

    /**
     * Called when the plugin is disabled.
     * Logs the unloading of the plugin.
     */
    @Override
    public void onDisable() {
        _logger.Info(String.format("%s has been successfully unloaded.", getProjectName()));
    }

    /**
     * Reloads the plugin's configuration and localizations.
     * Logs the reloading process.
     */
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
