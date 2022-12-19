package me.erichards.items;

import me.erichards.items.commands.CategoryCommand;
import me.erichards.items.commands.ItemsCommand;
import me.erichards.items.item.Category;
import me.erichards.items.item.CategoryManager;
import me.erichards.items.item.Item;
import me.erichards.items.item.ItemManager;
import me.erichards.items.utils.FileManager;
import me.erichards.pluginapi.gui.GUIListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

/**
 * Made by Ethan Richards
 * December 16, 2022
 */
public class Items extends JavaPlugin {

    private final ItemManager itemManager = new ItemManager();
    private final CategoryManager categoryManager = new CategoryManager();
    private FileConfiguration config;

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Category.class);
        ConfigurationSerialization.registerClass(Item.class);
        saveDefaultConfig();
        config = getConfig();
        config.getConfigurationSection("categories").getKeys(false).forEach(key -> categoryManager.addCategory((Category) config.get("categories." + key)));
        loadItems();
        getCommand("items").setExecutor(new ItemsCommand(this));
        getCommand("category").setExecutor(new CategoryCommand(this));
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public FileConfiguration getConfiguration() {
        return config;
    }

    private void loadItems() {
        File itemsFolder = FileManager.getDirectory("items");
        File[] files = itemsFolder.listFiles();
        if(files == null) return;

        Arrays.stream(files).filter(f -> f.getName().endsWith(".yml")).forEach(file -> {
            FileConfiguration itemConfig = FileManager.getConfiguration(file.getName(), "items");
            Item item = (Item) itemConfig.get("item");
            if(categoryManager.getCategoryByName(item.getCategory().getName()) != null) {
                categoryManager.getCategoryByName(item.getCategory().getName()).addItem(item);
            }
            itemManager.addItem(item);
        });
    }
}
