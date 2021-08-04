package me.xneox.guilds.manager;

import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.User;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class UserManager {
    private final Map<String, User> userMap = new HashMap<>();

    public void load(SakuraGuildsPlugin plugin) {
        try {
            long ms = System.currentTimeMillis();
            plugin.sqlManager().loadUsers();

            ChatUtils.broadcast("&7Wczytano &6" + this.userMap.size() + " &7danych graczy w ciągu &e" + (System.currentTimeMillis() - ms) + "ms.");
        } catch (Exception e) {
            ChatUtils.broadcast("&cWystąpił krytyczny błąd przy wczytywaniu bazy danych: &4" + e.getMessage());
            e.printStackTrace();
        }
    }

    @NotNull
    public User getUser(Player player) {
        return this.getUser(player.getName());
    }

    @NotNull
    public User getUser(String name) {
        return this.userMap.computeIfAbsent(name, s -> new User(500, 0, 0, new Date().getTime()));
    }

    public List<User> leaderboard() {
        List<User> copy = new ArrayList<>(this.userMap.values());
        Collections.sort(copy);
        return copy;
    }

    public Map<String, User> userMap() {
        return this.userMap;
    }
}
