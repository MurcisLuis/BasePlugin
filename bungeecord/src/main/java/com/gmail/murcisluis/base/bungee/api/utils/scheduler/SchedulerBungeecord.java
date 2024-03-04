package com.gmail.murcisluis.base.bungee.api.utils.scheduler;

import com.gmail.murcisluis.base.bungee.api.BaseBungee;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.scheduler.Scheduler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.concurrent.TimeUnit;

public class SchedulerBungeecord implements Scheduler<ScheduledTask> {

    private final BaseBungee BASE = (BaseBungee) BaseAPIFactory.getAPI().get();
    @Override
    public void stopTask(int id) {
        ProxyServer.getInstance().getScheduler().cancel(id);
    }
    @Override
    public void sync(Runnable runnable) {
        ProxyServer.getInstance().getScheduler().runAsync(BASE.getPlugin(), runnable);
    }
    @Override
    public ScheduledTask sync(Runnable runnable, long delay) {
        return ProxyServer.getInstance().getScheduler().schedule(BASE.getPlugin(), runnable, delay, TimeUnit.MILLISECONDS);
    }
    @Override
    public ScheduledTask syncTask(Runnable runnable, long interval) {
        return ProxyServer.getInstance().getScheduler().schedule(BASE.getPlugin(), runnable, 0, interval, TimeUnit.MILLISECONDS);
    }
    @Override
    public void async(Runnable runnable) {
        ProxyServer.getInstance().getScheduler().runAsync(BASE.getPlugin(), runnable);
    }
    @Override
    public void async(Runnable runnable, long delay) {
        ProxyServer.getInstance().getScheduler().schedule(BASE.getPlugin(), runnable, delay, TimeUnit.MILLISECONDS);
    }
    @Override
    public ScheduledTask asyncTask(Runnable runnable, long interval) {
        return ProxyServer.getInstance().getScheduler().schedule(BASE.getPlugin(), runnable, 0, interval, TimeUnit.MILLISECONDS);
    }
    @Override
    public ScheduledTask asyncTask(Runnable runnable, long interval, long delay) {
        return ProxyServer.getInstance().getScheduler().schedule(BASE.getPlugin(), runnable, delay, interval, TimeUnit.MILLISECONDS);
    }

}
