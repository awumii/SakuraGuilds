package me.xneox.guilds.command.sub;

import java.util.List;
import java.util.concurrent.TimeUnit;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.command.annotations.SubCommand;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.manager.GuildManager;
import me.xneox.guilds.util.text.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InviteCommand implements SubCommand {

  @Override
  public void handle(@NotNull GuildManager manager, @NotNull Player player, String[] args) {
    if (args.length < 2) {
      ChatUtils.sendMessage(player, "&cPodaj nick gracza.");
      return;
    }

    Player target = Bukkit.getPlayer(args[1]);
    if (target == null) {
      ChatUtils.sendMessage(player, "&cTen gracz nie jest online.");
      return;
    }

    if (manager.playerGuild(target) != null) {
      ChatUtils.sendMessage(player, "&cTen gracz już posiada gildię.");
      return;
    }

    Guild guild = manager.playerGuild(player.getName());
    if (guild == null) {
      ChatUtils.sendMessage(player, "&cNie posiadasz gildii.");
      return;
    }

    if (guild.members().size() >= guild.maxSlots()) {
      ChatUtils.sendMessage(player, "&cOsiągnięto limit graczy dodanych do gildii. Zakup ulepszenie gildii!");
      return;
    }

    if (guild.isMember(args[1])) {
      ChatUtils.sendMessage(player, "&cTen gracz jest już dodany do gildii.");
      return;
    }

    if (SakuraGuildsPlugin.get().cooldownManager().hasCooldown(player, "invite-" + target.getName())) {
      ChatUtils.sendMessage(player, "&cMusisz poczekać &6{0} &cprzed wysłaniem zaproszenia.",
          SakuraGuildsPlugin.get().cooldownManager().getRemaining(player, "invite-" + target.getName()));
      return;
    }

    ChatUtils.sendMessage(player, "Zaproszono gracza &6" + args[1] + " &7do twojej gildii.");
    guild.playerInvitations().add(target.getUniqueId());

    ChatUtils.sendNoPrefix(target, "");
    ChatUtils.sendNoPrefix(target, "  &7Otrzymano zaproszenie do gildii &6" + manager.playerGuild(player.getName()).name());
    ChatUtils.sendClickableMessage(target,
        "  &aKliknij, aby zaakceptować.",
        "&aPo kliknięciu dołączysz do gildii!",
        "/g join " + guild.name());
    ChatUtils.sendNoPrefix(target, "");

    SakuraGuildsPlugin.get().cooldownManager().add(player, "invite-" + target.getName(), 10, TimeUnit.MINUTES);
  }

  @Override
  public @Nullable List<String> suggest(String[] args) {
    return Bukkit.getOnlinePlayers().stream()
        .map(Player::getName)
        .toList();
  }
}
