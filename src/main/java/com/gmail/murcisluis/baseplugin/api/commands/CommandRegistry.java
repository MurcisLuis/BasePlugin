package com.gmail.murcisluis.baseplugin.api.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.logging.Level;

public class CommandRegistry {

    public static void registerCommands(JavaPlugin plugin, String commandPackage) {
        Reflections reflections = new Reflections(commandPackage);
        Set<Class<? extends AbstractCommand>> commandClasses = reflections.getSubTypesOf(AbstractCommand.class);

        for (Class<? extends AbstractCommand> commandClass : commandClasses) {
            try {
                Constructor<? extends AbstractCommand> constructor = commandClass.getConstructor();
                AbstractCommand command = constructor.newInstance();

                String commandName = command.getCommand();
                String permission = command.getPermission();
                String commandParent = command.getParentCommand();
                Boolean isOnlyPlayer = command.isOnlyPlayer();

                CommandExecutor executor = (sender, cmd, label, args) -> {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;

                        if (!player.hasPermission(permission)) {
                            player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                            return true;
                        }

                        command.execute(player, args);
                    } else {
                        if (!isOnlyPlayer) {
                            command.executeConsole(sender,args);
                        }else{
                            sender.sendMessage(ChatColor.RED + "This command can only be run by a player!");
                        }
                    }

                    return true;
                };

                if (commandParent != null && !commandParent.isEmpty()) {
                    PluginCommand parent = plugin.getCommand(commandParent);
                    if (parent != null) {
                        parent.setExecutor(executor);
                        parent.getTabCompleter().register(command);
                    } else {
                        plugin.getLogger().log(Level.WARNING, "Parent command not found for subcommand: " + commandName);
                    }
                } else {
                    PluginCommand cmd = plugin.getCommand(commandName);
                    if (cmd != null) {
                        cmd.setExecutor(executor);
                        cmd.setTabCompleter(command);
                    } else {
                        plugin.getLogger().log(Level.WARNING, "Command not found: " + commandName);
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Error registering command " + commandClass.getSimpleName(), e);
            }
        }
    }
    public boolean onCommand(){
        // Verificar si el comando es un subcomando
        if (command.isSubCommand()) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Debes especificar un subcomando.");
                return false;
            }
            String subCommandName = args[0];
            ICommand subCommand = getSubCommand(subCommandName);
            if (subCommand == null) {
                sender.sendMessage(ChatColor.RED + "El subcomando especificado no es válido.");
                return false;
            }
            // Redirigir al subcomando
            String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
            subCommand.execute(sender, subCommandArgs);
            return true;
        }

        // Ejecutar el comando principal
        command.execute(sender, args);
        return true;
    }
}
