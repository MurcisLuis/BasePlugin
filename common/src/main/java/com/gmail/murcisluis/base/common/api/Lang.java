package com.gmail.murcisluis.base.common.api;

import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.utils.Common;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

/**
 * @deprecated Use {@link LocalizationManager} instead.
 * This class is kept for backward compatibility and will be removed in a future version.
 * 
 * Migration guide:
 * - Replace Lang.PREFIX with LocalizationManager.FrameworkMessages.getPrefix()
 * - Replace Lang.NO_PERM with LocalizationManager.FrameworkMessages.getNoPermission()
 * - Replace Lang.reload() with LocalizationManager.getInstance().reloadAllLanguages()
 * - Use LocalizationManager for custom messages and multi-language support
 */
@Deprecated
@UtilityClass
public class Lang {

	private static final LocalizationManager localizationManager = LocalizationManager.getInstance();

	// Initialize default language if not loaded
	static {
		if (!localizationManager.isLanguageLoaded("en")) {
			localizationManager.loadLanguage("en", "lang.yml");
		}
	}

	/**
	 * @deprecated Use {@link LocalizationManager.FrameworkMessages#getPrefix()} instead.
	 */
	@Deprecated
	public static String PREFIX = Common.PREFIX;

	/**
	 * @deprecated Use {@link LocalizationManager.FrameworkMessages#getNoPermission()} instead.
	 */
	@Deprecated
	public static Component NO_PERM = LocalizationManager.FrameworkMessages.getNoPermission();

	/**
	 * @deprecated Use {@link LocalizationManager.FrameworkMessages#getPlayerOnly()} instead.
	 */
	@Deprecated
	public static Component ONLY_PLAYER = LocalizationManager.FrameworkMessages.getPlayerOnly();

	/**
	 * @deprecated Use {@link LocalizationManager.FrameworkMessages#getReloadSuccess()} instead.
	 */
	@Deprecated
	public static Component RELOADED = LocalizationManager.FrameworkMessages.getReloadSuccess();

	/**
	 * @deprecated Use {@link LocalizationManager.FrameworkMessages#getUseHelp(String)} instead.
	 */
	@Deprecated
	public static Component USE_HELP = LocalizationManager.FrameworkMessages.getUseHelp("help");

	/**
	 * @deprecated Use {@link LocalizationManager.FrameworkMessages#getCommandUsage(String)} instead.
	 */
	@Deprecated
	public static Component COMMAND_USAGE = LocalizationManager.FrameworkMessages.getCommandUsage("%1$s");

	/**
	 * @deprecated Use {@link LocalizationManager.FrameworkMessages#getUnknownSubCommand()} instead.
	 */
	@Deprecated
	public static Component UNKNOWN_SUB_COMMAND = LocalizationManager.FrameworkMessages.getUnknownSubCommand();

	/**
	 * @deprecated Use {@link LocalizationManager#reloadAllLanguages()} instead.
	 */
	@Deprecated
	public static void reload() {
		localizationManager.reloadAllLanguages();
		Common.PREFIX = LocalizationManager.FrameworkMessages.getPrefix();
	}

	/**
	 * Generic version message sender that works for any plugin.
	 * @deprecated Create your own version message using LocalizationManager with your plugin name.
	 * 
	 * @param pluginName the name of your plugin
	 * @param version the version of your plugin
	 * @param author the author of your plugin
	 * @param link the link to your plugin
	 */
	@Deprecated
	public static void sendVersionMessage(@NonNull String pluginName, @NonNull String version, @NonNull String author, @NonNull String link) {
		Common.log("&f");
		Common.log(
				localizationManager.getMessage("framework.version_info", pluginName, version, author, link)
		);
		Common.log("&f");
	}

	/**
	 * Generic update message sender that works for any plugin.
	 * @deprecated Create your own update message using LocalizationManager with your plugin name.
	 * 
	 * @param pluginName the name of your plugin
	 * @param version the new version available
	 * @param link the download link
	 */
	@Deprecated
	public static void sendUpdateMessage(@NonNull String pluginName, @NonNull String version, @NonNull String link) {
		Common.log("&f");
		Common.log(
				localizationManager.getMessage("framework.update_available", pluginName, version, link)
		);
		Common.log("&f");
	}

	/**
	 * Legacy method for backward compatibility.
	 * @deprecated Use sendVersionMessage(String, String, String, String) instead.
	 */
	@Deprecated
	public static void sendVersionMessage(@NonNull String version, @NonNull String link) {
		sendVersionMessage("BasePlugin", version, "Framework", link);
	}

	/**
	 * Legacy method for backward compatibility.
	 * @deprecated Use sendUpdateMessage(String, String, String) instead.
	 */
	@Deprecated
	public static void sendUpdateMessage(@NonNull String version, @NonNull String link) {
		sendUpdateMessage("BasePlugin", version, link);
	}


}