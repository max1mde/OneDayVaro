package com.maximde.onedayvaro.listeners;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


public record EntityDamageListener(OneDayVaro oneDayVaro) implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(oneDayVaro.isPaused()) event.setCancelled(true);
    }

}
