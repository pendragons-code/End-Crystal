package io.github.javierscode.endcrystal.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static io.github.javierscode.endcrystal.EndCrystal.*;

public class onPlayerDeath implements Listener {

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event) {
        if (!Objects.isNull(event.getEntity().getKiller())) {
            Player p = event.getEntity();
            String pname = p.getDisplayName();

            if (!data.contains(pname)) {
                // no section containing the players name (first time dying?)
                createPlayerSection(pname, data, dataFile);
            }
            data.set(playerDeaths(pname), getPlayerDeaths(pname) + 1);
            // add message at the top of the list
            data.set(playerDeathMessages(pname), getUpdatedList(getPlayerDeathMessages(pname), event.getDeathMessage()));


            Player k = p.getKiller();
            String kname = k.getDisplayName();

            if (!data.contains(kname)) {
                createPlayerSection(kname, data, dataFile);
            }
            data.set(playerKills(kname), getPlayerKills(kname) + 1);
            data.set(playerKillMessages(kname), getUpdatedList(getPlayerKillMessages(kname), event.getDeathMessage()));

            try {
                data.save(dataFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> getUpdatedList(List<String> list, String msg) {
        if (list.size() >= config.getInt("messages.size")) {
            // message history too full
            list.remove(list.size() - 1);
            // remove oldest/last message
        }
        list.add(0, msg);
        return list;
    }
}
