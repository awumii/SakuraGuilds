package me.xneox.guilds.manager;

import co.aikar.idb.DB;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.element.Guild;
import me.xneox.guilds.util.ChatUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class GuildManager {
    private final Map<String, Guild> guildMap = new HashMap<>();

    public void load(SakuraGuildsPlugin plugin) {
        try {
            long ms = System.currentTimeMillis();
            plugin.sqlManager().loadGuilds();

            ChatUtils.broadcast("&7Wczytano &6" + this.guildMap.size() + " &7gildii w ciągu &e" + (System.currentTimeMillis() - ms) + "ms.");
        } catch (Exception e) {
            ChatUtils.broadcast("&cWystąpił krytyczny błąd przy wczytywaniu bazy danych: &4" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(Guild guild) {
        for (Guild value : this.guildMap.values()) {
            value.allies().remove(guild.name());
        }

        guild.nexusLocation().getBlock().setType(Material.AIR);
        guild.nexusLocation().createExplosion(5, true, true);

        DB.executeUpdateAsync("DELETE FROM guilds WHERE name=?", guild.name());
        this.guildMap.remove(guild.name());
    }

    public Guild findAt(Location location) {
        for (Guild guild : this.guildMap.values()) {
            if (guild.inside(location)) {
                return guild;
            }
        }
        return null;
    }

    public Guild playerGuild(Player player) {
        return this.playerGuild(player.getName());
    }

    public Guild playerGuild(String player) {
        return this.guildMap.values().stream()
                .filter(guild -> guild.isMember(player))
                .findFirst()
                .orElse(null);
    }

    public Guild get(String name) {
        return this.guildMap.get(name);
    }

    public List<Guild> leaderboard() {
        List<Guild> copy = new ArrayList<>(this.guildMap.values());
        Collections.sort(copy);
        return copy;
    }

    public Map<String, Guild> guildMap() {
        return this.guildMap;
    }
}
