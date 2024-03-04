package com.gmail.murcisluis.base.common.api.commands;

import java.util.List;

@FunctionalInterface
public interface TabCompleteHandler<S> {

    /**
     * Handle Tab Complete.
     *
     * @param sender The sender.
     * @param args The arguments.
     * @return List of Tab Completed strings.
     */
    List<String> handleTabComplete(S sender, String[] args);
}