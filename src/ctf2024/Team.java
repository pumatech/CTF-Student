package ctf2024;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

/**
 * The class defines a CTF Team, which consists of an ArrayList of 8 Players,
 * a Flag, a team color, a team name, which side of the field the Team is on,
 * and various instance variable to help determine the team's score throughout
 * a match.
 */
public abstract class Team {
    public static final int MAX_SIZE = 8;
    public static final Location DEFAULT_FLAG_LOCATION = new Location(24, 10);

    private final ArrayList<Player> players;
    private Grid<Actor> grid;
    private Flag flag;
    private Team opposingTeam;
    private final Color color;
    private final String name;
    private volatile boolean hasWon;
    private int score;
    private int pickUps;
    private int tags;
    private int offensiveMoves;

    private int side;

    /**
     * Constructs a new Team with a name and default color.  A Flag is automatically
     * added, but Players must be added individually.
     *
     * @param name  the team's name
     * @param color the team's default color
     */
    public Team(String name, Color color) {
        players = new ArrayList<>();
        this.name = name;
        this.color = color;
    }

    /**
     * Generates a Team by adding Players (done in an extending subclass)
     */
    private final void generateTeam() {
    }

    /**
     * Resets a team back to initial state for use in a new game
     */
    private final void resetTeam() {
        players.clear();
        generateTeam();
    }

    /**
     * Adds a Player to the Team.  When Players are constructed, they should have an
     * initial Location on the LEFT side of the field.  When the Team is added to the
     * Grid, Players will automatically be reLocated to the right side of the screen
     * if appropriate.
     *
     * @param player - The instance of Player to be added
     */
    public final void addPlayer(Player player) {
        if (players.size() < MAX_SIZE) {
            players.add(player);
            player.setTeam(this);
        } else {
            throw new RuntimeException("Team is full - MAX_SIZE = " + MAX_SIZE);
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
        for (Player player : players) {
            double dist = Math.sqrt(Math.pow(player.getStartLocation().getRow() - DEFAULT_FLAG_LOCATION.getRow(), 2)
                    + Math.pow(player.getStartLocation().getCol() - DEFAULT_FLAG_LOCATION.getCol(), 2));
            if (player.getStartLocation().getCol() >= grid.getNumCols() / 2 || player.getStartLocation().getCol() < 0 || dist < 10.0) {
                System.err.println("Someone has cheated and given their players an invalid start location");
                Location nextLoc;
                do {
                    nextLoc = new Location((int) (Math.random() * grid.getNumRows() - 1), 0);
                } while (grid.get(nextLoc) != null);
                player.setStartLocation(nextLoc);
            }
            player.putSelfInGridProtected(grid, adjustForSide(player.getStartLocation(), grid));
        }
    }

    /**
     * Scores a Flag pick-up. NOT CALLABLE BY STUDENTS
     */
    protected final void addPickUp() {
        pickUps++;
    }

    /**
     * Scores a tag. NOT CALLABLE BY STUDENTS
     */
    protected final void addTag() {
        tags++;
    }

    /**
     * Scores an offensive move. NOT CALLABLE BY STUDENTS
     */

    protected final void addOffensiveMove() {
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        System.out.println(callingClass);
        offensiveMoves++;
    }

    /**
     * Displays a Team's stats in the console - usefulful for debugging.  DO NOT USE IF FINAL CODE
     *
     * @param steps - how many steps
     */
    public void displayStats(int steps) {
        System.out.println(name + " got " + pickUps + " pick ups and " + tags + " tags. They made " + offensiveMoves
                + " steps on the offencive side, and were on the other side " + offensiveMoves / (steps + players.size()) + "% of the game");
    }

    /**
     * Adds points to the Team's score. NOT CALLABLE BY STUDENTS
     *
     * @param s The score to add
     */
    protected final void addScore(int s) {
        score += s;
    }

    /**
     * Adjusts a Location to the proper side
     *
     * @param loc The location to be adjusted
     * @param grid The grid in which the Location is valid
     * @return The adjusted Location
     */
    private final Location adjustForSide(Location loc, Grid<Actor> grid) {
        return new Location(loc.getRow(), (side == 0 ? loc.getCol() : grid.getNumCols() - 1 - loc.getCol()));
    }

    /**
     * Setter for Opposing Team
     *
     * @param opposingTeam The opposing Team
     */
    protected final void setOpposingTeam(Team opposingTeam) {
        this.opposingTeam = opposingTeam;
    }

    /**
     * Declares that this Team has won
     */
    protected final void setHasWon() {
        hasWon = true;
    }

    /**
     * Checks a Location to see if it is on this Team's side or not
     *
     * @param loc The Location to check
     * @return Whether it's on this Team's side
     */
    public final boolean onSide(Location loc) {
        return side == 0 && loc.getCol() < grid.getNumCols() / 2 || side == 1 && loc.getCol() >= grid.getNumCols() / 2;
    }

    /**
     * Checks a Location to see if it is near a Team's Flag or not
     */
    final static int RANGE = 4;

    public final boolean nearFlag(Location loc) {
        if (flag == null || flag.getLocation() == null) return false;
        Location fLoc = flag.getLocation();

        return Math.abs(loc.getRow() - fLoc.getRow()) <= RANGE && Math.abs(loc.getCol() - fLoc.getCol()) <= RANGE;
        //return Math.sqrt(Math.pow(loc.getRow() - flag.getLocation().getRow(), 2) + Math.pow(loc.getCol() - flag.getLocation().getCol(), 2)) <= 4;
    }

    /**
     * Getter for the ArrayList of a Team's Players
     *
     * @return This Team's Players
     */
    public final ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) players.clone();
    }

    /**
     * Getter for the Team's Flag
     *
     * @return This Team's Flag instance
     */
    public final Flag getFlag() {
        return flag;
    }

    /**
     * Getter for the instance of the opposing Team
     *
     * @return The opposing Team instance
     */
    public final Team getOpposingTeam() {
        return opposingTeam;
    }

    /**
     * Getter for a Team's Color
     *
     * @return The Team's Color
     */
    public final Color getColor() {
        return color;
    }

    /**
     * Getter for a Team's name
     *
     * @return The Team's name
     */
    public final String getName() {
        return name;
    }

    /**
     * Getter for a Team's score
     *
     * @return The Team's current score
     */
    public final int getScore() {
        return score;
    }

    /**
     * Getter for a Team's Side (0=Left, 1=Right)
     *
     * @return The Team's side
     */
    public final int getSide() {
        return side;
    }

    /**
     * Getter to determine if a Team has won
     *
     * @return Whether a Team has won
     */
    public final boolean hasWon() {
        return hasWon;
    }

    /**
     * Compares another t=Team to see if it is the same as this Team
     *
     * @param team The other Team
     * @return Whether this Team is the same as the other Team
     */
    public final boolean equals(Team team) {
        return team.getSide() == side && team.getColor().equals(color) && team.getClass() == getClass();
    }
}