package me.erichards.items.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Made by Ethan Richards
 * March 21, 2021
 */
public class ItemManager {

    public List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.add(item);
    }

    public void clearItems() {
        items.clear();
    }

    public Item getItemByName(String name) {
        for(Item item : items) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
