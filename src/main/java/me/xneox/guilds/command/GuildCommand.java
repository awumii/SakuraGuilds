package me.xneox.guilds.command;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.command.impl.admin.*;
import me.xneox.guilds.command.annotations.AdminOnly;
import me.xneox.guilds.command.impl.hidden.AllyAcceptCommand;
import me.xneox.guilds.command.impl.hidden.WarAcceptCommand;
import me.xneox.guilds.command.impl.sub.*;
import me.xneox.guilds.command.misc.GlobalHelpCommand;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GuildCommand implements CommandExecutor {
    private final NeonGuilds plugin;
    private final Map<String, SubCommand> commandMap = new HashMap<>();
    private final CommandCompleter commandCompleter;

    public GuildCommand(NeonGuilds plugin) {
        this.commandCompleter = new CommandCompleter(this);
        this.plugin = plugin;

        commandMap.put("claim", new ClaimCommand());
        commandMap.put("unclaim", new UnClaimCommand());
        commandMap.put("create", new CreateCommand());
        commandMap.put("leave", new LeaveCommand());
        commandMap.put("disband", new DeleteCommand());
        commandMap.put("home", new HomeCommand());
        commandMap.put("info", new InfoCommand());
        commandMap.put("invite", new InviteCommand());
        commandMap.put("join", new JoinCommand());
        commandMap.put("kick", new KickCommand());
        commandMap.put("sethome", new SetHomeCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("ally", new AllyCommand());
        commandMap.put("top", new TopCommand());
        commandMap.put("chat", new ChatChannelCommand());
        commandMap.put("war", new WarCommand());
        commandMap.put("public", new PublicCommand());
        commandMap.put("browse", new BrowseCommand());
        commandMap.put("donate", new DonateCommand());

        commandMap.put("acceptally", new AllyAcceptCommand());
        commandMap.put("acceptwar", new WarAcceptCommand());

        commandMap.put("createarena", new ArenaCreateCommand());
        commandMap.put("setfirstspawn", new SetFirstSpawnCommand());
        commandMap.put("setsecondspawn", new SetSecondSpawnCommand());
        commandMap.put("teleport", new GuildTeleportCommand());
        commandMap.put("setleaderboard", new SetLeaderboardCommand());
        commandMap.put("settrophies", new SetTrophiesCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        GuildManager guildManager = this.plugin.getGuildManager();

        if (args.length < 1) {
            this.plugin.getInventoryManager().open(this.plugin.getGuildManager().getGuild(player.getName()) == null ? "newbie" : "management", player);
            return true;
        }

        SubCommand subCommand = this.commandMap.get(args[0]);
        if (subCommand == null) {
            ChatUtils.sendMessage(player, "&cNie odnaleziono takiej komendy.");
            return true;
        }

        AdminOnly permissible = subCommand.getClass().getAnnotation(AdminOnly.class);
        if (permissible != null && !player.isOp()) {
            ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień do tej komendy.");
        }

        try {
            subCommand.handle(guildManager, player, args);
        } catch (Exception e) {
            ChatUtils.sendMessage(player, "&cWystąpił błąd podczas wykonywania komendy: &4" + e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public CommandCompleter getCommandCompleter() {
        return commandCompleter;
    }

    public Map<String, SubCommand> getCommandMap() {
        return commandMap;
    }
}
