package com.gmail.murcisluis.base.spigot.api.utils.scheduler;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.DExecutor;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.scheduler.BukkitTask;

public class S {

    private static final BaseSpigot BASE = (BaseSpigot) BaseAPIFactory.getAPI().get();

    public static void stopTask(int id) {
        Bukkit.getScheduler().cancelTask(id);
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(BASE.getPlugin(), runnable);
    }

    public static BukkitTask sync(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(BASE.getPlugin(), runnable, delay);
    }

    public static BukkitTask syncTask(Runnable runnable, long interval) {
        return Bukkit.getScheduler().runTaskTimer(BASE.getPlugin(), runnable, 0, interval);
    }

    public static void async(Runnable runnable) {
        try {
            Bukkit.getScheduler().runTaskAsynchronously(BASE.getPlugin(), runnable);
        } catch (IllegalPluginAccessException e) {
            DExecutor.execute(runnable);
        }
    }

    public static void async(Runnable runnable, long delay) {
        try {
            Bukkit.getScheduler().runTaskLaterAsynchronously(BASE.getPlugin(), runnable, delay);
        } catch (IllegalPluginAccessException e) {
            DExecutor.execute(runnable);
        }
    }

    public static BukkitTask asyncTask(Runnable runnable, long interval) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(BASE.getPlugin(), runnable, 0, interval);
    }

    public static BukkitTask asyncTask(Runnable runnable, long interval, long delay) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(BASE.getPlugin(), runnable, delay, interval);
    }

}