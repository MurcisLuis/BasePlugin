package com.gmail.murcisluis.base.spigot.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.plugin.commands.ReloadSubCommand;
import com.gmail.murcisluis.base.spigot.api.Lang;
import com.gmail.murcisluis.base.spigot.api.Settings;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandBaseSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandHandlerSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.TabCompleteHandlerSpigot;
import com.gmail.murcisluis.base.spigot.api.emotes.Data;
import com.gmail.murcisluis.base.spigot.api.emotes.EmotesPlaceholder;
import com.gmail.murcisluis.base.spigot.api.emotes.manager.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandInfo(
        permission = "le.admin",
        usage = "/le reload",
        description = "The reload command."
)
public class ReloadCommand extends AbstractCommandSpigot implements CommandBaseSpigot,ReloadSubCommand<CommandSender> {

    public ReloadCommand() {
        super("reload");
    }

    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            CommandHandler<CommandSender> handler = ReloadSubCommand.super.getCommandHandler();
            return handler.handle(sender, args);
        };
    }

    @Override
    public TabCompleteHandlerSpigot getTabCompleteHandler() {
        return null;
    }

    @Override
    public void reload() {
        Settings.reload();
        Lang.reload();
        Data.reload();
        MenuManager.onDisable();
        MenuManager.onEnable();
    }


}
