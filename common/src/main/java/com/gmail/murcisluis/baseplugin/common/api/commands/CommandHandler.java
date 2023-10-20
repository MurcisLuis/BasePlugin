package com.gmail.murcisluis.baseplugin.common.api.commands;

import com.gmail.murcisluis.baseplugin.common.api.exception.AbstractCommandException;

@FunctionalInterface
public interface CommandHandler<S> {
    boolean handle(S sender, String[] args) throws AbstractCommandException;
}