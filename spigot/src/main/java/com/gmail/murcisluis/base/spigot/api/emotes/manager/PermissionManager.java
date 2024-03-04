package com.gmail.murcisluis.base.spigot.api.emotes.manager;

import com.gmail.murcisluis.base.common.api.BaseAPI;
import com.gmail.murcisluis.base.common.api.BaseAPIFactory;
import com.gmail.murcisluis.base.spigot.api.BaseSpigotAPI;
import com.gmail.murcisluis.base.spigot.plugin.BaseSpigotPlugin;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@UtilityClass
public class PermissionManager {

    public static boolean hasEmotePermission(Player player, String mobName) {
        return player.hasPermission("lujoemotes." + mobName);
    }

}
