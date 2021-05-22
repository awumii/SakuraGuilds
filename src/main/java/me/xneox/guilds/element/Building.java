package me.xneox.guilds.element;

import me.xneox.guilds.type.BuildingType;
import me.xneox.guilds.util.ChatUtils;
import me.xneox.guilds.util.HookUtils;
import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.VisualUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;

public class Building {
    private final Location location;
    private final BuildingType type;

    private State state;
    private int level;
    private long completeTime;

    public Building(Location location, BuildingType type, State state, int level) {
        this.location = location;
        this.type = type;
        this.state = state;
        this.level = level;
    }

    public void process(Guild guild) {
        if (this.state == State.INBUILT && System.currentTimeMillis() > this.completeTime) {
            this.level++;
            this.state = State.BUILT;

            if (this.type == BuildingType.STORAGE) {
                guild.updateStorage();
            }

            HookUtils.pasteSchematic(this.location, WordUtils.capitalizeFully(this.type.name()) + "_L_" + this.level);
            ChatUtils.guildAlert(guild, "&7Budynek &6" + this.type.getName() + " &7został wybudowany na poziom &e" + this.level);
        }
        VisualUtils.createBuildingInfo(guild, this);
    }

    public static Building parse(String string) {
        String[] split = string.split(";");
        return new Building(LocationUtils.fromString(split[0]), BuildingType.valueOf(split[1]), State.valueOf(split[2]), Integer.parseInt(split[3]));
    }

    public Location getLocation() {
        return location;
    }

    public BuildingType getType() {
        return type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getLevel() {
        return level;
    }

    public long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(long completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public String toString() {
        return LocationUtils.toString(this.location) + ";" + this.type.name() + ";" + this.state.name() + ";" + this.level;
    }

    public enum State {
        INBUILT, BUILT
    }
}
