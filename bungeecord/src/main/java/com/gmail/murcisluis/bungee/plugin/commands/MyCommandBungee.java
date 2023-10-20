package com.gmail.murcisluis.bungee.plugin.commands;

import com.gmail.murcisluis.baseplugin.common.api.commands.CommandHandler;
import com.gmail.murcisluis.baseplugin.common.plugin.commands.MyCommand;
import com.gmail.murcisluis.bungee.api.commands.AbstractCommandBungee;
import com.gmail.murcisluis.bungee.api.commands.CommandBaseBungee;
import com.gmail.murcisluis.bungee.api.commands.CommandHandlerBungee;
import com.gmail.murcisluis.bungee.api.commands.TabCompleteHandlerBungee;
import net.md_5.bungee.api.CommandSender;

public class MyCommandBungee extends AbstractCommandBungee implements CommandBaseBungee, MyCommand<CommandSender> {

    public MyCommandBungee() {
        super("bbase");
    }

    @Override
    public CommandHandlerBungee getCommandHandler() {
        return (sender, args) -> {
            CommandHandler<CommandSender> handler = MyCommand.super.getCommandHandler();
            return handler.handle(sender, args);
        };
    }

    @Override
    public TabCompleteHandlerBungee getTabCompleteHandler() {
        return null;
    }



}