package com.gmail.murcisluis.baseplugin.commands;

import com.gmail.murcisluis.baseplugin.api.commands.AbstractCommand;
import com.gmail.murcisluis.baseplugin.api.commands.CommandHandler;
import com.gmail.murcisluis.baseplugin.api.commands.TabCompleteHandler;

public class MyCommand extends AbstractCommand {
    public MyCommand() {
        super("baseplugin");
    }

    @Override
    public CommandHandler getCommandHandler() {
        return null;
    }

    @Override
    public TabCompleteHandler getTabCompleteHandler() {
        return null;
    }
}
