package com.gmail.murcisluis.base.common.api.commands;

import com.gmail.murcisluis.base.common.api.exception.AbstractCommandException;

import java.util.*;

public interface AbstractCommand extends CommandBase {

    Set<String> getSubCommandNames();

    Collection<CommandBase> getSubCommands();

    CommandBase getSubCommand(String name);

    AbstractCommand addSubCommand(CommandBase commandBase);

    void execute(Object sender, String[] args);

    boolean handle(Object sender, String[] args) throws AbstractCommandException;

    List<String> handleTabComplete(Object sender, String[] args);

    @Override
    String getPermission();

    @Override
    boolean isPlayerOnly();

    @Override
    int getMinArgs();

    @Override
    String getUsage();

    @Override
    String getDescription();

}



