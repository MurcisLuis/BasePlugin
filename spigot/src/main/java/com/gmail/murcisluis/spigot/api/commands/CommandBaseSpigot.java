package com.gmail.murcisluis.spigot.api.commands;


import com.gmail.murcisluis.baseplugin.common.api.commands.AbstractCommand;
import com.gmail.murcisluis.baseplugin.common.api.commands.CommandBase;

import java.util.List;

public interface CommandBaseSpigot extends CommandBase {


    @Override
    CommandHandlerSpigot getCommandHandler();
    @Override
    TabCompleteHandlerSpigot getTabCompleteHandler();

}
