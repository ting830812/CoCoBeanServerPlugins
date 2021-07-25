package com.Ting.ResidenceFly;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    static Main instance;
    static FileConfiguration dataconfig;
    static File datac;

    @Override
    public void onEnable() {
        instance = this;
        this.loadConfig();
        this.createdataConfig();
        save();

        Plugin ResidencePlugin = Bukkit.getPluginManager().getPlugin("Residence");
        if (ResidencePlugin == null) {
            this.getLogger().info("因Residence不存在而關閉插件");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        this.getServer().getPluginManager().registerEvents(new PlayerMovement(), this);

        FlagPermissions.addFlag("fly");
        this.getLogger().info("Plugin loaded");

    }

    public void onDisable() {
        this.getLogger().info("Plugin Unloaded");
    }

    public static Main getInstance() {
        return instance;
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
    }

    private void createdataConfig() {
        datac = new File(this.getDataFolder(), "data.yml");
        if (!datac.exists()) {
            datac.getParentFile().mkdirs();
            this.saveResource("data.yml", false);
        }

        dataconfig = new YamlConfiguration();

        try {
            dataconfig.load(datac);
        } catch (IOException | InvalidConfigurationException var2) {
            var2.printStackTrace();
        }

    }

    public static void save() {
        try {
            dataconfig.save(datac);
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }
}
