package com.gmail.murcisluis.base.spigot.api.commands;


import com.gmail.murcisluis.base.common.api.commands.CommandBase;

public interface CommandBaseSpigot extends CommandBase {


    @Override
    CommandHandlerSpigot getCommandHandler();
    @Override
    TabCompleteHandlerSpigot getTabCompleteHandler();

}
