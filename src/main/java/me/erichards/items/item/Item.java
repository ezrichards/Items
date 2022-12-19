package me.erichards.items.item;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Made by Ethan Richards
 * March 21, 2021
 */
public class Item implements ConfigurationSerializable {

    private String name;
    private ItemStack itemStack;
    private Category category;

    public Item(String name, ItemStack itemStack, Category category) {
        this.name = name;
        this.itemStack = itemStack;
        this.category = category;
    }

    public Item(Map<String, Object> serializedItem) {
        name = (String) serializedItem.get("name");
        itemStack = (ItemStack) serializedItem.get("item");
        category = new Category((String) serializedItem.get("category"), Material.CHEST);
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> mapSerializer = new HashMap<>();
        mapSerializer.put("item", itemStack);
        mapSerializer.put("category", category.getName());
        mapSerializer.put("name", name);
        return mapSerializer;
    }
}
