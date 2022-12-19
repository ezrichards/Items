package me.erichards.items.item;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Made by Ethan Richards
 * March 23, 2021
 */
public class Category implements ConfigurationSerializable {

    private String name;
    private Material icon;
    private List<Item> items;

    public Category(String name, Material icon) {
        this.name = name;
        this.icon = icon;
        this.items = new ArrayList<>();
    }

    public Category(Map<String, Object> serializedCategory) {
        name = (String) serializedCategory.get("name");
        icon = Material.valueOf((String) serializedCategory.get("icon"));
        items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> mapSerializer = new HashMap<>();
        mapSerializer.put("name", name);
        mapSerializer.put("icon", icon.name());
        return mapSerializer;
    }
}
