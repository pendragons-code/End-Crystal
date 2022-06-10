package io.github.javierscode.endcrystal.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javatuples.Pair;

import static io.github.javierscode.endcrystal.EndCrystal.*;

public class stats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("You need to specify a section!");
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can only use this command as an player!");
            return true;
        }
        Player p = (Player) sender;
        switch (args[0]) {
            case "kills":
                p.sendMessage(ChatColor.RED + "Your total kills are: " + ChatColor.BOLD + getPlayerKills(p.getDisplayName()));
                return true;
            case "deaths":
                p.sendMessage(ChatColor.RED + "Your total deaths are: " + ChatColor.BOLD + getPlayerDeaths(p.getDisplayName()));
                return true;
            case "recentkills":
                p.sendMessage(ChatColor.RED + "Your recent kill messages were:");
                for (String s : getPlayerKillMessages(p.getDisplayName())) {
                    p.sendMessage(s);
                }
                return true;
            case "recentdeaths":
                p.sendMessage(ChatColor.RED + "Your recent death messages were:");
                for (String s : getPlayerDeathMessages(p.getDisplayName())) {
                    p.sendMessage(s);
                }
                return true;
            default:
                sender.sendMessage("Unknown section specified!");
                return false;
        }
    }
}
