package ctf2025;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import javax.swing.*;

/**
 * Class to set up a single Match between two Teams
 */
public class Match {
    private static int numRocks;

    private Team teamA, teamB;
    private Team winner;
    private CtfWorld world;

    /**
     * Constructs a Match between two Teams with a default number of Rocks
     * @param a Team 1
     * @param b Team 2
     */
    public Match(Team a, Team b) {
        teamA = a;
        teamB = b;
        world = new CtfWorld(a, b);
        numRocks = 75;
    }

    /**
     * Constructs a Match between two Teams with a user-specified number of Rocks
     * @param a
     * @param b
     * @param rocks
     */
    public Match(Team a, Team b, int rocks) {
        teamA = a;
        teamB = b;
        world = new CtfWorld(a, b);
        System.gc();
        numRocks = rocks;
    }

    /**
     * Starts the Match:
     * Sets up the Teams on the Grid
     * Adds Rocks
     * Builds a CtfWorld and shows it so it can be played
     */
    public void start() {
        BoundedGrid<Actor> grid = new BoundedGrid<>(50, 100);

        double randomNumber = Math.random();
        teamA.addTeamToGrid(grid, randomNumber < .5 ? 0 : 1);
        teamB.addTeamToGrid(grid, randomNumber < .5 ? 1 : 0);
        teamA.setOpposingTeam(teamB);
        teamB.setOpposingTeam(teamA);

        for (int i = 0; i < numRocks; i++) {
            Location rockClumpLocation;
            do {
                rockClumpLocation = new Location((int) (Math.random() * (grid.getNumRows())), (int) (Math.random() * ((grid.getNumCols()) - 6)) + 3);
            } while (nearFlag(grid, rockClumpLocation) || grid.get(rockClumpLocation) != null);

            if (!nearPlayer(teamA, rockClumpLocation) && !nearPlayer(teamB, rockClumpLocation))
                new Rock().putSelfInGrid(grid, rockClumpLocation);
            for (int j = 0; j < 8; j++) {
                int randomDirection = (int) (Math.random() * 8) * Location.HALF_RIGHT;
                Location possibleRockLocation = rockClumpLocation.getAdjacentLocation(randomDirection);
                if (grid.isValid(possibleRockLocation) && !nearFlag(grid, possibleRockLocation) && grid.get(possibleRockLocation) == null)
                    if (!nearPlayer(teamA, possibleRockLocation) && !nearPlayer(teamB, possibleRockLocation))
                        new Rock().putSelfInGrid(grid, possibleRockLocation);
            }
        }

        world.setGrid(grid);
        String msg = "";
        if (teamA.getSide() == 0) {
            msg = teamA.getName() + " vs. " + teamB.getName();
        } else {
            msg = teamB.getName() + " vs. " + teamA.getName();
        }
        final JOptionPane pane = new JOptionPane(msg);
        final JDialog d = pane.createDialog((JFrame)null, "Next Match");
        System.out.println();
        d.setLocation((int)d.getLocation().getX(),Math.max(0, (int)d.getLocation().getY()-300));
        d.setVisible(true);
        System.out.println("Starting Match: " + teamA.getName() + " vs. " + teamB.getName());

        world.show();
        world.setMessage(msg);

        while (!teamA.hasWon() && !teamB.hasWon()) {
        }

        winner = teamA.hasWon() ? teamA : teamB;
        System.out.println(teamA.getName() + ": " + teamA.getScore() + " " + teamB.getName() + ": " + teamB.getScore() + " Winner: " + winner.getName() + "\n");
        JOptionPane.showMessageDialog(null, winner.getName() + " has won!");

    }

    /**
     * Getter to get the winner of this Match
     * @return the winning Team
     */
    public Team getWinner() {
        return winner;
    }

    /**
     * Helper method to determine if a Location is near a Flag
     * @param grid The Grid
     * @param loc The Location to test
     * @return Whether a Location is near a Flag
     */
    private static boolean nearFlag(Grid<Actor> grid, Location loc) {
        for (int i = loc.getCol() - 5; i <= loc.getCol() + 5; i++) {
            for (int j = loc.getRow() - 5; j <= loc.getRow() + 5; j++) {
                Location newloc = new Location(j, i);
                if (grid.isValid(newloc) && (grid.get(newloc) instanceof Flag)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Helper method to determine if a Location is near any member of a given Team
     * @param t The Grid
     * @param loc The Location to test
     * @return Whether a Location is near any member of a Team
     */
    private boolean nearPlayer(Team t, Location loc) {
        for (Player p : t.getPlayers()) {
            if (distance(p.getLocation(), loc) < 3)
                return true;
        }
        return false;
    }

    /**
     * Helper method to determine the Pythagorean distance between two Locations
     * @param loc1 Location 1
     * @param loc2 Location 2
     * @return the Pythagorean Distance between the two Locations
     */
    private static double distance(Location loc1, Location loc2) {
        return Math.sqrt(Math.pow(loc1.getRow() - loc2.getRow(), 2) + Math.pow(loc1.getCol() - loc2.getCol(), 2));
    }

    public final void close() {
        world.close();
    }
}
