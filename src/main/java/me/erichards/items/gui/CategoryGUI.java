package me.erichards.items.gui;

import me.erichards.items.Items;
import me.erichards.items.item.Category;
import me.erichards.pluginapi.gui.GUI;
import me.erichards.pluginapi.gui.GUIItem;
import me.erichards.pluginapi.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Made by Ethan Richards
 * December 16, 2022
 */
public class CategoryGUI extends GUI {

    public CategoryGUI(Items plugin) {
        super(ChatColor.DARK_GRAY + "Items", 6);

        int slot = 10;
        for (Category category : plugin.getCategoryManager().getCategories()) {
            addItem(new GUIItem(slot, new ItemBuilder(category.getIcon(), 1).setDisplayName(ChatColor.GOLD + category.getName()).setLore(ChatColor.GRAY + "Left click to view category.", "", ChatColor.GRAY + "Items: " + ChatColor.YELLOW + category.getItems().size()).build()) {
                @Override
                public void onClick(Player player, ClickType clickType) {
                    player.closeInventory();
                    new ItemsGUI("Items", category).open(player);
                }
            });
            slot++;
        }
    }
}
