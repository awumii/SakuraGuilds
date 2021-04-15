package me.xneox.guilds.command;

import me.xneox.guilds.NeonGuilds;
import me.xneox.guilds.command.admin.ArenaCreateCommand;
import me.xneox.guilds.command.admin.SetFirstSpawnCommand;
import me.xneox.guilds.command.admin.SetSecondSpawnCommand;
import me.xneox.guilds.command.sub.*;
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
        commandMap.put("donate", new DonateCommand());
        commandMap.put("top", new TopCommand());
        commandMap.put("chat", new ChatChannelCommand());
        commandMap.put("war", new WarCommand());
        commandMap.put("public", new PublicCommand());
        commandMap.put("x_acceptally", new AllyAcceptCommand());
        commandMap.put("x_acceptwar", new WarAcceptCommand());

        commandMap.put("x_admin_createarena", new ArenaCreateCommand());
        commandMap.put("x_admin_setfirstspawn", new SetFirstSpawnCommand());
        commandMap.put("x_admin_setsecondspawn", new SetSecondSpawnCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        GuildManager guildManager = this.plugin.getGuildManager();

        if (args.length < 1) {
            if (this.plugin.getGuildManager().getGuild(player.getName()) == null) {
                this.commandMap.get("help").handle(guildManager, player, args);
            } else {
                this.plugin.getInventoryManager().open("manage", player);
            }
            return true;
        }

        SubCommand subCommand = this.commandMap.get(args[0]);
        if (subCommand == null) {
            ChatUtils.sendMessage(player, "&cNie odnaleziono takiej komendy.");
            return true;
        }

        PermissibleCommand permissible = subCommand.getClass().getAnnotation(PermissibleCommand.class);
        if (permissible != null && !player.hasPermission(permissible.value())) {
            ChatUtils.sendMessage(player, "&cNie posiadasz uprawnień (" + permissible.value() + ")");
        }

        subCommand.handle(guildManager, player, args);
        return true;
    }

    public CommandCompleter getCommandCompleter() {
        return commandCompleter;
    }

    public Map<String, SubCommand> getCommandMap() {
        return commandMap;
    }
}
