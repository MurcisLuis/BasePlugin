package com.gmail.murcisluis.baseplugin.plugin.commands;

import com.gmail.murcisluis.baseplugin.api.Lang;
import com.gmail.murcisluis.baseplugin.api.commands.AbstractCommand;
import com.gmail.murcisluis.baseplugin.api.commands.CommandHandler;
import com.gmail.murcisluis.baseplugin.api.commands.CommandInfo;
import com.gmail.murcisluis.baseplugin.api.commands.TabCompleteHandler;
@CommandInfo(
        permission = "bp.use",
        usage = "/bp <args>",
        description = "The main command."
)
public class MyCommand extends AbstractCommand {
    public MyCommand() {
        super("baseplugin");

        addSubCommand(new ReloadSubCommand());
    }

    @Override
    public CommandHandler getCommandHandler() {
        return (sender, args) -> {
            if (sender.hasPermission(getClass().getAnnotation(CommandInfo.class).permission())) {
                if (args.length == 0) {
                    Lang.USE_HELP.send(sender);
                    return true;
                }
                Lang.UNKNOWN_SUB_COMMAND.send(sender);
                Lang.USE_HELP.send(sender);
            } else {
                Lang.sendVersionMessage(sender);
            }
            return true;
        };
    }

    @Override
    public TabCompleteHandler getTabCompleteHandler() {
        return null;
    }
}
