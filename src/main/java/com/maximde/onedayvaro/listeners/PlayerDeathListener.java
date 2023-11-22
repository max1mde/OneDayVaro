package com.maximde.onedayvaro.listeners;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public record PlayerDeathListener(OneDayVaro oneDayVaro) implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(event.getEntity().getWorld().getWorldBorder().getSize() > 50) {
            event.getEntity().getWorld().getWorldBorder().setSize(event.getEntity().getWorld().getWorldBorder().getSize() - 50, 10);
        }
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
                Bukkit.broadcastMessage(ChatColor.GOLD + finalP.getName() + " hat das varo event gewonnen! " + ChatColor.MAGIC + "mmm");
                for(Player all : Bukkit.getOnlinePlayers()) {
                    all.playSound(all.getLocation(), Sound.EVENT_RAID_HORN, 1F, 1F);
                    all.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + finalP.getName(), ChatColor.GOLD + "Ist der Gewinner!",  10, 100, 10);
                }
            }
        }
    }
}
