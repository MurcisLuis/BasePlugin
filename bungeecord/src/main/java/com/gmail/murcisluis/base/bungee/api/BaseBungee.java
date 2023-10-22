package com.gmail.murcisluis.base.bungee.api;

import com.gmail.murcisluis.base.bungee.api.commands.CommandManagerBungee;
import com.gmail.murcisluis.base.bungee.api.utils.config.FileConfigBungee;
import com.gmail.murcisluis.base.bungee.plugin.BaseBungeePlugin;
import com.gmail.murcisluis.base.common.api.Base;
import com.gmail.murcisluis.base.common.api.BasePlugin;
import com.gmail.murcisluis.base.common.api.Lang;
import com.gmail.murcisluis.base.common.api.Settings;
import com.gmail.murcisluis.base.common.api.utils.DExecutor;
import com.gmail.murcisluis.base.common.api.utils.config.FileConfig;

import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

@Getter
public class BaseBungee extends Base {
    private CommandManagerBungee commandManager;
    protected BungeeAudiences audience;
    public BaseBungee(BasePlugin plugin) {
        super(plugin);
    }

    public void load() {
//        if (Version.CURRENT == null) {
//            Common.log(Level.SEVERE, "Unsupported server version: " + ReflectionUtil.getVersion());
//            Common.log(Level.SEVERE, "Plugin will be disabled.");
//            ProxyServer proxyServer = getPlugin().getProxy();
//
//            proxyServer.getPluginManager().unregisterListeners(getPlugin());
//            proxyServer.getPluginManager().unregisterCommands(getPlugin());
//        }
    }
    public void enable() {
        Settings.reload();
        Lang.reload();
        DExecutor.init(3);
        this.audience= BungeeAudiences.create((Plugin) plugin);
        this.commandManager = new CommandManagerBungee();

    }
    public void disable() {
        this.commandManager = null;
        if(this.audience != null) {
            this.audience.close();
            this.audience = null;
        }

    }

    public void reload() {
        Settings.reload();
        Lang.reload();
    }
    public Audience audienciePlayer(Object player){
        if (player instanceof ProxiedPlayer) {
            return audience.player((ProxiedPlayer)player);
        }
        throw new IllegalArgumentException("player is not a BungeeCord ProxiedPlayer");

    }

    @Override
    public Audience audienceSender(Object sender) {
        if (sender instanceof CommandSender) {
            return audience.sender((CommandSender) sender);
        }
        throw new IllegalArgumentException("sender is not a BungeeCord CommandSender");
    }

    @Override
    public String placeholderAPI(Object player, String message) {
        return message;
    }

    public FileConfig<?> getFileConfig(String path) {

        if (path.isEmpty()) {
            return new FileConfigBungee(path,new Configuration());
        }else{
            return new FileConfigBungee(getPlugin(),path);

        }

    }

    @Override
    public BaseBungeePlugin getPlugin() {
        return (BaseBungeePlugin) super.getPlugin();
    }
}
