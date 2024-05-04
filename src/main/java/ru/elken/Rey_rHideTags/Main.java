package ru.elken.Rey_rHideTags;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.elken.Rey_rHideTags.listeners.Interact;
import ru.elken.Rey_rHideTags.listeners.Join;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main extends JavaPlugin implements TabCompleter {
    public static Scoreboard board;
    private static Team team;
    public static String actionbar_message;
    public static String chat_message;
    public static Boolean chat;
    public static Boolean actionbar;
    public static Main main;

    @Override
    public void onEnable() {
        main = this;

        // Конфигурация
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        actionbar = getConfig().getBoolean("actionbar");
        actionbar_message = getConfig().getString("actionbar_message");

        chat = getConfig().getBoolean("chat");
        chat_message = getConfig().getString("chat_message");

        this.boardSettings();

        // Ивенты
        this.getServer().getPluginManager().registerEvents(new Interact(), this);
        this.getServer().getPluginManager().registerEvents(new Join(), this);

        // Команды
        for (String s : Arrays.asList("rht", "rhidetags")) {
            this.getCommand(s).setExecutor(new Command());
            this.getCommand(s).setTabCompleter(new Command());
        }

        if (!this.getServer().getOnlinePlayers().isEmpty()) this.getServer().getOnlinePlayers().forEach(Main::hideName);

        // Сообщение о включении
        this.getLogger().info("");
        this.getLogger().info("\033[38;5;220mrHideTags \033[38;5;231m| \033[38;5;231mrHideTags 1.2.0 successfully enabled\033[0m");
        this.getLogger().info("\033[38;5;220mrHideTags \033[38;5;231m| \033[38;5;118mCreated by: \033[38;5;231mRey (vk.com/omashune)\033[0m");
        this.getLogger().info("\033[38;5;220mrHideTags \033[38;5;231m| \033[38;5;45mUpdated by: \033[38;5;231mzoga-com & mrElken\033[0m");
        this.getLogger().info("");
    }

    public static void hideName(Player p) {
        team.addEntry(p.getName());
        p.setScoreboard(board);
    }

    private void boardSettings() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        board.registerNewTeam("hidenametags");

        team = board.getTeam("hidenametags");
        if(team == null) return;
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        team.setCanSeeFriendlyInvisibles(false);
    }

    public static void reload_cfg() {
        Main.main.reloadConfig();

        actionbar = Main.main.getConfig().getBoolean("actionbar");
        actionbar_message = Main.main.getConfig().getString("actionbar_message");

        chat = Main.main.getConfig().getBoolean("chat");
        chat_message = Main.main.getConfig().getString("chat_message");
    }

    // Поддержка Legacy кодов и HEX (Спасибо Overwrite)
    public static String colors(String message) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        int subVersion = Integer.parseInt(
                version.replace("1_", "").replaceAll("_R\\d", "").replace("v", ""));

        if(subVersion >= 16) {
            Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start() + 1, matcher.end());
                message = message.replace("&" + color, ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
