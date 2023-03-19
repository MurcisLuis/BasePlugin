package com.gmail.murcisluis.baseplugin.api;

import com.gmail.murcisluis.baseplugin.api.utils.Common;
import com.gmail.murcisluis.baseplugin.api.utils.config.ConfigValue;
import com.gmail.murcisluis.baseplugin.api.utils.config.Configuration;
import com.gmail.murcisluis.baseplugin.api.utils.config.FileConfig;
import com.gmail.murcisluis.baseplugin.api.utils.config.Phrase;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class Lang {

	private static final Base BASE = BaseAPI.get();
	private static final FileConfig CONFIG = new FileConfig(BASE.getPlugin(), "lang.yml");

	// General
	public static final Phrase PREFIX = new Phrase(CONFIG, "prefix", Common.PREFIX);
	public static final Phrase NO_PERM = new Phrase(CONFIG, "no_perm", "{prefix}&cYou are not allowed to use this.");
	public static final Phrase ONLY_PLAYER = new Phrase(CONFIG, "only_player", "{prefix}&cThis action can only be executed by player.");
	public static final Phrase RELOADED = new Phrase(CONFIG, "reloaded", "{prefix}Successfully reloaded!");

	// Commands
	public static final Phrase USE_HELP = new Phrase(CONFIG, "command.use_help", "{prefix}Use &b/{command.prefix} help&7 to view possible commands.");
	public static final Phrase COMMAND_USAGE = new Phrase(CONFIG, "command.usage", "{prefix}Usage: &b%1$s");
	public static final Phrase UNKNOWN_SUB_COMMAND = new Phrase(CONFIG, "command.unknown_sub_command", "{prefix}Unknown sub command.");

	/*
	 *	General Methods
	 */

	private static final Map<String, ConfigValue<?>> VALUES = Maps.newHashMap();

	static {
		try {
			Field[] fields = Lang.class.getFields();
			for (Field field : fields) {
				if (field.getType().isAssignableFrom(Phrase.class)) {
					VALUES.put(field.getName(), (Phrase) field.get(null));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		Lang.reload();
	}

	public static void reload() {
		CONFIG.reload();
		VALUES.values().forEach(ConfigValue::updateValue);
		Common.PREFIX = PREFIX.getValue();
	}

	public static void sendVersionMessage(@NonNull CommandSender sender) {
		Common.tell(sender,
				"\n&fThis server is running &3BasePlugin v%s&f by &bd0by&f : &7%s",
				BaseAPI.get().getPlugin().getDescription().getVersion(),
				"https://www.spigotmc.org/resources/cambiarEnlaze/"
		);
	}

	public static void sendUpdateMessage(@NonNull CommandSender sender) {
		Common.tell(sender,
				"\n&fA newer version of &3DecentHolograms &fis available. Download it from: &7%s",
				"https://www.spigotmc.org/resources/cambiarEnlaze/"
		);
	}


}