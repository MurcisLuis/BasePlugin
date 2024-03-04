package com.gmail.murcisluis.base.common.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.TabCompleteHandler;
import com.gmail.murcisluis.base.common.api.utils.config.Phrase;

public interface MyCommand<T> extends CommandBase {

    Phrase getHelp();
    Phrase getUnknownSubCommand();
    void sendVersion(T sender);



    @Override
    default CommandHandler<T> getCommandHandler() {
        return (sender, args) -> {
            if (args.length == 0) {
                getHelp().send(sender);
                return true;
            }
            getUnknownSubCommand().send(sender);
            getHelp().send(sender);
            sendVersion(sender);
            return true;
        };
    }

    @Override
    default TabCompleteHandler<T> getTabCompleteHandler() {
        return null;
    }



}
