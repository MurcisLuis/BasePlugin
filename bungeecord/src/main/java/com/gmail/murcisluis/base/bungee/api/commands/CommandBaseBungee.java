package com.gmail.murcisluis.base.bungee.api.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;

public interface CommandBaseBungee extends CommandBase {
    @Override
    CommandHandlerBungee getCommandHandler();
    @Override
    TabCompleteHandlerBungee getTabCompleteHandler();

}
