package me.xneox.guilds.command;

import java.util.HashMap;
import java.util.Map;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.sub.AllyAcceptCommand;
import me.xneox.guilds.command.sub.AllyCommand;
import me.xneox.guilds.command.sub.ChatChannelCommand;
import me.xneox.guilds.command.sub.ClaimCommand;
import me.xneox.guilds.command.sub.CreateCommand;
import me.xneox.guilds.command.sub.DeleteCommand;
import me.xneox.guilds.command.sub.DonateCommand;
import me.xneox.guilds.command.sub.GuildTeleportCommand;
import me.xneox.guilds.command.sub.HelpCommand;
import me.xneox.guilds.command.sub.HomeCommand;
import me.xneox.guilds.command.sub.InfoCommand;
import me.xneox.guilds.command.sub.InviteCommand;
import me.xneox.guilds.command.sub.JoinCommand;
import me.xneox.guilds.command.sub.KickCommand;
import me.xneox.guilds.command.sub.LeaveCommand;
import me.xneox.guilds.command.sub.MenuCommand;
import me.xneox.guilds.command.sub.SetHomeCommand;
import me.xneox.guilds.command.sub.SetTrophiesCommand;
import me.xneox.guilds.command.sub.TopCommand;
import me.xneox.guilds.command.sub.UnClaimCommand;
import me.xneox.guilds.command.sub.UpgradeCommand;
import me.xneox.guilds.command.sub.ViewDatabaseCommand;
import me.xneox.guilds.command.annotations.SubCommand;
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
    commandMap.put("menu", new MenuCommand());
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

  @NotNull
  public Map<String, SubCommand> commandMap() {
    return this.commandMap;
  }

  @NotNull
  public GuildCommandExecutor executor() {
    return this.executor;
  }

  @NotNull
  public GuildCommandCompleter completer() {
    return this.completer;
  }

  @NotNull
  public SakuraGuildsPlugin plugin() {
    return this.plugin;
  }
}
