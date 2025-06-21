package com.gmail.murcisluis.base.examples.spigot.commands;

import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.spigot.api.commands.TabCompleteHandlerSpigot;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.plugin.commands.MyCommand;
import com.gmail.murcisluis.base.spigot.api.commands.AbstractCommandSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandBaseSpigot;
import com.gmail.murcisluis.base.spigot.api.commands.CommandHandlerSpigot;
import org.bukkit.command.CommandSender;

/**
 * EJEMPLO de comando personalizado para Spigot usando BasePlugin Framework.
 * 
 * IMPORTANTE: Esta es una clase de EJEMPLO. No la uses directamente en producción.
 * Copia y modifica según tus necesidades específicas.
 * 
 * Este ejemplo muestra:
 * - Configuración de comando con @CommandInfo
 * - Implementación de CommandHandler
 * - Uso opcional de TabComplete
 */
@CommandInfo(
        permission = "example.use",
        usage = "/example <args>",
        description = "Comando de ejemplo del framework BasePlugin.",
        aliases = {"ex", "ejemplo"}
)
public class ExampleCommandSpigot extends AbstractCommandSpigot implements CommandBaseSpigot, MyCommand<CommandSender> {

    public ExampleCommandSpigot() {
        super("example");
    }

    @Override
    public CommandHandlerSpigot getCommandHandler() {
        return (sender, args) -> {
            // Usar el handler por defecto del framework
            CommandHandler<CommandSender> handler = MyCommand.super.getCommandHandler();
            return handler.handle(sender, args);
        };
    }

    @Override
    public TabCompleteHandlerSpigot getTabCompleteHandler() {
        // Ejemplo de tab completion personalizado (opcional)
        return (sender, args) -> {
            if (args.length == 1) {
                return java.util.Arrays.asList("help", "info", "version", "reload");
            }
            return java.util.Collections.emptyList();
        };
    }
}