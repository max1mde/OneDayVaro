package com.maximde.onedayvaro.listeners;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public record PlayerDeathListener(OneDayVaro oneDayVaro) implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getEntity().getWorld().getWorldBorder().setSize(event.getEntity().getWorldBorder().getSize() - 50, 10);
        if(!oneDayVaro.isPaused()) {
            int size = 0;
            Player finalP = null;
            for(Player player : Bukkit.getOnlinePlayers()) {
                if(player.getGameMode() == GameMode.SURVIVAL) {
                    size++;
                    finalP = player;
                }
            }
            if(size == 1) {
                oneDayVaro.setPaused(true);
                Bukkit.broadcastMessage(ChatColor.GOLD + finalP.getName() + " hat das varo event gewonnen!");
            }
        }
    }
}
