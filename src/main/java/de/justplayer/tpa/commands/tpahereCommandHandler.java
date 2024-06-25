package de.justplayer.tpa.commands;

import de.justplayer.tpa.Plugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class tpahereCommandHandler implements CommandExecutor {

    private final Plugin plugin;

    public tpahereCommandHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            sender.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.errors.player-required"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.usages.tpahere"));
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.errors.player-not-found"));
            return true;
        }

        if (target == player) {
            player.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.errors.player-self-request"));
            return true;
        }

        if (plugin.cooldownManager.isOnCooldown(player.getUniqueId(), "tpaHere")) {
            player.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.errors.cooldown",
                    Map.of("seconds", Integer.toString(plugin.cooldownManager.getCooldown(player.getUniqueId(), "tpa")))
            ));

            return true;
        }

        if (plugin.teleportRequestManager.getRequestByPlayer(player.getUniqueId()) != null) {
            player.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.errors.request-pending"));
            return true;
        }

        plugin.cooldownManager.addCooldown(player.getUniqueId(), "tpaHere", plugin.config.getInt("tpa.cooldowns.tpaHere"));

        plugin.teleportRequestManager.createRequest(
                player.getUniqueId(),
                target.getUniqueId(),
                true
        );

        player.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.request.sent",
                Map.of(
                        "playername", target.getName(),
                        "seconds", plugin.config.getString("tpa.timeout")
                )
        ));

        target.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.request.received",
                Map.of(
                        "playername", player.getName(),
                        "seconds", plugin.config.getString("tpa.timeout")
                )
        ));

        target.sendMessage(plugin.translate("messages.prefix") + plugin.translate("messages.request.warning-tpa-here", Map.of("playername", player.getName())));
        TextComponent textAccept = new TextComponent(plugin.translate("messages.request.accept"));
        textAccept.setColor(ChatColor.GREEN);
        textAccept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + player.getName()));

        TextComponent textDeny = new TextComponent(plugin.translate("messages.request.deny"));
        textDeny.setColor(ChatColor.RED);
        textDeny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny " + player.getName()));

        BaseComponent[] bs = new ComponentBuilder()
                .append(">> ")
                .append(textAccept)
                .append(" ")
                .append(textDeny)
                .create();

        target.spigot().sendMessage(bs);

        return true;
    }
}
