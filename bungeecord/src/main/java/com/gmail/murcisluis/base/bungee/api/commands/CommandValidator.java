package com.gmail.murcisluis.base.bungee.api.commands;

import com.gmail.murcisluis.base.common.api.Lang;
import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;


@UtilityClass
public class CommandValidator {

    /**
     * Get the Player from CommandSender if it is a Player.
     * <p>
     *     If CommandSender is not a Player, message will be sent to them.
     * </p>
     *
     * @param sender The CommandSender.
     * @return The Player or null if CommandSender is not Player.
     */
    public static ProxiedPlayer getPlayer(CommandSender sender) {
        if (!(sender instanceof ProxiedPlayer)) {
            Lang.ONLY_PLAYER.send(sender);
            return null;
        }
        return (ProxiedPlayer) sender;
    }

    /**
     * Check if String is a valid identifier of Command.
     *
     * @param identifier The String to check.
     * @param commandBase The Command.
     * @return Boolean whether the String is a valid identifier of the CommandBase.
     */
    public static boolean isIdentifier(String identifier, CommandBase commandBase) {
        if (identifier.equalsIgnoreCase(commandBase.getName())) {
            return true;
        }

        for (String alias : commandBase.getAliasesL()) {
            if (identifier.equalsIgnoreCase(alias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if CommandSender is allowed to execute Command.
     * <p>
     *     If the CommandSender isn't allowed to execute the Command, message
     *     will be sent to him.
     * </p>
     *
     * @param sender The CommandSender
     * @param commandBase The Command being executed
     * @return Boolean whether the Command may be executed by the CommandSender
     */
    public static boolean canExecute(CommandSender sender, CommandBase commandBase) {
        if (commandBase.isPlayerOnly() && !(sender instanceof ProxiedPlayer)) {
            Lang.ONLY_PLAYER.send(sender);
            return false;
        }

        String perm = commandBase.getPermission();
        if (perm != null && !perm.trim().isEmpty() && !sender.hasPermission(perm)) {
            Lang.NO_PERM.send(sender);
            return false;
        }
        return true;
    }

    public static int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

}