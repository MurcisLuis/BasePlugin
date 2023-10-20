package com.gmail.murcisluis.base.common.api.commands;

import com.gmail.murcisluis.base.common.api.exception.AbstractCommandException;

@FunctionalInterface
public interface CommandHandler<S> {
    boolean handle(S sender, String[] args) throws AbstractCommandException;
}