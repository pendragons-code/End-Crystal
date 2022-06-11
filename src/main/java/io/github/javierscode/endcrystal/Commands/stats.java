package io.github.javierscode.endcrystal.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.javierscode.endcrystal.EndCrystal.*;

public class stats implements CommandExecutor {
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
        String targetPlayer;
        if (args.length > 1) {
            targetPlayer = args[1];
            if (!data.contains(targetPlayer)) {
                p.sendMessage(ChatColor.RED + targetPlayer + " doesn't seem to have any stats...");
            }
        } else {
            targetPlayer = p.getDisplayName();
        }
        switch (args[0]) {
            case "kills":
                p.sendMessage(ChatColor.RED + targetPlayer + " has a total of " + ChatColor.BOLD + getPlayerKills(targetPlayer) + " kills!");
                return true;
            case "deaths":
                p.sendMessage(ChatColor.RED + targetPlayer + " has a total of " + ChatColor.BOLD + getPlayerDeaths(targetPlayer) + " deaths!");
                return true;
            case "recentkills":
                if (getPlayerKillMessages(targetPlayer).size() <= 0) {
                    p.sendMessage(ChatColor.RED + targetPlayer + "has no recent kills!");
                    return true;
                }
                p.sendMessage(ChatColor.RED + targetPlayer + "'s recent kill messages were:");
                for (String s : getPlayerKillMessages(targetPlayer)) {
                    p.sendMessage(s);
                }
                return true;
            case "recentdeaths":
                if (getPlayerDeathMessages(targetPlayer).size() <= 0) {
                    p.sendMessage(ChatColor.RED + targetPlayer + "has no recent deaths!");
                    return true;
                }
                p.sendMessage(ChatColor.RED + targetPlayer + "'s recent death messages were:");
                for (String s : getPlayerDeathMessages(targetPlayer)) {
                    p.sendMessage(s);
                }
                return true;
            default:
                sender.sendMessage("Unknown section specified!");
                return false;
        }
    }
}
