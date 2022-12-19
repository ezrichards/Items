package me.erichards.items.commands;

import me.erichards.items.Items;
import me.erichards.items.gui.CategoryGUI;
import me.erichards.items.gui.ItemsGUI;
import me.erichards.items.item.Item;
import me.erichards.items.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Made by Ethan Richards
 * December 16, 2022
 */
public class ItemsCommand implements CommandExecutor {

    private Items plugin;

    public ItemsCommand(Items instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Console cannot use the Items command!");
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("items.admin")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use the Items command!");
            return true;
        }

        if(args.length == 0) {
            if(command.getName().equalsIgnoreCase("items")) {
                player.sendMessage(ChatColor.GRAY + "Opening the Items interface..");
                new CategoryGUI(plugin).open(player);
            }
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("help")) {
                player.sendMessage("");
                player.sendMessage(ChatColor.GOLD + "Items");
                player.sendMessage(ChatColor.YELLOW + "/items" + ChatColor.GRAY + " - opens the items interface.");
                player.sendMessage(ChatColor.YELLOW + "/items add <name> <category>" + ChatColor.GRAY + " - adds the held item in hand to a category.");
                player.sendMessage(ChatColor.YELLOW + "/items remove <name>" + ChatColor.GRAY + " - removes the specified item or item in hand.");
                player.sendMessage(ChatColor.YELLOW + "/items give <name> (player)" + ChatColor.GRAY + " - gives the specified item, optionally to a target player.");
                player.sendMessage(ChatColor.YELLOW + "/items view <category>" + ChatColor.GRAY + " - view a specific category of items.");
                player.sendMessage("");
            }
            else if(args[0].equalsIgnoreCase("add")) {
                player.sendMessage(ChatColor.RED + "Invalid argument! Usage: /items add <name> (category)");
            }
            else if(args[0].equalsIgnoreCase("remove")) {
                player.sendMessage(ChatColor.RED + "Invalid argument! Usage: /items remove <name>");
            }
            else if(args[0].equalsIgnoreCase("give")) {
                player.sendMessage(ChatColor.RED + "Invalid argument! Usage: /items give <name> (player)");
            }
            else if(args[0].equalsIgnoreCase("view")) {
                player.sendMessage(ChatColor.RED + "Invalid argument! Usage: /items view <category>");
            }
        }

        if(args.length == 2) {
            String name = args[1];

            if(plugin.getItemManager().getItemByName(name) != null) {
                Item item = plugin.getItemManager().getItemByName(name);

                if(args[0].equalsIgnoreCase("add")) {
                    player.sendMessage(ChatColor.RED + "Invalid argument! Usage: /items add <name> <category>");
                }
                else if(args[0].equalsIgnoreCase("remove")) {
                    player.sendMessage(ChatColor.GRAY + "Removed " + name + " from " + item.getCategory().getName());
                    item.getCategory().removeItem(item);
                    plugin.getItemManager().removeItem(item);
                }
                else if(args[0].equalsIgnoreCase("give")) {
                    player.sendMessage(ChatColor.GRAY + "Successfully given item: " + name + " from category " + item.getCategory().getName() + "!");
                    player.getInventory().addItem(item.getItemStack());
                }
                else {
                    player.sendMessage(ChatColor.RED + "Invalid argument! Try /items help for help.");
                }
            }
            else {
                if(args[0].equalsIgnoreCase("add")) {
                    player.sendMessage(ChatColor.RED + "Invalid argument! Usage: /items add <name> <category>");
                }
                else if(args[0].equalsIgnoreCase("remove")) {
                    player.sendMessage(ChatColor.RED + "The item by the name of " + name + " could not be found!");
                }
                else if(args[0].equalsIgnoreCase("give")) {
                    player.sendMessage(ChatColor.RED + "The item by the name of " + name + " could not be found!");
                }
                else if(args[0].equalsIgnoreCase("view")) {
                    if(plugin.getCategoryManager().getCategoryByName(name) == null) {
                        player.sendMessage(ChatColor.RED + "The specified category does not exist!");
                        return true;
                    }

                    player.sendMessage(ChatColor.GRAY + "Opening the items interface for category " + name + "..");
                    ItemsGUI itemsGUI = new ItemsGUI(ChatColor.GRAY + "Items for " + name, plugin.getCategoryManager().getCategoryByName(name));
                    itemsGUI.open(player);
                }
                else {
                    player.sendMessage(ChatColor.RED + "Invalid argument!");
                }
            }
        }

        if(args.length == 3) {
            String itemName = args[1];

            Player target = Bukkit.getPlayer(args[2]);
            if(target != null) {
                if(args[0].equalsIgnoreCase("give")) {
                    if(plugin.getItemManager().getItemByName(itemName) != null) {
                        Item item = plugin.getItemManager().getItemByName(itemName);
                        player.getInventory().addItem(item.getItemStack());
                        player.sendMessage(ChatColor.GRAY + "Successfully given item: " + itemName + " from category " + item.getCategory().getName() + "!");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Couldn't find the specified item!");
                    }
                }
            }
            else {
                String categoryName = args[2];

                if(plugin.getCategoryManager().getCategoryByName(categoryName) != null) {
                    if(args[0].equalsIgnoreCase("add")) {
                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        player.sendMessage(ChatColor.GRAY + "Successfully added " + itemStack.getItemMeta().getDisplayName() + ChatColor.GRAY + " as " + ChatColor.GREEN + itemName + ChatColor.GRAY + " to category " + ChatColor.GREEN + categoryName + ChatColor.GRAY + "!");

                        Item item = new Item(itemName, itemStack, plugin.getCategoryManager().getCategoryByName(categoryName));
                        plugin.getItemManager().addItem(item);
                        plugin.getCategoryManager().getCategoryByName(categoryName).addItem(item);

                        FileConfiguration config = FileManager.getConfiguration("items/" + item.getName() + ".yml");
                        config.set("item", item);
                        FileManager.saveFile(item.getName() + ".yml", "items");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Invalid argument!");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Could not find the specified category or player!");
                }
            }
        }
        return true;
    }
}
