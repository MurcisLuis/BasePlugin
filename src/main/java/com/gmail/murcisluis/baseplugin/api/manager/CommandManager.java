package com.gmail.murcisluis.baseplugin.api.manager;

import com.gmail.murcisluis.baseplugin.api.commands.AbstractCommand;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandManager {

    public final static String COMMAND_PACKAGE="com.gmail.murcisluis.baseplugin.commands";

    public Map<String,AbstractCommand> commands = new HashMap<>();

    public void init(){
        Reflections reflections = new Reflections(COMMAND_PACKAGE);
        Set<Class<? extends AbstractCommand>> commandClasses = reflections.getSubTypesOf(AbstractCommand.class);

        for (Class<? extends AbstractCommand> commandClass : commandClasses) {
            try {
                Constructor<? extends AbstractCommand> constructor = commandClass.getConstructor();
                AbstractCommand command = constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void registerCommands(AbstractCommand abstractCommand) {


    }

    public void unregisterCommands(AbstractCommand abstractCommand) {


    }
}
