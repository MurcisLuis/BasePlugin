package com.gmail.murcisluis.base.spigot.api;

import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;
import com.gmail.murcisluis.base.spigot.api.commands.CommandManagerSpigot;
import com.gmail.murcisluis.base.spigot.api.emotes.Data;
import com.gmail.murcisluis.base.spigot.api.emotes.EmotesPlaceholder;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemConfig;
import com.gmail.murcisluis.base.spigot.api.emotes.data.ItemEmote;
import com.gmail.murcisluis.base.spigot.api.emotes.data.PlayerData;
import com.gmail.murcisluis.base.spigot.api.emotes.manager.ListenerManager;
import com.gmail.murcisluis.base.spigot.api.emotes.manager.MenuManager;
import com.gmail.murcisluis.base.spigot.api.utils.PAPI;
import com.gmail.murcisluis.base.spigot.api.utils.config.FileConfigSpigot;
import com.gmail.murcisluis.base.spigot.api.utils.config.Metrics;
import com.gmail.murcisluis.base.spigot.plugin.BaseSpigotPlugin;
import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.utils.Common;
import com.gmail.murcisluis.base.common.api.utils.DExecutor;
import com.gmail.murcisluis.base.common.api.utils.reflect.ReflectionUtil;
import com.gmail.murcisluis.base.common.api.utils.reflect.Version;


import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Getter
public class BaseSpigot extends Base {

    private CommandManagerSpigot commandManager;
    protected BukkitAudiences audience;
    private ListenerManager listenerManager;


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
        ConfigurationSerialization.registerClass(ItemConfig.class, "Item");
        ConfigurationSerialization.registerClass(ItemEmote.class, "ItemEmote");
        ConfigurationSerialization.registerClass(PlayerData.class, "PlayerData");
        Settings.reload();
        Lang.reload();
        Data.reload();
        DExecutor.init(3);
        new Metrics(getPlugin(),21144);
        MenuManager.onEnable();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new EmotesPlaceholder().register();
        }

        this.audience=BukkitAudiences.create(getPlugin());
        this.listenerManager=new ListenerManager();
        this.commandManager = new CommandManagerSpigot();
        Common.tell(Bukkit.getConsoleSender(),"<gradient:#8A2387:#E94057:#F27121>Thanks for purchasing LujoEmotes, <bold>%%__USERNAME__%%</bold>!</gradient>");

    }
    public void disable() {
        this.commandManager = null;
        MenuManager.onDisable();

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
