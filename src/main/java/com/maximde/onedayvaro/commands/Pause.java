package com.maximde.onedayvaro.commands;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public record Pause(OneDayVaro oneDayVaro) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Keine Rechte!");
            return false;
        }

        if(oneDayVaro.isPaused()) {
            sender.sendMessage(ChatColor.RED + "Das Spiel wurde schon pausiert! Nutze /resume um es wieder zu starten!");
            return false;
        }

        oneDayVaro.setPaused(true);
        sender.sendMessage(ChatColor.GREEN + "Du hast das Spiel pausiert!");

        return false;
    }
}
