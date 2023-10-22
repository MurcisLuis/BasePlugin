package com.gmail.murcisluis.base.spigot.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.spigot.api.commands.TabCompleteHandlerSpigot;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.plugin.commands.MyCommand;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandBaseSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandHandlerSpigot;
import org.bukkit.command.CommandSender;
@CommandInfo(
        permission = "bp.use",
        usage = "/bp <args>",
        description = "The main command.",
        aliases = {"bp","base","plugin"}
)
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
