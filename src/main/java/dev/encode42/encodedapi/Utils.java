package dev.encode42.encodedapi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.logging.Logger;

public class Utils {
    private static Logger log;
    public Utils(Logger log) {Utils.log = log;}

    // ## Translate color codes to chat-compatible colors
    public static String toChat(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    // ## Permission checking
    public static boolean checkPerms(CommandSender sender, Command command, String args) {
        // Console running command
        if (!(sender instanceof Player)) return true;

        // Player running command
        if (args != null) return sender.hasPermission(command.getName() + "." + args);
        else return sender.hasPermission(command.getName()) || sender.hasPermission(command.getName() + ".*");
    }

    // Overloading
    public static boolean checkPerms(CommandSender sender, Command command) {
        return checkPerms(sender, command, null);
    }

    // ## Send message to sender
    public static void sendMessage(CommandSender sender, String message, HashMap options) {
        // Defaults (null checking)
        options.putIfAbsent("message", "Message not defined.");
        options.putIfAbsent("severity", "info");
        options.putIfAbsent("placeholders", false);
        options.putIfAbsent("toChat", true);

        // Colors, placeholders, prefix
        if (options.get("placeholders").equals(true))                  message = replacePlaceholders(message);
        if (options.get("toChat").equals(true))                        message = toChat(message);
        if (options.get("prefix") != null && sender instanceof Player) message = options.get("prefix") + message;

        // Send the player/log the message
        if (sender instanceof Player) sender.sendMessage(message);
        else {
            switch (options.get("severity").toString()) {
                case "severe": log.severe(message);
                case "warn":   log.warning(message);
                case "info":   log.info(message);
            }
        }
    }

    // ## Placeholders (This is W.I.P.)
    public static String replacePlaceholders(String message) {
        return message;
    }
}