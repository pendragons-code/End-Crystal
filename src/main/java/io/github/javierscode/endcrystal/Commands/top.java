package io.github.javierscode.endcrystal.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.javierscode.endcrystal.EndCrystal.getPlayerDeaths;
import static io.github.javierscode.endcrystal.EndCrystal.getPlayerKills;

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
        switch (args[0]) {
            case "kills":
                p.sendMessage(ChatColor.YELLOW + "The top " + amount + " players in kills are: ");
                return true;
            case "deaths":
                p.sendMessage(ChatColor.YELLOW + "The top " + amount + " players in deaths are: ");
                return true;
            default:
                sender.sendMessage("Unknown section specified!");
                return false;
        }

    }
    private void findLargest(String type) {

    }
    }                                                                                              
