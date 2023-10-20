package com.gmail.murcisluis.baseplugin.common.plugin.commands;

import com.gmail.murcisluis.baseplugin.common.api.commands.*;
import com.gmail.murcisluis.baseplugin.common.api.utils.scheduler.S;

@CommandInfo(
        permission = "bp.admin",
        usage = "/bp reload",
        description = "Reload the plugin."
)
public interface ReloadSubCommand<T> extends CommandBase {

    @Override
    default CommandHandler<T> getCommandHandler() {
        return (sender, args) -> {
            S.async(() -> {
                long start = System.currentTimeMillis();
                reload();
                long end = System.currentTimeMillis();
            });
            return true;
        };
    }

    @Override
    default TabCompleteHandler<T> getTabCompleteHandler() {
        return null;
    }


    abstract void reload();

}