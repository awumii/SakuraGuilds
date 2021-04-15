package me.xneox.guilds.manager;

import me.xneox.guilds.element.User;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final Map<String, User> userMap = new HashMap<>();

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public User getUser(Player player) {
        return this.getUser(player.getName());
    }

    public User getUser(String name) {
        return userMap.computeIfAbsent(name, User::new);
    }

    public void removeUser(String name) {
        this.getUser(name).save();
        userMap.remove(name);
    }

    public void saveAll() {
        this.userMap.values().forEach(User::save);
    }
}
