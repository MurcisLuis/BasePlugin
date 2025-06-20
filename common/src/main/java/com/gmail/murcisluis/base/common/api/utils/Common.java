package com.gmail.murcisluis.base.common.api.utils;

import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.localization.LocalizationManager;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Common utilities for the BasePlugin framework.
 * This class provides shared functionality across all platform implementations.
 * 
 * @deprecated This class will be refactored in future versions to reduce static dependencies.
 * Consider using {@link LocalizationManager} directly for messages.
 */
@UtilityClass
public class Common {

	private static final Pattern SPACING_CHARS_REGEX = Pattern.compile("[_ \\-]+");
	
	/**
	 * Gets the framework prefix from the localization manager.
	 * @return The framework prefix
	 */
	public static String getPrefix() {
		return LocalizationManager.getFrameworkMessage("prefix", "<gray>[<aqua>BasePlugin</aqua>]</gray> ");
	}
	
	/**
	 * Legacy prefix - kept for backward compatibility.
	 * @deprecated Use {@link #getPrefix()} instead
	 */
	@Deprecated
	public static String PREFIX = "&8[&3BasePlugin&8] &7";

	private static final Map<Character, String> legacyColorsToMiniMessage = new HashMap<>();

	static {
		legacyColorsToMiniMessage.put('0', "<black>");
		legacyColorsToMiniMessage.put('1', "<dark_blue>");
		legacyColorsToMiniMessage.put('2', "<dark_green>");
		legacyColorsToMiniMessage.put('3', "<dark_aqua>");
		legacyColorsToMiniMessage.put('4', "<dark_red>");
		legacyColorsToMiniMessage.put('5', "<dark_purple>");
		legacyColorsToMiniMessage.put('6', "<gold>");
		legacyColorsToMiniMessage.put('7', "<gray>");
		legacyColorsToMiniMessage.put('8', "<dark_gray>");
		legacyColorsToMiniMessage.put('9', "<blue>");
		legacyColorsToMiniMessage.put('a', "<green>");
		legacyColorsToMiniMessage.put('b', "<aqua>");
		legacyColorsToMiniMessage.put('c', "<red>");
		legacyColorsToMiniMessage.put('d', "<ligt_purple>");
		legacyColorsToMiniMessage.put('e', "<yellow>");
		legacyColorsToMiniMessage.put('f', "<white>");
	}


	/*
	 * 	Log
	 */

	/**
	 * Log a message into console.
	 *
	 * @param message The message.
	 */
	public static void log(String message) {
		log(Level.INFO, message);
	}

	/**
	 * Log a message into console.
	 * <p>
	 *     This method formats given arguments in the message.
	 * </p>
	 *
	 * @param message The message.
	 * @param args The arguments
	 */
	public static void log(String message, Object... args) {
		log(String.format(message, args));
	}

	/**
	 * Log a message into console.
	 *
	 * @param level Level of this message.
	 * @param message The message.
	 */
	public static void log(Level level, String message) {
		BaseAPIFactory.getPlugin().getLogger().log(level, getPrefix() + message);
	}

	/**
	 * Log a message into console.
	 * <p>
	 *     This method formats given arguments in the message.
	 * </p>
	 *
	 * @param level Level of this message.
	 * @param message The message.
	 * @param args The arguments.
	 */
	public static void log(Level level, String message, Object... args) {
		log(level, String.format(message, args));
	}

	/*
	 * 	Debug
	 */

	/**
	 * Print an object into console.
	 *
	 * @param o Object to print.
	 */
	public static void debug(Object o) {
		System.out.println(o);
	}

	/*
	 * 	Tell
	 */

	/**
	 * Send a message to given CommandSender.
	 * <p>
	 *     This method will colorize the message.
	 * </p>
	 *
	 * @param sender The CommandSender receiving the message.
	 * @param message The message.
	 */
	public static void tell(Object sender, String message) {
		Base base= BaseAPIFactory.getAPI().get();
		Audience audience=base.audienceSender(sender);
		message =base.placeholderAPI(sender,message);
		message = convertLegacyToMiniMessage(message);
		audience.sendMessage(MiniMessage.miniMessage().deserialize(message));


	}

	/**
	 * Send a message to given CommandSender.
	 * <p>
	 *     This method will colorize the message and formats given arguments to the message.
	 * </p>
	 *
	 * @param sender The CommandSender receiving the message.
	 * @param message The message.
	 * @param args The arguments.
	 */
	public static void tell(Object sender, String message, TagResolver... args) {
		Base base=BaseAPIFactory.getAPI().get();
		Audience audience=base.audienceSender(sender);
		message =base.placeholderAPI(sender,message);
		message = convertLegacyToMiniMessage(message);
		audience.sendMessage(MiniMessage.miniMessage().deserialize(message,args));
	}

	/**
	 * Send a message to given CommandSender.
	 * <p>
	 *     This method will colorize the message.
	 * </p>
	 *
	 * @param sender The CommandSender receiving the message.
	 * @param message The message.
	 * @param player The player to extract Placeholder
	 */
	public static void tell(Object sender, String message,Object player) {
		Base base=BaseAPIFactory.getAPI().get();
		Audience audience=base.audienceSender(sender);
		message =base.placeholderAPI(player,message);
		message = convertLegacyToMiniMessage(message);
		audience.sendMessage(MiniMessage.miniMessage().deserialize(message));

	}

	/**
	 * Send a message to given CommandSender.
	 * <p>
	 *     This method will colorize the message and formats given arguments to the message.
	 * </p>
	 *
	 * @param sender The CommandSender receiving the message.
	 * @param message The message.
	 * @param player The player to extract Placeholder
	 * @param args The arguments.
	 */
	public static void tell(Object sender, String message,Object player, TagResolver... args) {
		Base base=BaseAPIFactory.getAPI().get();
		Audience audience=base.audienceSender(sender);
		message =base.placeholderAPI(player,message);
		message = convertLegacyToMiniMessage(message);
		audience.sendMessage(MiniMessage.miniMessage().deserialize(message,args));


	}

	private static String convertLegacyToMiniMessage(String legacyMessage) {
		StringBuilder convertedMessage = new StringBuilder();
		int length = legacyMessage.length();

		for (int i = 0; i < length; i++) {
			char currentChar = legacyMessage.charAt(i);
			if (currentChar == '&' && i + 1 < length) {
				char colorChar = legacyMessage.charAt(i + 1);
				String miniMessageColor = legacyColorsToMiniMessage.get(colorChar);
				if (miniMessageColor != null) {
					convertedMessage.append(miniMessageColor);
				}
				i++; // Skip the next character since we already processed it as a color code
			} else {
				convertedMessage.append(currentChar);
			}
		}

		return convertedMessage.toString();
	}

	public static String removeSpacingChars(String string) {
		return SPACING_CHARS_REGEX.matcher(string).replaceAll("");
	}

}