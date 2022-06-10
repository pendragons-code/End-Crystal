package io.github.javierscode.endcrystal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.github.javierscode.endcrystal.Commands.endcrystal;
import io.github.javierscode.endcrystal.Listener.onPlayerDeath;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class EndCrystal extends JavaPlugin {

    public static FileConfiguration data;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new onPlayerDeath(), this);
        getCommand("endcrystal").setExecutor(new endcrystal());
        data = getConfig("data.yml");
        saveDefaultConfig();
        // save config.yml
        config = getConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private FileConfiguration getConfig(String filename) {
        // get file, create a new one if it doesnt exist
        File file = new File(getDataFolder(), filename);
        if (!file.exists()) {
            // file no exist
            file.getParentFile().mkdirs();
            saveResource(filename, true);
        }

        // load the file's config
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static void createPlayerSection(String playerName) {
        // create a section in data.yml for the player
        data.createSection(playerName);
        data.createSection(playerName + ".kills");
        data.createSection(playerName + ".deaths");
        data.createSection(playerName + ".msgs");
    }

    // strings for data.yml
    public static String playerKills(String name) {
        return name + ".kills";
    }
    public static String playerDeaths(String name) {
        return name + ".deaths";
    }
    public static String playerMessages(String name) {
        return name + ".msgs";
    }

    public static int getPlayerKills(String name) {
        return data.getInt(playerDeaths(name));
    }

    public static int getPlayerDeaths(String name) {
        return data.getInt(playerDeaths(name));
    }

    public static List<String> getPlayerMessages(String name) {
        return data.getStringList(playerDeaths(name));
    }

}
