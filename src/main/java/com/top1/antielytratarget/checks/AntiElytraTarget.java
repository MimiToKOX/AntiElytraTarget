package com.top1.antielytratarget.checks;

import com.top1.antielytratarget.Main;
import com.top1.antielytratarget.utils.MessageUtil;
import com.top1.antielytratarget.utils.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AntiElytraTarget implements Listener {

    private final Map<UUID, Integer> attackCount = new HashMap<>();
    private final Map<UUID, Integer> checks = new HashMap<>();

    public AntiElytraTarget() {
        new BukkitRunnable() {
            @Override
            public void run() {
                checks.clear();
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.hasPermission("antielytrattarget.alerts")) {
                        MessageUtil.sendMessage(onlinePlayer, "&8[&cAntiElytraTarget&8] &7Wszyscy gracze zosali zresetowani!");
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getInstance(), 6000L, 6000L);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();
        UUID attackerId = attacker.getUniqueId();

        if (!attacker.isGliding() || !isAirAround(attacker.getLocation())) return;

        attackCount.put(attackerId, attackCount.getOrDefault(attackerId, 0) + 1);

        if (attackCount.get(attackerId) >= 3) {
            checks.put(attackerId, checks.getOrDefault(attackerId, 0) + 1);
            Vector knockback = attacker.getLocation().getDirection().multiply(-1).setY(0.5);
            attacker.setVelocity(knockback);

            int checkCount = checks.get(attackerId);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("antielytrattarget.alerts")) {
                    MessageUtil.sendMessage(onlinePlayer, "&8[&cAntiElytraTarget&8] &7Gracz &c" + attacker.getName() + " &7failed &cElytraTarget &8[&c" + checkCount + "&7/5&8]");
                }
            }

            if (checkCount == 3) {
                MessageUtil.sendError(attacker, "&cEjejej! Kolego nie używaj elytra targeta");
                attacker.playSound(attacker.getLocation(), org.bukkit.Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                TitleUtil.sendTitle(attacker, "&4Off cheaty!", "&cKolego wyłącz cheaty");
            }

            if (checkCount >= 5) {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + attacker.getName() + " 31d AntiElytraTarget > Komar (bzzz)");
                });
            }

            attackCount.put(attackerId, 0);
        }
    }

    private boolean isAirAround(Location location) {
        for (int x = -4; x <= 4; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -4; z <= 4; z++) {
                    if (!location.clone().add(x, y, z).getBlock().getType().equals(Material.AIR)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
