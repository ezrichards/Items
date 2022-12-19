package me.erichards.items.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Made by Ethan Richards
 * December 16, 2022
 */
public class FileManager {

    // TODO: better solution for this & file utils

    public static File getDirectory(String directoryName) {
        File directory = new File(Bukkit.getPluginManager().getPlugin("Items").getDataFolder() + File.separator + directoryName);
        if(!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    public static YamlConfiguration getConfiguration(String fileName, String directory) {
        File file = new File(Bukkit.getPluginManager().getPlugin("Items").getDataFolder() + File.separator + directory, fileName);
        if (!file.exists()) {
            loadFile(fileName);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static YamlConfiguration getConfiguration(String fileName) {
        File file = new File(Bukkit.getPluginManager().getPlugin("Items").getDataFolder(), fileName);
        if (!file.exists()) {
            loadFile(fileName);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void loadFile(String fileName) {
        try {
            File file = new File(Bukkit.getPluginManager().getPlugin("Items").getDataFolder(), fileName);
            if(file.createNewFile()) {
                Bukkit.getServer().getLogger().info("Generated new " + fileName + "!");
                YamlConfiguration.loadConfiguration(file);
            }
        } catch(IOException e) {
            Bukkit.getServer().getLogger().severe("Could not create " + fileName + "!");
        }
    }

    public static void saveFile(String fileName) {
        try {
            getConfiguration(fileName).save(new File(Bukkit.getPluginManager().getPlugin("Items").getDataFolder(), fileName));
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe("Could not save " + fileName + ".yml!");
        }
    }

    public static void saveFile(String fileName, String directory) {
        try {
            getConfiguration(fileName).save(new File(Bukkit.getPluginManager().getPlugin("Items").getDataFolder() + File.separator + directory, fileName));
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe("Could not save " + fileName + ".yml!");
        }
    }
}