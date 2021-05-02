package me.xneox.guilds.element;

import me.xneox.guilds.util.LocationUtils;
import me.xneox.guilds.util.SchematicUtils;
import me.xneox.guilds.util.VisualUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;

public class Building {
    private final Location location;
    private final Type type;

    private State state;
    private int level;
    private long completeTime;

    public Building(Location location, Type type, State state, int level) {
        this.location = location;
        this.type = type;
        this.state = state;
        this.level = level;
    }

    public Location getLocation() {
        return location;
    }

    public Type getType() {
        return type;
    }

    public State getState() {
        return state;
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

    public void setState(State state) {
        this.state = state;
    }

    public void refresh(Guild guild) {
        if (this.state == State.INBUILT && System.currentTimeMillis() > this.completeTime) {
            this.level++;
            this.state = State.BUILT;

            SchematicUtils.pasteSchematic(this.location, WordUtils.capitalizeFully(this.type.name()) + "_L_" + this.level);
        }
        VisualUtils.createBuildingInfo(guild, this);
    }

    public static Building parse(String string) {
        String[] split = string.split(";");
        return new Building(LocationUtils.fromString(split[0]), Type.valueOf(split[1]), State.valueOf(split[2]), Integer.parseInt(split[3]));
    }

    @Override
    public String toString() {
        return LocationUtils.toString(this.location) + ";" + this.type.name() + ";" + this.state.name() + ";" + this.level;
    }

    public enum State {
        INBUILT, BUILT
    }

    public enum Type {
        TOWNHALL("Ratusz"),
        BARRACK("Koszary"),
        ALCHEMY("Labolatorium"),
        STORAGE("Magazyn");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
