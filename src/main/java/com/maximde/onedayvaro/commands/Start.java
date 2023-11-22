package com.maximde.onedayvaro.commands;

import com.maximde.onedayvaro.OneDayVaro;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public record Start(OneDayVaro oneDayVaro) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "You can only execute this command as a player!");
            return false;
        }

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

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.playSound(all.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F, 1F);
            all.sendTitle(ChatColor.RED + "Renn um dein Leben!", "",  10, 50, 10);
            all.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60, 255));
        }

        player.getWorld().getWorldBorder().setCenter(player.getLocation().getX(), player.getLocation().getZ());
        player.getWorld().getWorldBorder().setSize(1000, 5);
        player.getWorld().setTime(0);
        player.getWorld().setStorm(false);
        player.getWorld().setHardcore(true);
        Bukkit.getScheduler().runTaskLaterAsynchronously(oneDayVaro, this::giveCompass, (20L * 60) * 60);
        return false;
    }

    private void giveCompass() {
        Bukkit.broadcastMessage(ChatColor.GOLD + "1h has passed and everyone got a compass!");

        for(Player player : Bukkit.getOnlinePlayers()) {
            var item = new ItemStack(Material.COMPASS);
            item.getItemMeta().setDisplayName(ChatColor.RED + "Compass");
            player.getInventory().addItem(item);
        }

        final BukkitRunnable repeatingTask = new BukkitRunnable() {
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    updateCompass();
                }
            }
        };
        repeatingTask.runTaskTimerAsynchronously(oneDayVaro, 20, 20 * 5);
    }

    private void updateCompass() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.setCompassTarget(getNearest(player).getLocation());
        }
    }

    private Player getNearest(Player player) {
        double distance = Double.MAX_VALUE;
        Player target = null;
        for (Player all : Bukkit.getOnlinePlayers()) {
            if(!all.getGameMode().equals(GameMode.SURVIVAL)) continue;
            if(all == player) continue;
            double dis = player.getLocation().distance(all.getLocation());
            if (dis < distance) {
                distance = dis;
                target = all;
            }
        }
        return target;
    }
}
