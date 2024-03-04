package com.gmail.murcisluis.base.spigot.plugin.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandBase;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandBaseSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandHandlerSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.TabCompleteHandlerSpigot;
import com.google.common.collect.Lists;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.List;

@CommandInfo(
        permission = "lujoemotes.use",
        usage = "/le help",
        description = "The help command."
)//%%__USER__%%
public class HelpSubCommand extends AbstractCommandSpigot implements CommandBaseSpigot {

    public HelpSubCommand() {
        super("help");
    }
    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            sender.sendMessage("");
            Common.tell(sender, " <aqua>LUJO EMOTES HELP");
            Common.tell(sender, " All general commands.");
            CommandBaseSpigot command= PLUGIN.getCommandManager().getMainCommand();
            List<CommandBase> subCommands = Lists.newArrayList(command.getSubCommands());
            for (CommandBase subCommand : subCommands) {
                Common.tell(sender, " <dark_gray>? <aqua><click:SUGGEST_COMMAND:<usage>><usage></click> <dark_gray>- &7<description>", Placeholder.parsed("usage", subCommand.getUsage()), Placeholder.parsed("description", subCommand.getDescription()));
            }
            sender.sendMessage("");
            Common.tell(sender, " <gray>Aliases: <aqua><name><aliases>",
                    Placeholder.parsed("name",command.getName()) ,
                    Placeholder.parsed("aliases",command.getAliasesL().size() > 1
                            ? ", " + String.join(", ", command.getAliasesL())
                            : "")
            );
            return true;
        };
    }

    @Override
    public TabCompleteHandlerSpigot getTabCompleteHandler() {
        return null;
    }
}
