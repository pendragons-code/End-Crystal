package io.github.javierscode.endcrystal.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

import static io.github.javierscode.endcrystal.EndCrystal.config;
import static io.github.javierscode.endcrystal.EndCrystal.data;

public class top implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(args.length > 0)) {
            sender.sendMessage("You need to specify a section!");
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can only use this command as an player!");
            return true;
        }
        Player p = (Player) sender;
        int amount;
        int size = config.getKeys(false).size();
        if (args.length > 1) {
            // specified amount of players to show
            try {
                int i = Integer.parseInt(args[1]);
                if (i > size) {
                    p.sendMessage(ChatColor.RED + "Theres not enough players tracked! Showing " + size + " players.");
                    amount = size;
                } else {
                    amount = i;
                }
            } catch (NumberFormatException e) {
                p.sendMessage(ChatColor.RED + "Unable to read the amount of players to show. Using default numbers.");
                amount = Math.min(size, config.getInt("top.default"));
                // Math.min returns whichever int is smaller
            }

        } else {
            amount = Math.min(size, config.getInt("top.default"));
        }
        int i = 1;
        switch (args[0]) {
            case "kills":
                p.sendMessage(ChatColor.YELLOW + "The top " + amount + " players in kills are: ");
                HashMap<String, Integer> largestKills = findLargest(data, "kills", amount);
                for (String s : largestKills.keySet()) {
                    p.sendMessage(ChatColor.YELLOW + String.valueOf(i) + ": " + s + ", with " + ChatColor.BOLD + largestKills.get(s) + ChatColor.RESET + ChatColor.YELLOW + " kills!");
                    i++;
                }
                return true;
            case "deaths":
                p.sendMessage(ChatColor.YELLOW + "The top " + amount + " players in deaths are: ");
                HashMap<String, Integer> largestDeaths = findLargest(data, "deaths", amount);
                for (String s : largestDeaths.keySet()) {
                    p.sendMessage(ChatColor.YELLOW + String.valueOf(i) + ": " + s + ", with " + ChatColor.BOLD + largestDeaths.get(s) + ChatColor.RESET + ChatColor.YELLOW + " deaths!");
                    i++;
                }
                return true;
            default:
                sender.sendMessage("Unknown section specified!");
                return false;
        }

    }
    /**
     * @param amount The amount of players to return. Must be >0!
     * @param type The value needed to find. Should be kills/deaths
     *
     **/
    private HashMap<String, Integer> findLargest(FileConfiguration config, String type, int amount) {
        HashMap<String, Integer> playerValue = new HashMap<>();
        for (String s : config.getKeys(false)) {
            playerValue.put(s, config.getInt(s + "." + type));
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(playerValue.entrySet());
        // LinkedList is faster in manipulation of data (sorting)
        // look below to see why Map.Entry was used
        list.sort(Map.Entry.comparingByValue());
        // sorts from lowest to highest
        Collections.reverse(list);
        // reverse list order

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        // LinkedHashMap keeps order, HashMap doesnt
        while (temp.size() < amount) {
            for (Map.Entry<String, Integer> entry : list) {
                temp.put(entry.getKey(), entry.getValue());
            }
        }
        /*
        using Map.Entry helps make code look better and faster, compared to:

        for (String s : playerValue.keySet()) {
            temp.put(s, playerValue.get(s));
        }
         */
        return temp;
    }
}