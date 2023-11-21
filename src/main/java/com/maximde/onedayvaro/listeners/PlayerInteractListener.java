package com.maximde.onedayvaro.listeners;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public record PlayerInteractListener(OneDayVaro oneDayVaro) implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!event.getPlayer().isOp() && oneDayVaro.isPaused()) event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(!event.getPlayer().isOp() && oneDayVaro.isPaused()) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(!event.getPlayer().isOp() && oneDayVaro.isPaused()) event.setCancelled(true);
    }

}
