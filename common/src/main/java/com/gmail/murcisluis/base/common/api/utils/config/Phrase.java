package com.gmail.murcisluis.base.common.api.utils.config;

import com.gmail.murcisluis.base.common.api.utils.Common;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public class Phrase extends ConfigValue<String> {

	public Phrase(FileConfig config, String path, String defaultValue) {
		super(config, path, defaultValue);
	}

	public void send(Object sender) {
		Common.tell(sender, getValue().replace("{prefix}", Common.PREFIX));
	}

	public void send(Object sender, TagResolver... args) {
		Common.tell(sender, getValue().replace("{prefix}", Common.PREFIX), args);
	}

	@Override
	public String getValue() {
		return super.getValue().replace("{prefix}", Common.PREFIX);
	}

	public String getValue(TagResolver...tagResolvers) {
		Component component = MiniMessage.miniMessage().deserialize(getValue(),tagResolvers);

		// Convert Component to legacy section code format
		return ChatColor.translateAlternateColorCodes('&',LegacyComponentSerializer.legacySection().serialize(component));
	}

	public String getColorized(){
		Component component = MiniMessage.miniMessage().deserialize(getValue());
		return ChatColor.translateAlternateColorCodes('&',LegacyComponentSerializer.legacySection().serialize(component));

	}
}