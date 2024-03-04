package com.gmail.murcisluis.base.common.api.utils.scheduler;

public class S {

    private static Scheduler<?> instance;


    public static void setInstance(Scheduler<?> scheduler) {
        instance = scheduler;
    }

    public static Scheduler<?> getInstance() {
        return instance;
    }

    public static void stopTask(int id){
        instance.stopTask(id);
    }

    public static void sync(Runnable runnable){
        instance.sync(runnable);
    }

    public static <T> T sync(Runnable runnable, long delay){
        return (T) instance.sync(runnable,delay);
    }

    public static <T> T syncTask(Runnable runnable, long interval){
        return (T) instance.syncTask(runnable, interval);
    }

    public static void async(Runnable runnable){
        instance.async(runnable);
    }

    public static void async(Runnable runnable, long delay){
        instance.async(runnable, delay);
    }

    public static <T> T asyncTask(Runnable runnable, long interval){
        return (T) instance.asyncTask(runnable, interval);
    }

    public static <T> T asyncTask(Runnable runnable, long interval, long delay){
        return (T) instance.asyncTask(runnable, interval, delay);
    }
}
