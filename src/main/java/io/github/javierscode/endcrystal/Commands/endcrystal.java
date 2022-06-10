package io.github.javierscode.endcrystal.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javatuples.Pair;

public class endcrystal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("You need to specify a section!");
            return false;
        }
        Pair<Boolean, Player> pair = checkAndRejectNonPlayer(sender);
        if (pair.getValue0()) {return true;}
        Player p = pair.getValue1();
        switch (args[0]) {
            case "kills":

                return true;
            case "deaths":

                return true;
            case "recentkills":

                return true;
            case "recentdeaths":

                return true;
            default:
                sender.sendMessage("Unknown section.");
                return false;
        }
    }

    public Pair<Boolean, Player> checkAndRejectNonPlayer(CommandSender sender) { //if sender is player
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can only use this command as an player!");
            return new Pair<>(false, null);
        }
        return new Pair<>(true, (Player) sender);
    }
}
