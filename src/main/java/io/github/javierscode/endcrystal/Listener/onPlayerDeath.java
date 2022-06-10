package io.github.javierscode.endcrystal.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;
import java.util.Objects;

import static io.github.javierscode.endcrystal.EndCrystal.*;

public class onPlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!Objects.isNull(event.getEntity().getKiller())) {
            Player p = event.getEntity();
            String pname = p.getDisplayName();

            if (!data.contains(pname)) {
                // no section containing the players name (first time dying?)
                createPlayerSection(pname);
            }
            data.set(playerDeaths(pname), getPlayerDeaths(pname) + 1);
            List<String> newDeathList = getPlayerDeathMessages(pname);
            if (getPlayerDeathMessages(pname).size() >= config.getInt("messages.size")) {
                // message history too full
                newDeathList.remove(newDeathList.size() - 1);
                // remove oldest/last message
            }
            newDeathList.add(0, event.getDeathMessage());
            // add message at the top of the list
            data.set(playerDeathMessages(pname), newDeathList);


            // should use a method but mehhhhhh
            Player k = p.getKiller();
            String kname = k.getDisplayName();

            if (!data.contains(kname)) {
                createPlayerSection(kname);
            }
            data.set(playerKills(kname), getPlayerKills(kname) + 1);
            List<String> newKillList = getPlayerKillMessages(kname);
            if (getPlayerKillMessages(kname).size() >= config.getInt("messages.size")) {
                // message history too full
                newKillList.remove(newKillList.size() - 1);
                // remove oldest/last message
            }
            newKillList.add(0, event.getDeathMessage());
            // add message at the top of the list
            data.set(playerKillMessages(kname), newKillList);
        }
    }
}
