package io.github.tavstaldev.antiCreative;

import io.github.tavstaldev.antiCreative.listeners.PlayerEventListener;
import io.github.tavstaldev.antiCreative.metrics.Metrics;
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
    public static PluginLogger logger() {
        return Instance.getCustomLogger();
    }

    /**
     * Retrieves the translator for the plugin.
     *
     * @return The PluginTranslator instance.
     */
    public static PluginTranslator translator() {
        return Instance.getTranslator();
    }

    /**
     * Retrieves the configuration for the plugin.
     *
     * @return The AntiCreativeConfig instance.
     */
    public static AntiCreativeConfig config() {
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
        _logger.info(String.format("Loading %s...", getProjectName()));
        _config = new AntiCreativeConfig();
        _config.load();
        _translator = new PluginTranslator(this, new String[]{ "hun"});

        if (VersionUtils.isLegacy()) {
            _logger.error("The plugin is not compatible with legacy versions of Minecraft. Please use a newer version of the game.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Register event listeners
        this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);

        // Metrics
        try {
            @SuppressWarnings("unused") Metrics metrics = new Metrics(this, 27760);
        }
        catch (Exception ex)
        {
            _logger.error("Failed to start Metrics: " + ex.getMessage());
        }

        _logger.ok(String.format("%s has been successfully loaded.", getProjectName()));
        if (config().checkForUpdates) {
            isUpToDate().thenAccept(upToDate -> {
                if (upToDate) {
                    _logger.ok("Plugin is up to date!");
                } else {
                    _logger.warn("A new version of the plugin is available: " + getDownloadUrl());
                }
            }).exceptionally(e -> {
                _logger.error("Failed to determine update status: " + e.getMessage());
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
        _logger.info(String.format("%s has been successfully unloaded.", getProjectName()));
    }

    /**
     * Reloads the plugin's configuration and localizations.
     * Logs the reloading process.
     */
    public void reload() {
        _logger.info(String.format("Reloading %s...", getProjectName()));
        _logger.debug("Reloading localizations...");
        _translator.load();
        _logger.debug("Localizations reloaded.");
        _logger.debug("Reloading configuration...");
        _config.load();
        _logger.debug("Configuration reloaded.");
    }
}
