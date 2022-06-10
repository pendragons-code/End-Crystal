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
            List<String> newList = getPlayerMessages(pname);
            if (getPlayerMessages(pname).size() >= config.getInt("messages.size")) {
                // message history too full
                newList.remove(newList.size() - 1);
                // remove oldest/last message
            }
            newList.add(0, event.getDeathMessage());
            // add message at the top of the list
            data.set(playerMessages(pname), newList);



            Player k = p.getKiller();
            String kname = k.getDisplayName();

            if (!data.contains(kname)) {
                createPlayerSection(kname);
            }
            data.set(playerKills(kname), getPlayerKills(kname) + 1);
        }
    }
}
