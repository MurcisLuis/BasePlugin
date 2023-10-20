package com.gmail.murcisluis.bungee.api.commands;

import com.gmail.murcisluis.baseplugin.common.api.commands.CommandBase;
import com.gmail.murcisluis.baseplugin.common.api.commands.CommandHandler;
import com.gmail.murcisluis.baseplugin.common.api.commands.TabCompleteHandler;

public interface CommandBaseBungee extends CommandBase {
    @Override
    CommandHandlerBungee getCommandHandler();
    @Override
    TabCompleteHandlerBungee getTabCompleteHandler();

}
