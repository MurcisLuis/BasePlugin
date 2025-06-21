package com.gmail.murcisluis.base.examples.bungeecord.commands;

import com.gmail.murcisluis.base.bungee.api.commands.AbstractCommandBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandBaseBungee;
import com.gmail.murcisluis.base.bungee.api.commands.CommandHandlerBungee;
import com.gmail.murcisluis.base.bungee.api.commands.TabCompleteHandlerBungee;
import com.gmail.murcisluis.base.common.api.commands.CommandHandler;
import com.gmail.murcisluis.base.common.api.commands.CommandInfo;
import com.gmail.murcisluis.base.common.plugin.commands.MyCommand;
import net.md_5.bungee.api.CommandSender;

/**
 * EJEMPLO de comando personalizado para BungeeCord usando BasePlugin Framework.
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
public class ExampleCommandBungee extends AbstractCommandBungee implements CommandBaseBungee, MyCommand<CommandSender> {

    public ExampleCommandBungee() {
        super("example");
    }

    @Override
    public CommandHandlerBungee getCommandHandler() {
        return (sender, args) -> {
            // Usar el handler por defecto del framework
            CommandHandler<CommandSender> handler = MyCommand.super.getCommandHandler();
            return handler.handle(sender, args);
        };
    }

    @Override
    public TabCompleteHandlerBungee getTabCompleteHandler() {
        // Ejemplo de tab completion personalizado (opcional)
        return (sender, args) -> {
            if (args.length == 1) {
                return java.util.Arrays.asList("help", "info", "version", "reload");
            }
            return java.util.Collections.emptyList();
        };
    }
}