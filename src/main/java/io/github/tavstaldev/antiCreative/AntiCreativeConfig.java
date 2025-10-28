package io.github.tavstaldev.antiCreative;

import io.github.tavstaldev.minecorelib.config.ConfigurationBase;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AntiCreativeConfig extends ConfigurationBase {

    public AntiCreativeConfig() {
        super(AntiCreative.Instance, "config.yml", null);
    }

    public String prefix;
    public boolean checkForUpdates, debug;

    // Anti Creative
    public boolean antiCreativeEnabled;
    public String antiCreativeBanCommand;
    public boolean antiCreativeBanOnGamemodeChange, antiCreativeBanOnWorldChange, antiCreativeBanOnJoin, antiCreativeBanOnTeleport;
    public Set<String> antiCreativeAllowedPlayers;
    public Set<String> antiCreativeDisabledWorlds;

    // Anti Abuse
    public boolean antiAbuseEnabled;
    public boolean antiAbuseEnableBypassPermission;
    public String antiAbuseBypassPermission;
    public boolean antiAbusePreventProjectiles;
    public boolean antiAbusePreventThrownItems;
    public boolean antiAbusePreventEnderPearls;
    public boolean antiAbusePreventOpeningContainers;
    public Set<Material> antiAbuseInteractionBlacklist;

    @Override
    protected void loadDefaults() {
        if (antiCreativeAllowedPlayers == null)
            antiCreativeAllowedPlayers = new HashSet<>();

        if (antiCreativeDisabledWorlds == null)
            antiCreativeDisabledWorlds = new HashSet<>();


        // General
        resolve("locale", "eng");
        resolve("usePlayerLocale", false);
        checkForUpdates = resolveGet("checkForUpdates", false);
        debug = resolveGet("debug", false);
        prefix = resolveGet("prefix", "&bAnti&3Creative &8»");

        //# region Anti creative
        antiCreativeEnabled = resolveGet("anti-creative.enabled", false);
        antiCreativeBanCommand = resolveGet("anti-creative.ban.command", "litebans:ban %player% 30d You were banned for being in creative mode.");

        antiCreativeBanOnGamemodeChange = resolveGet("anti-creative.ban.on-gamemode-change", true);
        antiCreativeBanOnWorldChange = resolveGet("anti-creative.ban.on-world-change", false);
        antiCreativeBanOnJoin = resolveGet("anti-creative.ban.on-join", false);
        antiCreativeBanOnTeleport = resolveGet("anti-creative.ban.on-teleport", false);

        var list = resolveGet("anti-creative.allowed-players", List.of("Steve", "Alex"));
        antiCreativeAllowedPlayers.clear(); // on reload: clear the set first
        antiCreativeAllowedPlayers.addAll(list);
        list = resolveGet("anti-creative.disabled-worlds", List.of("world_events"));
        antiCreativeDisabledWorlds.clear(); // on reload: clear the set first
        antiCreativeDisabledWorlds.addAll(list);
        //#endregion

        //# region Anti abuse
        antiAbuseEnabled = resolveGet("anti-abuse.enabled", false);
        antiAbuseEnableBypassPermission = resolveGet("anti-abuse.enable-bypass-permission", true);
        antiAbuseBypassPermission = resolveGet("anti-abuse.bypass-permission", "anticreative.bypass");
        antiAbusePreventProjectiles = resolveGet("anti-abuse.prevent-projectiles", true);
        antiAbusePreventThrownItems = resolveGet("anti-abuse.prevent-thrown-items", true);
        antiAbusePreventEnderPearls = resolveGet("anti-abuse.prevent-ender-pearls", false);
        antiAbusePreventOpeningContainers = resolveGet("anti-abuse.prevent-opening-containers", true);
        var materialList = resolveGet("anti-abuse.interaction-blacklist", List.of(
                // --- Specific Materials ---
                "ARMOR_STAND",
                "BIRCH_BOAT",
                "BIRCH_CHEST_BOAT",
                "ACACIA_BOAT",
                "ACACIA_CHEST_BOAT",
                "CHERRY_BOAT",
                "CHERRY_CHEST_BOAT",
                "DARK_OAK_BOAT",
                "DARK_OAK_CHEST_BOAT",
                "JUNGLE_BOAT",
                "JUNGLE_CHEST_BOAT",
                "MANGROVE_BOAT",
                "MANGROVE_CHEST_BOAT",
                "OAK_BOAT",
                "OAK_CHEST_BOAT",
                "PALE_OAK_BOAT",
                "PALE_OAK_CHEST_BOAT",
                "SPRUCE_BOAT",
                "SPRUCE_CHEST_BOAT",
                "MINECART",
                "CHEST_MINECART",
                "COMMAND_BLOCK_MINECART",
                "HOPPER_MINECART",
                "FURNACE_MINECART",
                "TNT_MINECART",
                "END_PORTAL",
                "NETHER_PORTAL",
                "END_PORTAL_FRAME",
                "WITHER_SKELETON_SKULL",
                "OBSERVER",
                "EGG",
                "ENDER_EYE",
                "BEE_NEST",
                "BEEHIVE",

                // --- All Remaining Spawn Eggs (Material.<MOB_NAME>_SPAWN_EGG) ---
                "ALLAY_SPAWN_EGG",
                "AXOLOTL_SPAWN_EGG",
                "BAT_SPAWN_EGG",
                "BEE_SPAWN_EGG",
                "BLAZE_SPAWN_EGG",
                "CAMEL_SPAWN_EGG",
                "CAT_SPAWN_EGG",
                "CAVE_SPIDER_SPAWN_EGG",
                "CHICKEN_SPAWN_EGG",
                "COD_SPAWN_EGG",
                "COW_SPAWN_EGG",
                "CREEPER_SPAWN_EGG",
                "DOLPHIN_SPAWN_EGG",
                "DONKEY_SPAWN_EGG",
                "DROWNED_SPAWN_EGG",
                "ENDERMAN_SPAWN_EGG",
                "ENDERMITE_SPAWN_EGG",
                "EVOKER_SPAWN_EGG",
                "ELDER_GUARDIAN_SPAWN_EGG",
                "FOX_SPAWN_EGG",
                "FROG_SPAWN_EGG",
                "GHAST_SPAWN_EGG",
                "GLOW_SQUID_SPAWN_EGG",
                "GOAT_SPAWN_EGG",
                "GUARDIAN_SPAWN_EGG",
                "HOGLIN_SPAWN_EGG",
                "HORSE_SPAWN_EGG",
                "HUSK_SPAWN_EGG",
                "IRON_GOLEM_SPAWN_EGG",
                "LLAMA_SPAWN_EGG",
                "MAGMA_CUBE_SPAWN_EGG",
                "MOOSHROOM_SPAWN_EGG",
                "MULE_SPAWN_EGG",
                "OCELOT_SPAWN_EGG",
                "PANDA_SPAWN_EGG",
                "PARROT_SPAWN_EGG",
                "PHANTOM_SPAWN_EGG",
                "PIG_SPAWN_EGG",
                "PIGLIN_BRUTE_SPAWN_EGG",
                "PIGLIN_SPAWN_EGG",
                "PILLAGER_SPAWN_EGG",
                "POLAR_BEAR_SPAWN_EGG",
                "PUFFERFISH_SPAWN_EGG",
                "RABBIT_SPAWN_EGG",
                "RAVAGER_SPAWN_EGG",
                "SALMON_SPAWN_EGG",
                "SHEEP_SPAWN_EGG",
                "SHULKER_SPAWN_EGG",
                "SILVERFISH_SPAWN_EGG",
                "SKELETON_HORSE_SPAWN_EGG",
                "SKELETON_SPAWN_EGG",
                "SLIME_SPAWN_EGG",
                "SNIFFER_SPAWN_EGG",
                "SNOW_GOLEM_SPAWN_EGG",
                "SPIDER_SPAWN_EGG",
                "SQUID_SPAWN_EGG",
                "STRAY_SPAWN_EGG",
                "STRIDER_SPAWN_EGG",
                "TADPOLE_SPAWN_EGG",
                "TROPICAL_FISH_SPAWN_EGG",
                "TURTLE_SPAWN_EGG",
                "VEX_SPAWN_EGG",
                "VILLAGER_SPAWN_EGG",
                "VINDICATOR_SPAWN_EGG",
                "WANDERING_TRADER_SPAWN_EGG",
                "WARDEN_SPAWN_EGG",
                "WITCH_SPAWN_EGG",
                "WITHER_SKELETON_SPAWN_EGG",
                "WOLF_SPAWN_EGG",
                "ZOGLIN_SPAWN_EGG",
                "ZOMBIE_HORSE_SPAWN_EGG",
                "ZOMBIE_SPAWN_EGG",
                "ZOMBIE_VILLAGER_SPAWN_EGG",
                "ZOMBIFIED_PIGLIN_SPAWN_EGG"
        ));
        antiAbuseInteractionBlacklist.clear(); // on reload: clear the set first
        for (String matStr : materialList) {
            try {
                Material mat = Material.valueOf(matStr);
                antiAbuseInteractionBlacklist.add(mat);
            } catch (IllegalArgumentException ex) {
                AntiCreative.Instance.getLogger().warning("Invalid material in anti-abuse.interaction-blacklist: " + matStr);
            }
        }

    }
}