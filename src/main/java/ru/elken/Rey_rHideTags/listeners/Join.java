package ru.elken.Rey_rHideTags.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.elken.Rey_rHideTags.Main;

public class Join implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Main.hideName(event.getPlayer());
    }
}
