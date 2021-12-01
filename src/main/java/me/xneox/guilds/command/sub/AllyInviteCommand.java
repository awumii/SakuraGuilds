package me.xneox.guilds.command.sub;

import java.util.List;
import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.enums.Permission;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AllyInviteCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    if (args.length < 2) {
      ChatUtils.sendMessage(player, "&cPodaj nazwę gildii.");
      return;
    }

    Guild guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
      return;
    }

    if (!guild.member(player.getName()).hasPermission(Permission.ALLIES)) {
      ChatUtils.sendMessage(player, "&cTwoja ranga w gildii jest zbyt niska!");
      return;
    }

    Guild otherGuild = manager.get(args[1]);
    if (otherGuild == null) {
      ChatUtils.sendMessage(player, "&cPodana gildia nie istnieje.");
      return;
    }

    if (guild.name().equals(otherGuild.name())) {
      ChatUtils.sendMessage(player, "&cNie możesz zawrzeć sojuszu ze sobą XDDXDxdDXdx.");
      return;
    }

    if (guild.allies().contains(otherGuild)) {
      ChatUtils.sendMessage(player, "&cJuż posiadacie sojusz.");
      return;
    }

    if (SakuraGuildsPlugin.get().cooldownManager().hasCooldown(player, "ally-" + otherGuild.name())) {
        ChatUtils.sendMessage(player, "&cMusisz poczekać &6{0} &cprzed wysłaniem zaproszenia.",
            SakuraGuildsPlugin.get().cooldownManager().getRemaining(player, "ally-" + otherGuild.name()));
      return;
    }

    ChatUtils.sendMessage(player, "&7Wysłano zaproszenie sojuszu do &6" + otherGuild.name());

    ChatUtils.guildAlertRaw(otherGuild, " ");
    ChatUtils.guildAlertRaw(otherGuild, "  &7Otrzymano zaproszenie do sojuszu od &6" + guild.name());
    ChatUtils.guildAlertRaw(otherGuild, " ");

    for (Player otherMember : otherGuild.getOnlineMembers()) {
      ChatUtils.sendClickableMessage(otherMember,
          "  &aKliknij, aby zaakceptować.",
          "&aPo kliknięciu zostaniecie sojusznikami!",
          "/g acceptally " + guild.name());

      ChatUtils.sendClickableMessage(otherMember,
          "  &cKliknij, aby odrzucić.",
          "&cOdrzuca zaproszenie.",
          "/g acceptally " + guild.name());
    }

    ChatUtils.guildAlertRaw(otherGuild, " ");
    SakuraGuildsPlugin.get().cooldownManager().add(player, "ally-" + otherGuild.name(), 10, TimeUnit.MINUTES);
  }

  @Override
  public List<String> suggest(String[] args) {
    return SakuraGuildsPlugin.get().guildManager().guildNames();
  }
}
