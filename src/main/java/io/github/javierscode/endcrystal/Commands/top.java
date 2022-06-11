package io.github.javierscode.endcrystal.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

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
        int amount = 5;
        if (args.length > 1) {
            // specified amount of players to show
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                p.sendMessage(ChatColor.RED + "Unable to read the amount of players to show. Defaulting to 5.");
            }
        }
        int i = 1;
        switch (args[0]) {
            case "kills":
                p.sendMessage(ChatColor.YELLOW + "The top " + amount + " players in kills are: ");
                HashMap<String, Integer> largestKills = findLargest(data, "kills", amount);
                for (String s : largestKills.keySet()) {
                    p.sendMessage(ChatColor.YELLOW + String.valueOf(i) + ": " + s + ", with " + ChatColor.BOLD + largestKills.get(s) + ChatColor.RESET + ChatColor.YELLOW + " deaths!");
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
     * @param amount The amount of players to return.
     * @param type The value needed to find. Should be kills/deaths
     *
     **/
    private HashMap<String, Integer> findLargest(FileConfiguration config, String type, int amount) {
        HashMap<Integer, String> playerValue = new HashMap<>();
        for (String s : config.getKeys(false)) {
            playerValue.put(config.getInt(s + "." + type), s);
        }
        Integer[] intarray = playerValue.keySet().toArray(new Integer[0]);
        Arrays.sort(intarray);
        // sorts from smallest to biggest
        Collections.reverse(Arrays.asList(intarray));
        // reverse from biggest to smallest
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < amount; i++) {
            hashMap.put(playerValue.get(intarray[i]), intarray[i]);
        }
        return hashMap;
    }
}