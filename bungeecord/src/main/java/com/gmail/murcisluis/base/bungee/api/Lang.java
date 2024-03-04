package com.gmail.murcisluis.base.bungee.api;

import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.utils.config.ConfigValue;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import com.gmail.murcisluis.base.common.api.utils.config.Phrase;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class Lang {

	private static final Base BASE = BaseAPIFactory.getAPI().get();
	private static final FileConfig CONFIG = BASE.getFileConfig("lang.yml");

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

	public static <T> void sendVersionMessage(@NonNull T sender) {
		Common.tell(sender,
				"\n&fThis server is running &3LujoEmotes v<v>&f by <aqua>Murcis_Luis&f : &7<link>",
				Placeholder.parsed("v", BaseAPIFactory.getPlugin().getPluginDescription().getVersion()) ,
				Placeholder.parsed("link","https://cambiarEnlaze/")
		);
	}

	public static <T> void sendUpdateMessage(@NonNull T sender) {
		Common.tell(sender,
				"\n&fA newer version of &3Plugin &fis available. Download it from: &7<link>",
				Placeholder.parsed("link","https://cambiarEnlaze/")
		);
	}


}