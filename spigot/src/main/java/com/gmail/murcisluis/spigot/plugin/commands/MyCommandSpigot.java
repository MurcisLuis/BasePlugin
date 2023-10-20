package com.gmail.murcisluis.spigot.plugin.commands;

import com.gmail.murcisluis.baseplugin.common.api.commands.CommandHandler;
import com.gmail.murcisluis.baseplugin.common.plugin.commands.MyCommand;
import com.gmail.murcisluis.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.spigot.api.commands.CommandBaseSpigot;
import com.gmail.murcisluis.spigot.api.commands.CommandHandlerSpigot;
import com.gmail.murcisluis.spigot.api.commands.TabCompleteHandlerSpigot;
import org.bukkit.command.CommandSender;

public class MyCommandSpigot extends AbstractCommandSpigot implements CommandBaseSpigot,MyCommand<CommandSender> {

    public MyCommandSpigot() {
        super("base");
    }

    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            CommandHandler<CommandSender> handler = MyCommand.super.getCommandHandler();
            return handler.handle(sender, args);
        };
    }

    @Override
    public TabCompleteHandlerSpigot getTabCompleteHandler() {
        return null;
    }



}
