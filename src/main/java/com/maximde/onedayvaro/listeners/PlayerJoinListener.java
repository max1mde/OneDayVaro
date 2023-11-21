package com.maximde.onedayvaro.listeners;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record PlayerJoinListener(OneDayVaro oneDayVaro) implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

    }
}
