package com.gmail.murcisluis.base.spigot.api.commands;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.commands.AbstractCommand;
import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.spigot.api.BaseSpigot;
import com.gmail.murcisluis.base.common.api.exception.AbstractCommandException;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class AbstractCommandSpigot extends Command implements CommandBaseSpigot {

    protected static final BaseSpigot PLUGIN = (BaseSpigot) BaseAPIFactory.get();
    protected final Map<String, CommandBase> subCommands = new LinkedHashMap<>();
    protected final CommandInfo info;

    public AbstractCommandSpigot(String name) {
        super(name);
        this.info = getClass().getAnnotation(CommandInfo.class);
        if (info == null) {
            throw new RuntimeException(String.format("Command %s is not annotated with @CommandInfo.", name));
        }

        this.setAliases(Arrays.asList(info.aliases()));

    }

    @Override
    public Set<String> getSubCommandNames() {
        return subCommands.keySet();
    }

    @Override
    public Collection<CommandBase> getSubCommands() {
        return subCommands.values();
    }
    @Override
    public CommandBase getSubCommand(@NotNull String name) {
        return subCommands.get(name);
    }

    @Override
    public CommandBase addSubCommand(CommandBase commandBase) {
        subCommands.put(commandBase.getName(), commandBase);
        return this;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        try {
            return this.handle(sender, args);
        } catch (AbstractCommandException e) {
            Common.tell(sender, e.getMessage());
            return true;
        }
    }


    /**
     * Handle Tab Complete of the Command.
     *
     * @param sender The sender.
     * @param args The arguments.
     * @return List of tab completed Strings.
     */
    protected final List<String> handeTabComplete(CommandSender sender, String[] args) {
        return handleTabComplete(sender, args);
    }


    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return handeTabComplete(sender, args);
    }

    @Override
    public String getPermission() {
        return info.permission();
    }

    @Override
    public boolean isPlayerOnly() {
        return info.playerOnly();
    }

    @Override
    public int getMinArgs() {
        return info.minArgs();
    }

    @Override
    public String getUsage() {
        return info.usage();
    }

    @Override
    public String getDescription() {
        return info.description();
    }

    /**
     * Handle the Command.
     *
     * @param sender The sender.
     * @param args The arguments.
     * @return Boolean whether the execution was successful.
     */
    protected final boolean handle(CommandSender sender, String[] args) throws AbstractCommandException {
        if (!CommandValidator.canExecute(sender, this)) {
            return true;
        }

        if (args.length != 0) {
            for (CommandBase subCommand : getSubCommands()) {
                if (CommandValidator.isIdentifier(args[0], subCommand)) {
                    final String[] subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
                    if (subCommandArgs.length < subCommand.getMinArgs()) {
                        String message = LocalizationManager.getFrameworkMessage("invalid-args", "<red>Invalid arguments. Usage: {usage}</red>");
                        Common.tell(sender, message, Placeholder.parsed("usage", subCommand.getUsage()), Placeholder.parsed("description", subCommand.getDescription()));
                        return true;
                    }
                    return ((AbstractCommandSpigot) subCommand).handle(sender, subCommandArgs);
                }
            }
        } else if (getMinArgs() > 0) {
            CommandBase command= PLUGIN.getCommandManager().getMainCommand();

            String message = LocalizationManager.getFrameworkMessage("invalid-args", "<red>Invalid arguments. Usage: {usage}</red>");
            Common.tell(sender, message, Placeholder.parsed("name", command.getName()),
                    Placeholder.parsed("aliases", command.getAliasesL().size() > 1
                            ? ", " + String.join(", ", command.getAliasesL())
                            : ""));
            return false;
        }

        return this.getCommandHandler().handle(sender, args);
    }

    /**
     * Handle Tab Complete of the Command.
     *
     * @param sender The sender.
     * @param args The arguments.
     * @return List of tab completed Strings.
     */
    protected final List<String> handleTabComplete(CommandSender sender, String[] args) {
        if (getPermission() != null && !sender.hasPermission(getPermission())) {
            return ImmutableList.of();
        }

        if (args.length == 1) {
            List<String> subs = new ArrayList<>();
            getSubCommands().forEach(cmd -> {
                subs.add(cmd.getName());
                subs.addAll(Lists.newArrayList(cmd.getAliasesL()));
            });

            List<String> matches = TabCompleteHandlerSpigot.getPartialMatches(args[0], subs);

            if (!matches.isEmpty()) {
                Collections.sort(matches);
                return matches;
            }
        } else if (args.length > 1) {
            for (CommandBase subCommand : getSubCommands()) {
                if (CommandValidator.isIdentifier(args[0], subCommand)) {
                    return ((AbstractCommandSpigot) subCommand).handleTabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }

        if (this.getTabCompleteHandler() == null) {
            return ImmutableList.of();
        }

        return this.getTabCompleteHandler().handleTabComplete(sender, args);
    }


    @Override
    public List<String> getAliasesL() {
        return getAliases();
    }
}