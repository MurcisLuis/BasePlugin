package com.gmail.murcisluis.bungee.api.commands;

import com.gmail.murcisluis.baseplugin.common.api.Base;
import com.gmail.murcisluis.baseplugin.common.api.BaseAPIFactory;
import com.gmail.murcisluis.baseplugin.common.api.commands.TabCompleteHandler;
import net.md_5.bungee.api.CommandSender;

import java.util.*;

public interface TabCompleteHandlerBungee extends TabCompleteHandler<CommandSender> {
    Base PLUGIN = BaseAPIFactory.getAPI().get();

    static List<String> getPartialMatches(String token, String... originals) {
        return getPartialMatches(token, Arrays.asList(originals));
    }

    static List<String> getPartialMatches(String token, Collection<String> originals) {
        if (originals == null) {
            return Collections.emptyList();
        }

        if (token == null || token.isEmpty()) {
            return new ArrayList<>(originals);
        }

        List<String> matches = new ArrayList<>();
        for (String str : originals) {
            if (str.length() >= token.length() && str.regionMatches(true, 0, token, 0, token.length())) {
                matches.add(str);
            }
        }

        return matches;
    }

    /**
     * Handle Tab Complete.
     *
     * @param sender The sender.
     * @param args The arguments.
     * @return List of Tab Completed strings.
     */
    List<String> handleTabComplete(CommandSender sender, String[] args);
}
