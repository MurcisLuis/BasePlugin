package com.gmail.murcisluis.base.common.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.Lang;
import com.gmail.murcisluis.base.common.api.commands.TabCompleteHandler;

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
