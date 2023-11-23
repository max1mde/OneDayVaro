package com.maximde.onedayvaro.listeners;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public record PlayerJoinListener(OneDayVaro oneDayVaro) implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(!oneDayVaro.isPaused()) {
            event.getPlayer().kickPlayer(ChatColor.RED + "Das Varo event hat schon begonnen!" + "\n \n" + ChatColor.DARK_PURPLE + "Wenn du zuschauen willst gehe auf twitch.com/yPeat");
        }
    }
}
