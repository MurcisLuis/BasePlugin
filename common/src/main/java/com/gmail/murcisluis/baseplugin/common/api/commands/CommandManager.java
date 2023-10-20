package com.gmail.murcisluis.baseplugin.common.api.commands;

import java.util.Collection;
import java.util.Set;

public interface CommandManager {

    /*
     *  General Methods
     */

    void destroy();

    void registerCommand(AbstractCommand command);

    void unregisterCommand(String name);

    void setMainCommand(AbstractCommand command);

    AbstractCommand getMainCommand();

    Set<String> getCommandNames();

    Collection<AbstractCommand> getCommands();

}