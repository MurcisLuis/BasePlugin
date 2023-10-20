package com.gmail.murcisluis.baseplugin.common.plugin.commands;

import com.gmail.murcisluis.baseplugin.common.api.Lang;
import com.gmail.murcisluis.baseplugin.common.api.commands.*;
import com.gmail.murcisluis.baseplugin.common.api.utils.scheduler.S;

@CommandInfo(
        permission = "bp.use",
        usage = "/bp <args>",
        description = "The main command.",
        aliases = {"bp","base","plugin"}
)
public interface MyCommand<T>  extends CommandBase {

    @Override
    default CommandHandler<T> getCommandHandler() {
        return (sender, args) -> {
            if (args.length == 0) {
                Lang.USE_HELP.send(sender);
                return true;
            }
            Lang.UNKNOWN_SUB_COMMAND.send(sender);
            Lang.USE_HELP.send(sender);
            Lang.sendVersionMessage(sender);
            return true;
        };
    }

    @Override
    default TabCompleteHandler<T> getTabCompleteHandler() {
        return null;
    }

}
