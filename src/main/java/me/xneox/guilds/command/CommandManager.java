package me.xneox.guilds.command;

import java.util.HashMap;
import java.util.Map;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.impl.AllyAcceptCommand;
import me.xneox.guilds.command.impl.AllyCommand;
import me.xneox.guilds.command.impl.ChatChannelCommand;
import me.xneox.guilds.command.impl.ClaimCommand;
import me.xneox.guilds.command.impl.CreateCommand;
import me.xneox.guilds.command.impl.DeleteCommand;
import me.xneox.guilds.command.impl.DonateCommand;
import me.xneox.guilds.command.impl.GuildTeleportCommand;
import me.xneox.guilds.command.impl.HelpCommand;
import me.xneox.guilds.command.impl.HomeCommand;
import me.xneox.guilds.command.impl.InfoCommand;
import me.xneox.guilds.command.impl.InviteCommand;
import me.xneox.guilds.command.impl.JoinCommand;
import me.xneox.guilds.command.impl.KickCommand;
import me.xneox.guilds.command.impl.LeaveCommand;
import me.xneox.guilds.command.impl.RaceCommand;
import me.xneox.guilds.command.impl.SetHomeCommand;
import me.xneox.guilds.command.impl.SetTrophiesCommand;
import me.xneox.guilds.command.impl.TopCommand;
import me.xneox.guilds.command.impl.UnClaimCommand;
import me.xneox.guilds.command.impl.UpgradeCommand;
import me.xneox.guilds.command.impl.ViewDatabaseCommand;
import me.xneox.guilds.command.internal.SubCommand;
import org.jetbrains.annotations.NotNull;

public class CommandManager {
  private final Map<String, SubCommand> commandMap = new HashMap<>();

  private final GuildCommandExecutor executor;
  private final GuildCommandCompleter completer;

  private final SakuraGuildsPlugin plugin;

  public CommandManager(@NotNull SakuraGuildsPlugin plugin) {
    this.plugin = plugin;

    this.executor = new GuildCommandExecutor(this);
    this.completer = new GuildCommandCompleter(this);

    this.registerCommands();
  }

  private void registerCommands() {
    commandMap.put("race", new RaceCommand());
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
    commandMap.put("upgrade", new UpgradeCommand());
    commandMap.put("donate", new DonateCommand());
    commandMap.put("database", new ViewDatabaseCommand());

    // admin commands
    commandMap.put("acceptally", new AllyAcceptCommand());
    commandMap.put("teleport", new GuildTeleportCommand());
    commandMap.put("settrophies", new SetTrophiesCommand());
  }

  public Map<String, SubCommand> commandMap() {
    return this.commandMap;
  }

  public GuildCommandExecutor executor() {
    return this.executor;
  }

  public GuildCommandCompleter completer() {
    return this.completer;
  }

  public SakuraGuildsPlugin plugin() {
    return this.plugin;
  }
}
