package ctf2025;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class organizes Players into a single team.  Each Team may have up to 8 Players and one Flag.
 * It can also be used to derive information about this Team and the opposing Team.
 */
public abstract class Team {
    public static final int MAX_PLAYERS = 8;
    public static final Location DEFAULT_FLAG_LOCATION = new Location(24, 10);

    private Color color;
    private String name;

    private ArrayList<Player> players;
    private Grid<Actor> grid;
    private Flag flag;

    private Team opposingTeam;
    private int side;

    private volatile boolean hasWon;
    private int score;
    private int pickUps;
    private int tags;
    private int offensiveMoves;
    private int defensiveMoves;
    private int carries;

    /**
     * Constructs a new Team with the specified name and color.
     *
     * @param color the Team's color
     */
    public Team(String name, Color color) {
        this.color = color;
        this.name = name;

        players = new ArrayList<>();
        this.flag = new Flag(this);

        this.hasWon = false;
        this.score = 0;
        this.pickUps = 0;
        this.tags = 0;
        this.offensiveMoves = 0;
        this.defensiveMoves = 0;
        this.carries = 0;

        // overriding class should to add players in its constructor
    }
    /**
     * Resets a team back to initial state for use in a new game
     */
    private final void resetTeam() {
        players.clear();
        this.generateTeam();
    }

    /**
     * Generates a Team by adding Players (done in an extending subclass)
     */
    public void generateTeam() {
    }

    /**
     * Adds a Player to this Team (if there is room)
     *
     * @param player the Player to be added
     */

    public final void addPlayer(Player player) {
        if (players.size() < MAX_PLAYERS) {
            players.add(player);
            player.setTeam(this);
        } else {
            System.err.println("Player not added since this Team is full - MAX_SIZE = " + MAX_PLAYERS);
        }
    }

