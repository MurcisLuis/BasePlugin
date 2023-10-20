package com.gmail.murcisluis.base.spigot.api;

import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import com.gmail.murcisluis.base.spigot.api.commands.CommandManagerSpigot;
import com.gmail.murcisluis.base.spigot.api.utils.PAPI;
import com.gmail.murcisluis.base.spigot.api.utils.config.FileConfigSpigot;
import com.gmail.murcisluis.base.spigot.plugin.BaseSpigotPlugin;
import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Lang;
import com.gmail.murcisluis.base.common.api.Settings;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.utils.DExecutor;
import com.gmail.murcisluis.base.common.api.utils.reflect.ReflectionUtil;
import com.gmail.murcisluis.base.common.api.utils.reflect.Version;


import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Getter
public class BaseSpigot extends Base {

    private CommandManagerSpigot commandManager;
    protected BukkitAudiences audience;


    BaseSpigot(BasePlugin plugin) {
        super(plugin);
    }

    public void load() {
        if (Version.CURRENT == null) {
            Common.log(Level.SEVERE, "Unsupported server version: " + ReflectionUtil.getVersion());
            Common.log(Level.SEVERE, "Plugin will be disabled.");
            Bukkit.getPluginManager().disablePlugin((JavaPlugin)plugin);
        }
    }
    public void enable() {
        Settings.reload();
        Lang.reload();
        DExecutor.init(3);

        this.audience=BukkitAudiences.create(getPlugin());


        this.commandManager = new CommandManagerSpigot();

    }
    public void disable() {
        this.commandManager = null;

    }

    public void reload() {
        Settings.reload();
        Lang.reload();
    }

    @Override
    public Audience audienceSender(Object sender) {
        if (sender instanceof CommandSender) {
            return audience.sender((CommandSender) sender);
        }
        throw new IllegalArgumentException("sender is not a Spigot CommandSender");    }

    @Override
    public String placeholderAPI(Object player, String message) {
        if (player instanceof Player) {
            Player p= (Player) player;
            return PAPI.setPlaceholders(p,message);
        }

        return message;
    }

    @Override
    public FileConfig<?> getFileConfig(String path) {
        return new FileConfigSpigot(getPlugin(),path);
    }

    @Override
    public BaseSpigotPlugin getPlugin() {
        return (BaseSpigotPlugin) super.getPlugin();
    }
}
