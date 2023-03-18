package com.gmail.murcisluis.baseplugin.plugin.commands;

import com.gmail.murcisluis.baseplugin.api.Lang;
import com.gmail.murcisluis.baseplugin.api.commands.AbstractCommand;
import com.gmail.murcisluis.baseplugin.api.commands.CommandHandler;
import com.gmail.murcisluis.baseplugin.api.commands.CommandInfo;
import com.gmail.murcisluis.baseplugin.api.commands.TabCompleteHandler;
import com.gmail.murcisluis.baseplugin.api.utils.scheduler.S;

@CommandInfo(
        permission = "bp.admin",
        usage = "/bp reload",
        description = "Reload the plugin."
)
public class ReloadSubCommand extends AbstractCommand  {

    public ReloadSubCommand() {
        super("reload");
    }

    @Override
    public CommandHandler getCommandHandler() {
        return (sender, args) -> {
            S.async(() -> {
                long start = System.currentTimeMillis();
                PLUGIN.reload();
                long end = System.currentTimeMillis();
                Lang.RELOADED.send(sender, end - start);
            });
            return true;
        };
    }

    @Override
    public TabCompleteHandler getTabCompleteHandler() {
        return null;
    }

}