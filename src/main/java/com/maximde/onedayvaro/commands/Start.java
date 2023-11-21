package com.maximde.onedayvaro.commands;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public record Start(OneDayVaro oneDayVaro) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Keine Rechte!");
            return false;
        }

        if(!oneDayVaro.isPaused()) {
            sender.sendMessage(ChatColor.RED + "Varo hat schon gestartet!");
            return false;
        }
        oneDayVaro.setPaused(false);
        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "DAS VARO EVENT HAT BEGONNEN!");
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
            player.sendMessage(ChatColor.RED + "Renn um dein Leben!");
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 255));
        }
        if(sender instanceof Player player) {
            player.getWorld().setTime(0);
            player.getWorld().setStorm(false);
            player.getWorld().setHardcore(true);
        }
        return false;
    }
}
