package io.github.tavstaldev.antiCreative.utils;

import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
    public static boolean isContainer(Material material) {
        //noinspection EnhancedSwitchMigration
        switch (material) {
            case CHEST:
            case TRAPPED_CHEST:
            case ENDER_CHEST:
            case BARREL:
            case SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case LIGHT_GRAY_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case BLACK_SHULKER_BOX:
            case DISPENSER:
            case DROPPER:
            case FURNACE:
            case BLAST_FURNACE:
            case SMOKER:
            case HOPPER:
                return true;
            default:
                return false;
        }
    }

    public boolean isContainerInventory(Inventory inventory) {
        if (inventory == null)
            return false;

        //noinspection EnhancedSwitchMigration
        switch (inventory.getType()) {
            case CHEST:
            case ENDER_CHEST:
            case BARREL:
            case SHULKER_BOX:
            case DISPENSER:
            case DROPPER:
            case FURNACE:
            case BLAST_FURNACE:
            case SMOKER:
            case HOPPER:
                return true;
        }
        return false;
    }

    public static boolean hasItemsOrNBT(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName() || meta.hasLore() || meta.hasEnchants()) {
            return true;
        }

        if (!(meta instanceof BlockStateMeta blockStateMeta))
            return false;

        if (!(blockStateMeta.getBlockState() instanceof Container container))
            return false;

        for (ItemStack content : container.getInventory().getContents()) {
            if (content == null || content.getType() == Material.AIR)
                continue;
            return true;
        }

        return false;
    }
}
