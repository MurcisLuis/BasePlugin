package com.gmail.murcisluis.base.spigot.api;

import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import com.gmail.murcisluis.base.spigot.api.commands.CommandManagerSpigot;
import com.gmail.murcisluis.base.spigot.api.utils.PAPI;
import com.gmail.murcisluis.base.spigot.api.utils.config.FileConfigSpigot;
import com.gmail.murcisluis.base.spigot.plugin.BaseSpigotPlugin;
import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.localization.LocalizationManager;
import com.gmail.murcisluis.base.common.api.localization.InternalMessages;
import com.gmail.murcisluis.base.common.api.Settings;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.utils.DExecutor;
import com.gmail.murcisluis.base.common.api.utils.reflect.ReflectionUtil;
import com.gmail.murcisluis.base.common.api.utils.reflect.Version;


import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.logging.Level;

@Getter
public abstract class BaseSpigot extends Base {

    private CommandManagerSpigot commandManager;
    protected BukkitAudiences audience;

    protected BaseSpigot(BasePlugin plugin) {
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
        LocalizationManager.getInstance().reloadAllLanguages();
        DExecutor.init(3);

        this.audience=BukkitAudiences.create(getPlugin());


        this.commandManager = new CommandManagerSpigot();

    }
    public void disable() {
        this.commandManager = null;

    }

    public void reload() {
        Settings.reload();
        LocalizationManager.getInstance().reloadAllLanguages();
    }

    @Override
    public Audience audienceSender(Object sender) {
        if (sender instanceof CommandSender) {
            return audience.sender((CommandSender) sender);
        }
        throw new IllegalArgumentException(InternalMessages.getErrorMessage(InternalMessages.ErrorMessage.NOT_SPIGOT_SENDER));    }

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

        if (path.isEmpty()) {
            return new FileConfigSpigot(getPlugin(),null,new YamlConfiguration());
        }else{
            return new FileConfigSpigot(getPlugin(),path);

        }

    }

    @Override
    public BaseSpigotPlugin getPlugin() {
        return (BaseSpigotPlugin) super.getPlugin();
    }
}
