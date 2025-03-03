package com.kevinkwok.kkitemdetails;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class KKItemDetails extends JavaPlugin {

    public static KKItemDetails Instance;

    @Override
    public void onEnable() {

        System.out.println("KKItemDetails 已启用");

        Bukkit.getPluginCommand("kkitemdetails").setExecutor(new IDCommands());
        Bukkit.getPluginManager().registerEvents(new IDListener(),this);

        Initialization();

        Instance = this;
    }

    private void Initialization()
    {
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {

        System.out.println("KKItemDetails 已卸载");

    }
}
