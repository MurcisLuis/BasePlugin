package com.gmail.murcisluis.base.spigot.api.utils.scheduler;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.DExecutor;
import com.gmail.murcisluis.base.common.api.utils.scheduler.Scheduler;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.scheduler.BukkitTask;

public class SchedulerSpigot implements Scheduler<BukkitTask> {

    private static final BaseSpigot BASE = (BaseSpigot) BaseAPIFactory.getAPI().get();
    @Override
    public void stopTask(int id) {
        Bukkit.getScheduler().cancelTask(id);
    }
    @Override
    public void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(BASE.getPlugin(), runnable);
    }
    @Override
    public BukkitTask sync(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(BASE.getPlugin(), runnable, delay);
    }
    @Override
    public BukkitTask syncTask(Runnable runnable, long interval) {
        return Bukkit.getScheduler().runTaskTimer(BASE.getPlugin(), runnable, 0, interval);
    }
    @Override
    public void async(Runnable runnable) {
        try {
            Bukkit.getScheduler().runTaskAsynchronously(BASE.getPlugin(), runnable);
        } catch (IllegalPluginAccessException e) {
            DExecutor.execute(runnable);
        }
    }
    @Override
    public void async(Runnable runnable, long delay) {
        try {
            Bukkit.getScheduler().runTaskLaterAsynchronously(BASE.getPlugin(), runnable, delay);
        } catch (IllegalPluginAccessException e) {
            DExecutor.execute(runnable);
        }
    }
    @Override
    public BukkitTask asyncTask(Runnable runnable, long interval) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(BASE.getPlugin(), runnable, 0, interval);
    }
    @Override
    public BukkitTask asyncTask(Runnable runnable, long interval, long delay) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(BASE.getPlugin(), runnable, delay, interval);
    }
}
