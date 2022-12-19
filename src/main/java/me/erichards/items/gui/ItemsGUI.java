package me.erichards.items.gui;

import me.erichards.items.item.Category;
import me.erichards.items.item.Item;
import me.erichards.pluginapi.gui.GUI;
import me.erichards.pluginapi.gui.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Made by Ethan Richards
 * December 16, 2022
 */
public class ItemsGUI extends GUI {

    public ItemsGUI(String title, Category category) {
        super(title, 6);

        int slot = 0;
        for (Item item : category.getItems()) {
            addItem(new GUIItem(slot, item.getItemStack()) {
                @Override
                public void onClick(Player player, ClickType clickType) {
                    player.getInventory().addItem(item.getItemStack());
                    player.closeInventory();
                }
            });
            slot++;
        }
    }
}
