package com.top1.antielytratarget.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleUtil {

    public static void sendTitle(Player player, String title, String subtitle) {
        if (player != null) {
            player.sendTitle(
                    ChatColor.translateAlternateColorCodes('&', title),
                    ChatColor.translateAlternateColorCodes('&', subtitle),
                    10, 70, 20
            );
        }
    }

    public static void sendTitleAll(String title, String subtitle) {
        String formattedTitle = ChatColor.translateAlternateColorCodes('&', title);
        String formattedSubtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(formattedTitle, formattedSubtitle, 10, 70, 20);
        }
    }
}
