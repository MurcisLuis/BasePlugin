package com.gmail.murcisluis.base.common.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.commands.TabCompleteHandler;
import com.gmail.murcisluis.base.common.api.utils.scheduler.S;

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