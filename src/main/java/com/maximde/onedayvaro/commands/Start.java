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
        Bukkit.broadcastMessage(ChatColor.RED + "=================== Wichtig! ===================");
        Bukkit.broadcastMessage(ChatColor.RED + "1. Du hast eine Schutzzeit von 5 Minuten");
        Bukkit.broadcastMessage(ChatColor.RED + "2. Verlasse den Server NICHT ansonsten bist du raus");
        Bukkit.broadcastMessage(ChatColor.RED + "3. Wenn du stirbst bist du raus!");
        Bukkit.broadcastMessage(ChatColor.RED + "=================== Wichtig! ===================");
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.playSound(all.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);
            all.sendTitle(ChatColor.GREEN + "Renn!", ChatColor.GREEN + "Es geht los ⚔️",  10, 50, 10);
            all.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, minutes(5), 255));
            all.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, minutes(5), 255));
        }

        player.getWorld().getWorldBorder().setCenter(player.getLocation().getX(), player.getLocation().getZ());
        player.getWorld().getWorldBorder().setSize(1500, 5);
        player.getWorld().setTime(0);
        player.getWorld().setStorm(false);
        player.getWorld().setHardcore(true);
        Bukkit.getScheduler().runTaskLaterAsynchronously(oneDayVaro, this::sendTitleFinal, minutes(5));
        Bukkit.getScheduler().runTaskLaterAsynchronously(oneDayVaro, this::giveCompass, minutes(60));
        return false;
    }

    private void sendTitleFinal() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.playSound(all.getLocation(), Sound.EVENT_RAID_HORN, 1F, 1F);
            all.sendTitle(ChatColor.RED + "Achtung!", ChatColor.RED + "Die Schutzzeit ist zuende!",  10, 50, 10);
        }
    }

    private int minutes(int minutes) {
        return (20 * 60) * minutes;
    }

    private void giveCompass() {
        Bukkit.broadcastMessage(ChatColor.GOLD + "Eine Stunde ist vorbei! Jeder hat einen Kompass bekommen");

        for(Player player : Bukkit.getOnlinePlayers()) {
            var item = new ItemStack(Material.COMPASS);
            var itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.RED + "Compass");
            item.setItemMeta(itemMeta);
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
            var target = getNearest(player);
            if(target != null) player.setCompassTarget(target.getLocation());
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
