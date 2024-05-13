package ctf2024;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.awt.*;

/**
 * This class holds the special Actor for the game's Flag
 */
public class Flag extends Actor {

    private final Team team;
    private Player carrier;

    /**
     * Constructs a Flag for a particular Team and initializes it to the Team's color
     *
     * @param team the Team owning this flag
     */
    public Flag(Team team) {
        this.team = team;
        setColor(Color.YELLOW);
    }

    /**
     * Overridden act method to do nothing
     */
    public final void act() {
    } // I want only players to get called to act

    /**
     * Allows a Player to pick up the Flag - NOT CALLABLE BY STUDENTS
     *
     * @param player The Player picking up the Flag
     */
    protected final void pickUp(Player player) {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.endsWith(".Player")) {
            super.removeSelfFromGrid();
            this.carrier = player;
        } else {
            System.err.println("Someone has cheated and tried to remove a Flag from the grid");
            CtfWorld.extra += " Cheat";
        }
    }

    /**
     * Flag is removed from the Grid - NOT CALLABLE BY STUDENTS
     */
    public final void removeSelfFromGrid() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.endsWith("CtfWorld"))
            super.removeSelfFromGrid();
        else {
            System.err.println("Someone has cheated and tried to remove a player from the grid");
            CtfWorld.extra += " Cheat";
        }
    }

    /**
     * Getter to get this Flag's Team
     * @return the Team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Getter to get this Flag's Location
     * @return the Location
     */
    public Location getLocation() {
        if (getGrid() == null && carrier != null)
            return carrier.getLocation();
        return new Location(super.getLocation().getRow(), super.getLocation().getCol());
    }

    /**
     * Getter to see if Flag is being carried or not
     * @return Whether this Flag is being carried
     */
    public boolean beingCarried() {
        return getGrid() == null && carrier != null;
    }
}
