package com.gmail.murcisluis.base.common.api.utils.scheduler;


public interface Scheduler<S> {

    void stopTask(int id);

    void sync(Runnable runnable);

    S sync(Runnable runnable, long delay);

    S syncTask(Runnable runnable, long interval);

    void async(Runnable runnable);

    void async(Runnable runnable, long delay);

    S asyncTask(Runnable runnable, long interval);

    S asyncTask(Runnable runnable, long interval, long delay);

}