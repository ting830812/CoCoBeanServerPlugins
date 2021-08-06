package com.Ting.SnowballPortalFreezeAddon;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    static Main instance;

    @Override
    public void onEnable(){

        instance = this;
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

    }

    public static Main getInstance() {
        return instance;
    }

}
