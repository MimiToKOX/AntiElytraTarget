package com.top1.antielytratarget;

import com.top1.antielytratarget.checks.AntiElytraTarget;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(new AntiElytraTarget(), this);
        logStartupMessage();
    }

    @Override
    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
    }


    private void logStartupMessage() {
        String[] lines = {
                "[=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=]",
                "|                                             |",
                "|              AntiElytraTarget               |",
                "|               Jest włączony!                |",
                "|                                             |",
                "[=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=]"
        };
        for (String line : lines) {
            getLogger().info(line);
        }
    }

}
