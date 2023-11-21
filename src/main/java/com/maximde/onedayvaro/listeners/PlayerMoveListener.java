package com.maximde.onedayvaro.listeners;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public record PlayerMoveListener(OneDayVaro oneDayVaro) implements Listener {

    @EventHandler
    public void onInteract(PlayerMoveEvent event) {
        if(!event.getPlayer().isOp() && oneDayVaro.isPaused()) event.setCancelled(true);
    }

}