    /**
     * sets the side (left or right) for this team
     *
     * @param side
     */
    public void setSide(int side) {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals(CtfWorld.thisYearsPackage+".CtfWorld")) {
            this.side = side;
        } else {
            CtfWorld.addExtraText("Cheat");
            System.err.println(callingClass + " has cheated and tried to change the side of a team");
        }
    }
    /**
     * Places all Players and Flag from a Team onto the correct side of the Grid.
     * Players can not be placed too close to their own Flag or they will be automatically
     * relocated.  NOT CALLABLE BY STUDENTS
     *
     * @param grid the Grid to add the Team to
     * @param side the side to add the Team to (if on the right, Players will be
     *             flipped around the centerline to appear on the right
     */
    protected final void addTeamToGrid(Grid<Actor> grid, int side) {
        this.grid = grid;
        this.side = side;
        score = 0;
        pickUps = 0;
        tags = 0;
        offensiveMoves = 0;
        hasWon = false;
        flag = new Flag(this);
        flag.putSelfInGrid(grid, adjustForSide(DEFAULT_FLAG_LOCATION, grid));
        resetTeam();
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player player = it.next();
            double dist = Math.sqrt(Math.pow(player.getStartLocation().getRow() - DEFAULT_FLAG_LOCATION.getRow(), 2)
                    + Math.pow(player.getStartLocation().getCol() - DEFAULT_FLAG_LOCATION.getCol(), 2));
            if (player.getStartLocation().getCol() >= grid.getNumCols() / 2 || player.getStartLocation().getCol() < 0 || dist < 10.0) {
                System.err.println("Someone has cheated and given their players an invalid start location");
                it.remove();
//                Location nextLoc;
//                do {
//                    nextLoc = new Location((int) (Math.random() * grid.getNumRows() - 1), 0);
//                } while (grid.get(nextLoc) != null);
//                player.setStartLocation(nextLoc);
            }
            else {
                player.putSelfInGridProtected(grid, adjustForSide(player.getStartLocation(), grid));
            }
        }
    }

    protected final void addPickUp() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals(CtfWorld.thisYearsPackage+".Player")) {
            pickUps++;
        } else {
            CtfWorld.addExtraText("Cheat");
            System.err.println(callingClass + " has cheated and tried to change the score");
        }
    }

    protected final void addTag() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals(CtfWorld.thisYearsPackage+".Player")) {
            tags++;
        } else {
            CtfWorld.addExtraText("Cheat");
            System.err.println(callingClass + " has cheated and tried to change the score");
        }
    }

    protected final void addCarry() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals(CtfWorld.thisYearsPackage+".Player")) {
            carries++;
        } else {
            CtfWorld.addExtraText("Cheat");
            System.err.println(callingClass + " has cheated and tried to change the score");
        }
    }

    protected final void addOffensiveMove() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals(CtfWorld.thisYearsPackage+".Player")) {
            offensiveMoves++;
        } else {
            CtfWorld.addExtraText("Cheat");
            System.err.println(callingClass + " has cheated and tried to change the score");
        }
    }

    protected final void addDefensiveMove() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals(CtfWorld.thisYearsPackage+".Player")) {
            defensiveMoves++;
        } else {
            CtfWorld.addExtraText("Cheat");
            System.err.println(callingClass + " has cheated and tried to change the score");
        }
    }

    /**
     * Gets current point stats for this Team
     */
    public String getStats() {
        return String.format("%s  had:\n%d defensive moves\n%d offensive moves\n%d carries\n%d tagouts\n%d Flag pickups\nfor a total score of: %d\n", name, defensiveMoves, offensiveMoves, carries, tags, pickUps, score);
    }

    protected final void addScore(int s) {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals(CtfWorld.thisYearsPackage+".Player")) {
            score += s;
        } else {
            CtfWorld.addExtraText("Cheat");
            System.err.println(callingClass + " has cheated and tried to change the score");
        }
    }

    /**
     * Mirrors a Location from the left side of the field to the right
     *
     * @param loc  the Location on the left side to be mirrored
     * @param grid the Grid in which this Location is valid
     * @return the new mirrored Location on the right hand side
     */
    public final Location adjustForSide(Location loc, Grid<Actor> grid) {
        return new Location(loc.getRow(), (side == 0 ? loc.getCol() : grid.getNumCols() - 1 - loc.getCol()));
    }

    protected final void setOpposingTeam(Team opposingTeam) {
        this.opposingTeam = opposingTeam;
    }

    protected final void setHasWon() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals(CtfWorld.thisYearsPackage+".CtfWorld")) {
            hasWon = true;
        } else {
            CtfWorld.addExtraText("Cheat");
            System.err.println(callingClass + " has cheated and tried change the winning team");
        }
    }

    /**
     * Determines whether or not a Location is on the same side (left or right) as this Player
     *
     * @param loc the Location to be tested
     * @return whether or not the Location is on the same side as this Player
     */
    public final boolean onSide(Location loc) {
        return side == 0 && loc.getCol() < 50 || side == 1 && loc.getCol() >= 50;
    }

    final static int RANGE = 4;

    /**
     * Determines whether or not a Location is within 4 spaces of this Player's Flag
     *
     * @param loc the Location to be tested
     * @return whether or not the Location is within 4 spaces of this Player's Flag
     */
    public final boolean nearFlag(Location loc) {
        if (flag == null || flag.getLocation() == null) return false;
        Location fLoc = flag.getLocation();

        return Math.abs(loc.getRow() - fLoc.getRow()) <= RANGE && Math.abs(loc.getCol() - fLoc.getCol()) <= RANGE;
        //return Math.sqrt(Math.pow(loc.getRow() - flag.getLocation().getRow(), 2) + Math.pow(loc.getCol() - flag.getLocation().getCol(), 2)) <= 4;
    }

    /**
     * Returns an ArrayList of this Team's Players
     *
     * @return this Team's Players
     */
    public final ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) players.clone();
    }

    /**
     * Returns this Team's Flag
     *
     * @return this Team's Flag
     */
    public final Flag getFlag() {
        return flag;
    }

    /**
     * Returns this Team's opposing Team
     *
     * @return this Team's opposing Team
     */
    public final Team getOpposingTeam() {
        return opposingTeam;
    }

    /**
     * Returns this Team's color
     *
     * @return this Team's color
     */
    public final Color getColor() {
        return color;
    }

    /**
     * Returns this Team's name
     *
     * @return this Team's name
     */
    public final String getName() {
        return name;
    }

    /**
     * Returns this Team's score
     *
     * @return this Team's score
     */
    public final int getScore() {
        return score;
    }

    /**
     * Returns this Team's side
     *
     * @return this Team's side
     */
    public final int getSide() {
        return side;
    }

    /**
     * Returns whether or not this Team has won the game
     *
     * @return whether or not this Team has won the game
     */
    public final boolean hasWon() {
        return hasWon;
    }

    public final boolean equals(Team team) {
        return team.getSide() == side && team.getClass() == getClass();
    }
}