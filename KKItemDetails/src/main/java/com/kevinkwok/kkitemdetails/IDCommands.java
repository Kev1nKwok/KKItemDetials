package com.kevinkwok.kkitemdetails;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


public class IDCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player){
            Player player = (Player) commandSender;

            if(args.length != 1){
                player.sendMessage("§f  ▶  §7请输入子命令 ->§f check §7以检查");
                return false;
            }

            if(args[0].equals("reload"))
            {
                if(player.hasPermission("server.admin")){
                    KKItemDetails.Instance.reloadConfig();
                    player.sendMessage("§f  ▶  §7插件已重载");
                }else{
                    player.sendMessage("§f  ▶  §7你并没有权限执行此命令");
                }
            }

            if(args[0].equals("check")) {
                Inventory inv = Bukkit.createInventory(null, 27, "物品详情");

                ItemStack playerItem = player.getInventory().getItemInMainHand();

                if (playerItem.getType() == Material.AIR) {
                    player.sendMessage("§f  ▶  §7请手持一个正确的物品来检查。");
                    return true;
                }

                String itemName = playerItem.getItemMeta().getDisplayName();
                Material material = playerItem.getData().getItemType();
                //List<String> lore = playerItem.getItemMeta().getLore();
                //ItemMeta itemMeta = playerItem.getItemMeta();

                ConfigurationSection itemList = KKItemDetails.Instance.getConfig().getConfigurationSection("ItemList");

                if(itemList != null) {
                    boolean foundMatch = false;

                    for(String key : itemList.getKeys(false)) {
                        String checkName = itemList.getString(key + ".Name");
                        List<String> replaceLore = itemList.getStringList(key + ".ReplaceLore");

                        if(removeColorCodes(itemName).contains(removeColorCodes(checkName))) {
                            foundMatch = true;

                            ItemStack menuIconItem = new ItemStack(material);
                            ItemMeta iconItemMeta = menuIconItem.getItemMeta();
                            iconItemMeta.setDisplayName(itemName);
                            iconItemMeta.setLore(replaceLore);
                            menuIconItem.setItemMeta(iconItemMeta);

                            inv.setItem(13, menuIconItem);
                            player.openInventory(inv);

                            break;
                        }
                    }

                    if(!foundMatch) {
                        player.sendMessage("§f  ▶  §7这个物品并没有太多的注解");
                    }else{
                        return true;
                    }
                }
            }
        }

        if(args.length != 1){
            commandSender.sendMessage("  ▶  /kkid reload");
            return false;
        }

        if(args[0].equals("reload"))
        {
            if(commandSender.hasPermission("server.admin")){
                KKItemDetails.Instance.reloadConfig();
                commandSender.sendMessage("§e插件已重载");
            }else{
                commandSender.sendMessage(" ▶  你并没有权限执行此命令");
            }
        }

        return false;
    }

    public String removeColorCodes(String input) {
        return ChatColor.stripColor(input);
    }
}
