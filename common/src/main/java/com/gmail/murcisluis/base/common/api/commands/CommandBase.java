package com.gmail.murcisluis.base.common.api.commands;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CommandBase {

    String getName();

    String getPermission();

    List<String> getAliasesL();

    boolean isPlayerOnly();

    int getMinArgs();

    String getUsage();

    String getDescription();

    CommandBase addSubCommand(CommandBase commandBase);

    CommandBase getSubCommand(String name);

    Set<String> getSubCommandNames();

    Collection<CommandBase> getSubCommands();

    CommandHandler getCommandHandler();

    TabCompleteHandler getTabCompleteHandler();


}
