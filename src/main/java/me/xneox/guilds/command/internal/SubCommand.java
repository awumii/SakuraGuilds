package me.xneox.guilds.command.internal;

import me.xneox.guilds.manager.GuildManager;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

public interface SubCommand {
    void handle(GuildManager manager, Player player, String[] args);

    default @Nullable
    List<String> suggest(String[] args) {
        return null;
    }
}
