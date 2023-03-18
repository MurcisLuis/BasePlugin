package com.gmail.murcisluis.baseplugin.api.commands;

import com.gmail.murcisluis.baseplugin.api.exception.AbstractCommandException;
import org.bukkit.command.CommandSender;

@FunctionalInterface
public interface CommandHandler {
    boolean handle(CommandSender sender, String[] args) throws AbstractCommandException;
}
