package me.erichards.items.commands;

import me.erichards.items.Items;
import me.erichards.items.item.Category;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Made by Ethan Richards
 * December 16, 2022
 */
public class CategoryCommand implements CommandExecutor {

    private Items plugin;

    public CategoryCommand(Items instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Console cannot use the Category command!");
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("items.admin")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use the Category command!");
            return true;
        }

        if(args.length == 0) {
            if(command.getName().equalsIgnoreCase("category")) {
                player.sendMessage("");
                player.sendMessage(ChatColor.GOLD + "Item Categories");
                player.sendMessage(ChatColor.YELLOW + "/category add <name>" + ChatColor.GRAY + " - creates a category with the specified name.");
                player.sendMessage(ChatColor.YELLOW + "/category remove <name>" + ChatColor.GRAY + " - removes a category by name.");
                player.sendMessage(ChatColor.YELLOW + "/category list" + ChatColor.GRAY + " - lists all loaded categories.");
                player.sendMessage("");
            }
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("add")) {
                player.sendMessage(ChatColor.RED + "Invalid argument! Usage: /category add <name>");
            }
            else if(args[0].equalsIgnoreCase("remove")) {
                player.sendMessage(ChatColor.RED + "Invalid argument! Usage: /category remove <name>");
            }
            else if(args[0].equalsIgnoreCase("list")) {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < plugin.getCategoryManager().getCategories().size(); i++) {
                    if(i + 1 == plugin.getCategoryManager().getCategories().size()) {
                        sb.append(plugin.getCategoryManager().getCategories().get(i).getName());
                    }
                    else {
                        sb.append(plugin.getCategoryManager().getCategories().get(i).getName()).append(", ");
                    }
                }
                player.sendMessage(ChatColor.GRAY + "Categories: " + sb);
            }
        }

        if(args.length == 2) {
            String categoryName = args[1];
            if(plugin.getCategoryManager().getCategoryByName(categoryName) != null) {
                if(args[0].equalsIgnoreCase("remove")) {
                    player.sendMessage(ChatColor.RED + "Removed category " + categoryName + ".");
                    Category category = plugin.getCategoryManager().getCategoryByName(categoryName);
                    plugin.getConfiguration().set("categories." + categoryName, null);
                    plugin.saveConfig();
                    plugin.getCategoryManager().removeCategory(category);
                }
                else {
                    player.sendMessage(ChatColor.RED + "Invalid argument!");
                }
            }
            else {
                if(args[0].equalsIgnoreCase("add")) {
                    Category category = new Category(categoryName, Material.CHEST);
                    plugin.getCategoryManager().addCategory(category);
                    plugin.getConfiguration().set("categories." + categoryName, category);
                    plugin.saveConfig();
                    player.sendMessage(ChatColor.GRAY + "Created new category " + ChatColor.GOLD + categoryName + ChatColor.GRAY + ".");
                }
                else {
                    player.sendMessage(ChatColor.RED + "Couldn't find the specified category!");
                }
            }
        }
        return true;
    }
}
