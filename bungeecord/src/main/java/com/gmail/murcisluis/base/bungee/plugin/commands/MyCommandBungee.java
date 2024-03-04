package com.gmail.murcisluis.base.bungee.plugin.commands;

import com.gmail.murcisluis.base.bungee.api.commands.AbstractCommandBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandBaseBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandHandlerBungee;
import com.gmail.murcisluis.base.bungee.api.commands.TabCompleteHandlerBungee;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.plugin.commands.MyCommand;
import net.md_5.bungee.api.CommandSender;
@CommandInfo(
        permission = "bp.use",
        usage = "/bp <args>",
        description = "The main command.",
        aliases = {"bp","base","plugin"}
)
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