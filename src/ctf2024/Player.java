package ctf2024;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * This class models a single Player.  Project Players must extend this class and not override any method other than
 * getNextMove
 */
public abstract class Player extends Actor {

    // point values for different actions
    private static final int MOVE = 1;
    private static final int MOVE_ON_OPPONENT_SIDE = 2;
    private static final int CAPTURE = 50;
    private static final int TAG = 20;
    private static final int CARRY = 5;

    // The time the whole team has, in milliseconds. Each player is individually capped on time, not the team
    private static final int TURNTIME = 500;

    private Team team;
    private boolean hasFlag;
    private Location startLocation;
    private int tagCoolDown;

    /**
     * Constructs a Player at a given Location
     *
     * @param startLocation The initial Location
     */
    public Player(Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * Basic act method for a Player - can only be called from CtfWorld
     * The basic sequence of actions is:
     * 1. Ensure that this is only being called by CtfWorld
     * 2. Determine if a Player has won the game by crossing the center line with the Flag
     * 3. Process all neighbors (might tag out other Player, get tagged out, pick up Flag, etc.)
     * 3. Wait (no move allowed) if "cooling down" after being tagged out
     * 4. Determine the next Location to move to by calling the extending class's getMoveLocation method
     *    (must return a Location within the fixed allocated time, or Player does not move)
     * 5. Attempt to move to the specified Location (must be valid, unoccupied, and not near own Flag, etc.)
     */
    public final void act() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (!callingClass.endsWith("CtfWorld")) {
            System.out.println("Someone has cheated and attempted to call act directly");
            return;
        }
        try {
            if (team.hasWon() || team.getOpposingTeam().hasWon()) {
                if (team.hasWon()) {
                    if (hasFlag)
                        setColor(Color.MAGENTA);
                    else
                        setColor(Color.YELLOW);
                }
                return;
            }

            if (tagCoolDown > 0) {
                setColor(Color.BLACK);
                tagCoolDown--;
                if (tagCoolDown == 0) {
                    setColor(team.getColor());
                }
            } else {
                processNeighbors();
//                LocationHolder loc = new LocationHolder();
//                Thread getMoveLocationThread = new Thread() {
//                    @Override
//                    public void run() {
//                        Location l = getMoveLocation();
//                        loc.location = l;
//                    }
//                };
//                getMoveLocationThread.start();
//                long timeLimit = TURNTIME / team.getPlayers().size();
//                long startTime = System.currentTimeMillis();
//                while (!this.getGrid().isValid(loc.location) && System.currentTimeMillis() - startTime < timeLimit) {
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//
//                    }
//                }
//                if (!getMoveLocationThread.isInterrupted()) {
//                    getMoveLocationThread.interrupt();
//                    CtfWorld.extra += " Timeout.";
//                }

//                Location loc = getMoveLocation();

                Location loc = new Location(-1, -1);
                Thread getMoveLocationThread = new Thread() {
                    @Override
                    public void run() {
                        Location l = getMoveLocation();
                        loc.setCol(l.getCol());
                        loc.setRow(l.getRow());
                    }
                };
                getMoveLocationThread.start();
                long timeLimit = TURNTIME / team.getPlayers().size();
                long startTime = System.currentTimeMillis();
                while (!this.getGrid().isValid(loc) && System.currentTimeMillis() - startTime < timeLimit) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                }
                if (getMoveLocationThread.isAlive()) {
                    getMoveLocationThread.stop();
                    System.out.println("Player ran out of time: " + this);
                    CtfWorld.extra += " Time";
                }

                makeMove(!this.getGrid().isValid(loc) ? null : loc); // null = don't move
            }
        } catch (Exception e) {
            CtfWorld.extra += " Err";
            System.err.println("Player " + this + " has generated a runtime exception");
            e.printStackTrace();
        }
    }

    /**
     * Process this Player relative to its neighbors (tagging out, picking up Flag, etc.)
     */
    private final void processNeighbors() {
        List<Location> neighborLocations = getGrid().getOccupiedAdjacentLocations(getLocation());
        for (int i = neighborLocations.size() - 1; i >= 0; i--) {
            Actor neighbor = getGrid().get(neighborLocations.get(i));
            if (!(neighbor instanceof Player) || ((Player) neighbor).team.equals(team)) {
                neighborLocations.remove(i);
                if (neighbor instanceof Flag && !((Flag) neighbor).getTeam().equals(team)) {
                    hasFlag = true;
                    setColor(Color.YELLOW);
                    team.getOpposingTeam().getFlag().pickUp(this);
                    team.addScore(CAPTURE);
                    team.addPickUp();
                }
            }
        }
        if (team.onSide(getLocation())) {
            Collections.shuffle(neighborLocations);
            for (Location neighborLocation : neighborLocations) {
                if (team.onSide(neighborLocation)) {
                    Actor neighbor = getGrid().get(neighborLocation);
                    if (((Player) neighbor).hasFlag() || Math.random() < (1. / neighborLocations.size())) {
                        ((Player) neighbor).tag();
                        team.addScore(TAG);
                        team.addTag();
                    }
                }
            }
        }
    }

    /**
     * Move to specified Location (if allowed)
     * @param loc
     */
    private final void makeMove(Location loc) {
        // if null, treat as if you are staying in same location
        if (loc == null) {
            loc = getLocation();
        }

        // limit to one step towards desired location
        if (!loc.equals(getLocation()))
            loc = getLocation().getAdjacentLocation(getLocation().getDirectionToward(loc));

        // Player is too close to own flag and not moving away from it, it must bounce
        if (team.onSide(getLocation()) && getGrid().get(team.getFlag().getLocation()) instanceof Flag && team.nearFlag(getLocation()) && team.nearFlag(loc)) {
            loc = bounce();
            CtfWorld.extra += " Bounce";
        }
        // if Player is on own side and flag isn't being carried, it can't move too close to own flag
        if (team.onSide(getLocation()) && getGrid().get(team.getFlag().getLocation()) instanceof Flag && team.nearFlag(loc)) {
            CtfWorld.extra += " Close to flag";
            return;
        }

        // move to loc and score appropriate points
        if (!loc.equals(getLocation()) && getGrid().isValid(loc) && getGrid().get(loc) == null) {
            this.setDirection(getLocation().getDirectionToward(loc));
            moveTo(loc);
            if (team.onSide(getLocation()))
                team.addScore(MOVE);
            else
                team.addScore(MOVE_ON_OPPONENT_SIDE);
            team.addOffensiveMove();
            if (this.hasFlag) {
                team.addScore(CARRY);
            }
        }

    }

    // get bounce-to location to move a player away from own flag
    private final Location bounce() {
        // preferred option - move directly away from flag until no longer too close
        int inc = Math.random() < .5 ? 10 : -10;

        for (int i = 0; i < 360; i += inc) {
            int dir = team.getFlag().getLocation().getDirectionToward(getLocation()) + i;
            Location loc = getLocation();
            while (team.nearFlag(loc)) loc = loc.getAdjacentLocation(dir);
            if (getGrid().isValid(loc) && getGrid().get(loc) == null && team.onSide(loc)) return loc;
        }
        return getLocation();   // failed to find any valid location - rare!
    }

    /**
     * MUST be overridden by extending class
     * @return The desired Location to move to
     */
    public abstract Location getMoveLocation();

    /**
     * Tag out another Player
     */
    private final void tag() {
        Location oldLoc = getLocation();
        Location nextLoc;
        do {
            nextLoc = team.adjustForSide(new Location((int) (Math.random() * getGrid().getNumRows()), 0), getGrid());
        }
        while (getGrid().get(nextLoc) != null);
        moveTo(nextLoc);
        tagCoolDown = 10;
        if (hasFlag) {
            team.getOpposingTeam().getFlag().putSelfInGrid(getGrid(), oldLoc);
            hasFlag = false;
        }
        setColor(Color.BLACK);
    }

    /**
     * Puts self in Grid (can't be overridden or extended)
     * @param grid the Grid to put into
     * @param loc the Location to be placed in
     */
    protected final void putSelfInGridProtected(Grid<Actor> grid, Location loc) {
        if (getGrid() != null)
            super.removeSelfFromGrid();
        hasFlag = false;
        tagCoolDown = 0;
        setColor(team.getColor());
        super.putSelfInGrid(grid, loc);
    }

    /**
     * Remove self from Grid - should never happen!
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
     * sets a Players Team - NOT CALLABLE BY STUDENTS
     * @param team
     */
    protected final void setTeam(Team team) {
        this.team = team;
        setColor(team.getColor());
    }

    /**
     * Sets a Player's starting Location - NOT CALLABLE BY STUDENTS
     * @param startLocation
     */
    protected final void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    /**
     * Getter to determine if a Player is carrying the Flag
     * @return
     */
    public final boolean hasFlag() {
        return hasFlag;
    }

    /**
     * Getter for a Player's starting Location
     * @return
     */
    protected final Location getStartLocation() {
        return startLocation;
    }

    /**
     * Getter for a Player's Team - duplicate of getMyTeam
     * @return the Players Team
     */
    public final Team getTeam() {
        return team;
    }

    /**
     * Getter for a Player's Team - duplicate of getTeam
     * @return the Players Team
     */
    public final Team getMyTeam() {
        return team;
    }

    /**
     * Getter to get a Player's opponent's Team
     * @return The opponent's Team
     */
    public final Team getOtherTeam() {
        return team.getOpposingTeam();
    }

    /**
     * Getter for a Player's Location (gets a cloned copy of the Location needed for Threaded timing)
     * @return
     */
    public final Location getLocation() {
        return new Location(super.getLocation().getRow(), super.getLocation().getCol());
    }

    /**
     * Moves this Player to a new Location - NOT CALLABLE BY STUDENTS
     * @param loc the new location
     */
    public final void moveTo(Location loc) {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.endsWith("Player"))
            super.moveTo(loc);
        else {
            CtfWorld.extra += " Cheat";
            System.out.println("This Player has attempted an unauthorized moveTo");
        }
    }

    /**
     * Player's toString (override this)
     * @return info about a Player
     */
    public String toString() {
        return "Player";
    }
}
