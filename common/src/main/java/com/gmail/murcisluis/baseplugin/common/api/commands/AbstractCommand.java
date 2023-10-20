package com.gmail.murcisluis.baseplugin.common.api.commands;

import com.gmail.murcisluis.baseplugin.common.api.exception.AbstractCommandException;
import com.gmail.murcisluis.baseplugin.common.api.utils.Common;
import org.apache.commons.lang3.Validate;

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



