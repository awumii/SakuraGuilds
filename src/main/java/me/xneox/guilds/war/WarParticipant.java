package me.xneox.guilds.war;

import me.xneox.guilds.element.Guild;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarParticipant {
    private final Guild guild;
    private final List<Player> members;
    private int points;

    public WarParticipant(Guild guild) {
        this.guild = guild;
        this.members = new ArrayList<>(guild.getOnlineMembers());
    }

    public Guild getGuild() {
        return guild;
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
