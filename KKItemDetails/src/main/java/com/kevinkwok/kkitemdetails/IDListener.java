package com.kevinkwok.kkitemdetails;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class IDListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent event)
    {
        Inventory inv = event.getInventory();

        if(inv.getTitle().equals("物品详情"))
        {
            if(event.getRawSlot() == 13)
            {
                Player player = (Player) event.getWhoClicked();
                event.setCancelled(true);
            }
        }
    }

}
