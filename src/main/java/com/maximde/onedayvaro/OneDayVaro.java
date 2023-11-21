package com.maximde.onedayvaro;

import com.maximde.onedayvaro.commands.Pause;
import com.maximde.onedayvaro.commands.Resume;
import com.maximde.onedayvaro.commands.Start;
import com.maximde.onedayvaro.listeners.EntityDamageListener;
import com.maximde.onedayvaro.listeners.PlayerInteractListener;
import com.maximde.onedayvaro.listeners.PlayerJoinListener;
import com.maximde.onedayvaro.listeners.PlayerMoveListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.HashMap;
import java.util.UUID;

public final class OneDayVaro extends JavaPlugin {

    @Getter @Setter
    private boolean paused = true;
    public HashMap<UUID, String> playerLocations = new HashMap<>();

    @Override
    public void onEnable() {
        getCommand("pause").setExecutor(new Pause(this));
        getCommand("resume").setExecutor(new Resume(this));
        getCommand("start").setExecutor(new Start(this));

        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
    }

}
