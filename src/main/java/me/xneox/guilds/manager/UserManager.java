package me.xneox.guilds.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.xneox.guilds.element.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserManager {
    private final Cache<String, User> userCache = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    @NotNull
    public User getUser(Player player) {
        return this.getUser(player.getName());
    }

    @NotNull
    public User getUser(String name) {
        return this.userCache.asMap().computeIfAbsent(name, User::new);
    }

    public void save() {
        this.userCache.asMap().values().forEach(User::save);
    }
}
