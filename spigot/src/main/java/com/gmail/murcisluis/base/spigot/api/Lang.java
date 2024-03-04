package com.gmail.murcisluis.base.spigot.api;

import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.utils.config.ConfigValue;
import com.gmail.murcisluis.base.common.api.utils.config.Phrase;
import com.gmail.murcisluis.base.spigot.api.utils.config.FileConfigSpigot;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.Map;

@UtilityClass
public class Lang {


    private static final Base BASE = BaseAPIFactory.getAPI().get();
	private static final FileConfigSpigot CONFIG = (FileConfigSpigot) BASE.getFileConfig("lang.yml");

	// General
	public static final Phrase PREFIX = new Phrase(CONFIG, "prefix", Common.PREFIX);
	public static final Phrase NO_PERM = new Phrase(CONFIG, "no_perm", "{prefix}&cYou are not allowed to use this.");
	public static final Phrase ONLY_PLAYER = new Phrase(CONFIG, "only_player", "{prefix}&cThis action can only be executed by player.");
	public static final Phrase RELOADED = new Phrase(CONFIG, "reloaded", "{prefix}Successfully reloaded!");

	// Commands
	public static final Phrase USE_HELP = new Phrase(CONFIG, "command.use_help", "{prefix}Use &b/le help&7 to view possible commands.");
	public static final Phrase COMMAND_USAGE = new Phrase(CONFIG, "command.usage", "{prefix}Usage: &b%1$s");
	public static final Phrase UNKNOWN_SUB_COMMAND = new Phrase(CONFIG, "command.unknown_sub_command", "{prefix}Unknown sub command.");

	public static final Phrase MENU_PLAYER_TITLE = new Phrase(CONFIG, "menu.player.title", "Emotes menu");
	public static final Phrase MENU_PLAYER_LORE_RIGHT_CLICK= new Phrase(CONFIG , "menu.player.click.right","Right click to select emote to kill other player");
	public static final Phrase MENU_PLAYER_LORE_LEFT_CLICK= new Phrase(CONFIG , "menu.player.click.left","Left click to select emote to death");

	public static final Phrase MENU_ADMIN_TITLE= new Phrase(CONFIG ,"menu.admin.title","Select emotes to players");
	public static final Phrase MENU_ADMIN_LORE_RIGHT_CLICK= new Phrase(CONFIG , "menu.admin.click.right","Right click to change material");
	public static final Phrase MENU_ADMIN_LORE_LEFT_CLICK= new Phrase(CONFIG , "menu.admin.click.left","Left click to add/remove to player menu.");
	public static final Phrase MENU_ADMIN_LORE_FACTION= new Phrase(CONFIG , "menu.admin.faction","Faction: ");


	public static final Phrase MENU_CONFIG_TITLE = new Phrase(CONFIG ,"menu.config.title","Edit item to preview");

	public static final Phrase MENU_CONFIG_NAME_TITLE = new Phrase(CONFIG,"menu.config_name.title","Editing name");

	public static final Phrase MENU_CONFIG_LORE_TITLE = new Phrase(CONFIG,"menu.config_lore.title","Editing lore");

	public static final Phrase MENU_CONFIG_MATERIAL_TITLE = new Phrase(CONFIG,"menu.config_material.title","Editing material");
	public static final Phrase MENU_LORE_LEFT_CLICK =  new Phrase(CONFIG,"menu.config_lore.left","Use left click to edit ");

	public static final Phrase MENU_LORE_RIGHT_CLICK =  new Phrase(CONFIG,"menu.config_lore.right","Use right click to remove");

	public static final Phrase SELECT_EMOTE_MESSAGE=new Phrase(CONFIG,"message.select.emote","You have selected <Emote>.");

	public static final Phrase SELECT_EMOTE_LORE=new Phrase(CONFIG,"menu.player.select.lore","Click to remove");




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
				"\n&fThis server is running &3LujoEmotes v<v>&f by <aqua>Murcis_Luis&f : &7<link>",
				Placeholder.parsed("v", BaseAPIFactory.getPlugin().getPluginDescription().getVersion()) ,
				"%%__BUILTBYBIT__%%".equals(true)?Placeholder.parsed("link","https://builtbybit.com/resources/lujoemotes.40218") :Placeholder.parsed("link","https://polymart.org/resource/lujoemotes.5551")
		);
	}

	public static void sendUpdateMessage(@NonNull CommandSender sender) {
		Common.tell(sender,
				"\n&fA newer version of &3LujoEmotes &fis available. Download it from: &7<link>",
				Placeholder.parsed("link","https://cambiarEnlaze/")
		);
	}


}