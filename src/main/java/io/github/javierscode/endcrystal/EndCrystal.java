package io.github.javierscode.endcrystal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.github.javierscode.endcrystal.Commands.stats;
import io.github.javierscode.endcrystal.Listener.onPlayerDeath;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class EndCrystal extends JavaPlugin {

    public static FileConfiguration data;
    public static File dataFile;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new onPlayerDeath(), this);
        Objects.requireNonNull(getCommand("stats")).setExecutor(new stats());

        dataFile = getFile("data.yml");
        data = getConfig(dataFile);

        saveDefaultConfig();
        config = getConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private File getFile(String filename) {
        // get file, create a new one if it doesnt exist
        File file = new File(getDataFolder(), filename);
        if (!file.exists()) {
            // file no exist
            file.getParentFile().mkdirs();
            saveResource(filename, true);
        }
        return file;
    }

    private FileConfiguration getConfig(File file) {
        // load the file's config
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static void createPlayerSection(String playerName, FileConfiguration config, File configFile) {
        // create a section in data.yml for the player
        config.createSection(playerName);
        config.createSection(playerName + ".kills");
        config.createSection(playerName + ".deaths");
        config.createSection(playerName + ".msgs");
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // strings for data.yml
    public static String playerKills(String name) {
        return name + ".kills";
    }
    public static String playerDeaths(String name) {
        return name + ".deaths";
    }
    public static String playerKillMessages(String name) {
        return name + ".killmsgs";
    }
    public static String playerDeathMessages(String name) {
        return name + ".deathmsgs";
    }

    public static int getPlayerKills(String name) {
        return data.getInt(playerKills(name));
    }

    public static int getPlayerDeaths(String name) {
        return data.getInt(playerDeaths(name));
    }

    public static List<String> getPlayerKillMessages(String name) {
        return data.getStringList(playerKillMessages(name));
    }

    public static List<String> getPlayerDeathMessages(String name) {
        return data.getStringList(playerDeathMessages(name));
    }
}
