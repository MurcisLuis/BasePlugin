package com.gmail.murcisluis.base.spigot.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.utils.config.Phrase;
import com.gmail.murcisluis.base.spigot.api.Lang;
import com.gmail.murcisluis.base.spigot.api.commands.TabCompleteHandlerSpigot;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.plugin.commands.MyCommand;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandBaseSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandHandlerSpigot;
import com.gmail.murcisluis.base.spigot.api.emotes.inventory.MenuAdmin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@CommandInfo(
        permission = "lujoemotes.use",
        usage = "/le <args>",
        description = "The main command.",
        aliases = {"le","lujoemotes","emotes"}
)
public class MyCommandSpigot extends AbstractCommandSpigot implements CommandBaseSpigot,MyCommand<CommandSender> {

    public MyCommandSpigot() {
        super("lujoemotes");
        addSubCommand(new HelpSubCommand());
        addSubCommand(new AdminSubCommand());
        addSubCommand(new MenuSubCommand());
        addSubCommand(new ReloadCommand());

    }

    @Override
    public Phrase getHelp() {
        return Lang.USE_HELP;
    }

    @Override
    public Phrase getUnknownSubCommand() {
        return Lang.UNKNOWN_SUB_COMMAND;
    }

    @Override
    public void sendVersion(CommandSender sender) {
        Lang.sendVersionMessage(sender);
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
