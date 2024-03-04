package com.gmail.murcisluis.base.spigot.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandBaseSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandHandlerSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.TabCompleteHandlerSpigot;
import com.gmail.murcisluis.base.spigot.api.emotes.inventory.MenuAdmin;
import com.gmail.murcisluis.base.spigot.api.emotes.inventory.MenuPlayer;
import org.bukkit.entity.Player;

@CommandInfo(
        permission = "lujoemotes.use",
        usage = "/le menu",
        description = "The players command to select emote and preview."
)
public class MenuSubCommand extends AbstractCommandSpigot implements CommandBaseSpigot{
    public MenuSubCommand() {
        super("menu");
    }

    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            new MenuPlayer((Player) sender);
            return true;
        };
    }

    @Override
    public TabCompleteHandlerSpigot getTabCompleteHandler() {
        return null;
    }
}
