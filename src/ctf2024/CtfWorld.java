package ctf2024;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is a modified version of ActorWorld for use with the class's Capture the Flag competition
 */
public class CtfWorld extends ActorWorld {
    public static final int MAX_GAME_LENGTH = 1000;
    public static String extra = "";
    private final ArrayList<Player> players;
    private Team teamA, teamB;
    private int steps;

    /**
     * Constructs a new CtfWorld (default)
     */
    public CtfWorld() {
        super();
        players = new ArrayList<>();
        this.setMessage("Click Run to begin");
    }

    /**
     * Constructs a new CtfWorld with the two specified Teams
     * @param a Team 1
     * @param b Team 2
     */
    public CtfWorld(Team a, Team b) {
        super();// img won't work
        players = new ArrayList<>();
        teamA = a;
        teamB = b;
        this.setMessage("Click Run to begin");
    }

    /**
     * Performs one "step" in the CtfWorld.
     * On the first step of a match, all Players from boith teams are added to the players instance variable
     * After the last step, a winner is determined based on scores
     * Intermediate steps collect all the Players and shuffle them together and have them all act in random order
     */
    public void step() {
        if (players.isEmpty()) {
            players.addAll(teamA.getPlayers());
            players.addAll(teamB.getPlayers());
        }
        steps++;
        if (steps == MAX_GAME_LENGTH) {
            if (teamA.getScore() > teamB.getScore()) {
                teamA.setHasWon();
            } else {
                teamB.setHasWon();
            }
            announceScores();
        } else if (steps < MAX_GAME_LENGTH){
            Collections.shuffle(players);
            for (Player p : players) {
                p.act();
                if (p.hasFlag()) {
                    if (p.getTeam().onSide(p.getLocation())) {
                        p.getTeam().setHasWon();
                        return;
                    }
                }
            }
            announceScores();
        }
    }

    /**
     * Updates the CtfWorld's display to announce the current scores
     */
    protected final void announceScores() {
        String scoreAnnouncement = "step: " + steps + "   \t";
        if (teamA.getSide() == 0) {
            scoreAnnouncement += teamA.getName() + ": " + teamA.getScore();
            scoreAnnouncement += "   \t" + teamB.getName() + ": " + teamB.getScore();
        }
        else {
            scoreAnnouncement += teamB.getName() + ": " + teamB.getScore();
            scoreAnnouncement += "   \t" + teamA.getName() + ": " + teamA.getScore();
        }
        scoreAnnouncement += extra;
        this.setMessage(scoreAnnouncement);
        extra = "";
    }

    /**
     * Setter to (re)set the two teams in this World
     * @param a Team 1
     * @param b Team 2
     */
    public void setTeams(Team a, Team b) {
        players.clear();
        steps = 0;
        teamA = a;
        teamB = b;
    }

    /**
     * Getter to get the current Game Length
     * @return the game length
     */
    public int getGameLength() {
        return MAX_GAME_LENGTH;
    }

    /**
     * Getter to determine how many steps have been completed
     * @return The number of steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Called automatically when a Location is clicked (disables any action)
     * @param loc the grid location that the user selected
     * @return true always
     */
    public boolean locationClicked(Location loc) { return true; }

}