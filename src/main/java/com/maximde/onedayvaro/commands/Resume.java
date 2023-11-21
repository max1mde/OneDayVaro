package com.maximde.onedayvaro.commands;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public record Resume(OneDayVaro oneDayVaro) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Keine Rechte!");
            return false;
        }

        if(!oneDayVaro.isPaused()) {
            sender.sendMessage(ChatColor.RED + "Das Spiel wurde nicht pausiert!");
            return false;
        }

        oneDayVaro.setPaused(false);
        sender.sendMessage(ChatColor.GREEN + "Du hast das Spiel gestartet!");

        return false;
    }
}
