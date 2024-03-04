package com.gmail.murcisluis.base.spigot.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandBaseSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandHandlerSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.TabCompleteHandlerSpigot;
import com.gmail.murcisluis.base.spigot.api.emotes.inventory.MenuAdmin;
import org.bukkit.entity.Player;

@CommandInfo(
        permission = "lujoemotes.admin",
        usage = "/le admin",
        description = "The admin command to select emotes available for players."
)
public class AdminSubCommand extends AbstractCommandSpigot implements CommandBaseSpigot{

    public AdminSubCommand() {
        super("admin");
    }

    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            new MenuAdmin((Player) sender);
            return true;
        };
    }

    @Override
    public TabCompleteHandlerSpigot getTabCompleteHandler() {
        return null;
    }
}
