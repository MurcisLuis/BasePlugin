package com.gmail.murcisluis.baseplugin.api.commands;

import org.bukkit.command.CommandSender;

@FunctionalInterface
public class CommandHandler {
    boolean handle(CommandSender sender, String[] args) throws ;
}
