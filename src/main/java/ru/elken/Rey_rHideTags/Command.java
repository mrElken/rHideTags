package ru.elken.Rey_rHideTags;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static ru.elken.Rey_rHideTags.Main.reload_cfg;

public class Command implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.@NotNull Command cmd, String label, String[] args) {
        if (args.length == 0) {

            sender.sendMessage("");
            sender.sendMessage("§erHideTags §f| §fCommands:§r");
            sender.sendMessage("§erHideTags §f| §f/" + label + " reload - Reload config§r");
            sender.sendMessage("");

            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            reload_cfg();

            sender.sendMessage("");
            sender.sendMessage("§erHideTags §f| §fConfig reloaded!§r");
            sender.sendMessage("");

            if (sender instanceof Player) {
                Main.main.getLogger().info("");
                Main.main.getLogger().info("\033[38;5;220mrHideTags \033[38;5;231m| \033[38;5;231mConfig reloaded by " + sender.getName() + "!\033[0m");
                Main.main.getLogger().info("");
            }

            return true;
        }

        sender.sendMessage("");
        sender.sendMessage("§erHideTags §f| §fUnknown command!§r");
        sender.sendMessage("");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Lists.newArrayList("reload");
        }
        return new ArrayList<>();
    }
}
