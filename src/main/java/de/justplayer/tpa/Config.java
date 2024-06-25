package de.justplayer.tpa;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class Config {
    private final Plugin plugin;

    // Make sure the configuration is initialized
    public Config(Plugin plugin) {
        this.plugin = plugin;
        FileConfiguration config = this.plugin.getConfig();

        config.addDefault("tpa.timeout", 60);
        config.setComments("tpa.timeout", List.of("Time in seconds you have to accept a teleport request"));

        config.addDefault("tpa.wait", 5);
        config.setComments("tpa.wait", List.of(
                "Time in seconds you have to wait before you teleport (after teleport request was accepted)",
                "Any movement will cancel the teleport, 0 to disable",
                "Note: this feature is not implemented yet, teleports are currently instant."
        ));

        config.addDefault("tpa.cooldowns.tpa", 60);
        config.setComments("tpa.cooldowns.tpa", List.of(
                "Time in seconds you have to wait before you can send another teleport request"
        ));

        config.addDefault("tpa.cooldowns.tpaHere", 60);
        config.setComments("tpa.cooldowns.tpaHere", List.of(
                "Time in seconds you have to wait before you can send another tpa here request"
        ));

        config.addDefault("bStats.enabled", true);
        config.setComments("bStats.enabled", List.of(
                "Enable bStats for this plugin"
        ));

        // We use modrinth to check for updates
        config.addDefault("check-for-updates", false);
        config.setComments("check-for-updates", List.of(
                "Check for updates on startup, this uses the modrinth api to get the newest version for your server."
        ));

        config.addDefault("messages.prefix", "§8[§6传送§8] §7");
        config.addDefault("messages.reloaded", "配置已重新加载。");
        config.addDefault("messages.usages.tpa", "用法: /tpa <玩家>");
        config.addDefault("messages.usages.tpahere", "用法: /tpahere <玩家>");
        config.addDefault("messages.usages.tpaccept", "用法: /tpaccept 接受最近的传送请求，或 /tpaccept <玩家> 接受特定传送请求");

        config.addDefault("messages.errors.player-required", "您必须是玩家才能使用此命令。");
        config.addDefault("messages.errors.player-not-found", "玩家未找到。");
        config.addDefault("messages.errors.player-self-request", "您无法向自己发送传送请求。");
        config.addDefault("messages.errors.request-pending", "您已经有一个待处理的请求。");
        config.addDefault("messages.errors.request-not-found", "您没有待处理的请求。");
        config.addDefault("messages.errors.request-not-found-by", "您没有来自 %playername% 的待处理请求。");
        config.addDefault("messages.errors.cooldown", "您需要等待 %seconds% 秒才能发送另一个传送请求。");

        config.addDefault("messages.request.sent", "传送请求已发送至 %playername%，%seconds% 秒后过期。");
        config.addDefault("messages.request.received", "您收到来自 %playername% 的传送请求，将在 %seconds% 秒后过期。");
        config.addDefault("messages.request.accept", "[接受请求]");
        config.addDefault("messages.request.deny", "[拒绝请求]");
        config.addDefault("messages.request.denied-by", "%playername% 拒绝了传送。");
        config.addDefault("messages.request.denied", "您拒绝了来自 %playername% 的传送请求。");
        config.addDefault("messages.request.warning-tpa-here", "警告: 如果您接受此请求，您将被传送到 %playername%。");
        config.addDefault("messages.request.canceled", "传送已取消。");
        config.addDefault("messages.request.canceled-by", "%playername% 取消了传送。");
        config.addDefault("messages.request.accepted", "您接受了来自 %playername% 的传送请求。");
        config.addDefault("messages.request.accepted-by", "您的传送请求已被 %playername% 接受。");
        config.addDefault("messages.request.timeout-to", "您发送给 %playername% 的传送请求已超时。");
        config.addDefault("messages.request.timeout-from", "来自 %playername% 的传送请求已超时。");
        config.addDefault("messages.request.teleported-to", "你已经被传送到 %playername%。");
        config.addDefault("messages.request.teleported-from", "%playername% 已经传送到你的位置。");

        config.options().copyDefaults(true);
        this.plugin.saveConfig();
        this.plugin.reloadConfig();
    }

    public String getString(String key) {
        return this.plugin.getConfig().getString(key);
    }
    public String getString(String key, String defaultValue) {
        return this.plugin.getConfig().getString(key, defaultValue);
    }

    public int getInt(String key) {
        return this.plugin.getConfig().getInt(key);
    }
    public int getInt(String key, int defaultValue) {
        return this.plugin.getConfig().getInt(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return this.plugin.getConfig().getBoolean(key);
    }
    public boolean getBoolean(String key, boolean defaultValue) {
        return this.plugin.getConfig().getBoolean(key, defaultValue);
    }
}
