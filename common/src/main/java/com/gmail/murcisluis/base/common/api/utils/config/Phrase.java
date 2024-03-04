package com.gmail.murcisluis.base.common.api.utils.config;

import com.gmail.murcisluis.base.common.api.utils.Common;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

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
}