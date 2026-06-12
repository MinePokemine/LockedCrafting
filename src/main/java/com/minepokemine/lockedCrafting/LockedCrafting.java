package com.minepokemine.lockedCrafting;

import org.bukkit.plugin.java.JavaPlugin;

public final class LockedCrafting extends JavaPlugin {
    public static LockedCrafting instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new Events(), this);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
