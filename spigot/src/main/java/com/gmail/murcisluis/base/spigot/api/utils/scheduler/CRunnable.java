package com.gmail.murcisluis.base.spigot.api.utils.scheduler;

import com.gmail.murcisluis.base.common.api.utils.scheduler.S;
import org.bukkit.scheduler.BukkitTask;

public abstract class CRunnable implements Runnable{
    protected BukkitTask task;


    public void start() {
        if (task != null) {
            task.cancel();
        }
        task =S.asyncTask(this, 20L);
    }


    public void start(long interval) {
        if (task != null) {
            task.cancel();
        }
        task = S.asyncTask(this, interval);
    }

    public void cancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
