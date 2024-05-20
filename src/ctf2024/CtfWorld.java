package ctf2024;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.event.WindowEvent;
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
    private static int steps;
    public static final String thisYearsPackage = "ctf2024";

    public static void addExtraText(String text) {
        CtfWorld.extra += " " + text;
    }

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
        teamA = a;
        teamB = b;
        players = new ArrayList<>();
        this.setMessage("Click Run to begin");
        steps = 0;
    }

    /**
     * returns the current step number
     * @return
     */
    public static int getStepNum() {
        return steps;
    }
    /**
     * Performs one "step" in the CtfWorld.
     * On the first step of a match, all Players from both teams are added to the players instance variable
     * After the last step, a winner is determined based on scores
     * Intermediate steps collect all the Players and shuffle them together and have them all act in random order
     */
    public void step() {
        // make sure this is only called from GUI
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        if (callingClass.equals("info.gridworld.gui.GUIController")) {
            // if first step add players
            if (players.isEmpty()) {
                players.addAll(teamA.getPlayers());
                players.addAll(teamB.getPlayers());
            }
            // game over due to number of steps
            if (this.steps >= MAX_GAME_LENGTH) {
                if (this.teamA.getScore() > this.teamB.getScore()) {
                    this.teamA.setHasWon();
                } else if (this.teamB.getScore() > this.teamA.getScore()) {
                    this.teamB.setHasWon();
                }
                else { // tie!
                    boolean coin = (Math.random() < 0.5);
                    if (coin) {
                        this.teamA.setHasWon();
                    }
                    else {
                        this.teamB.setHasWon();
                    }
                }
                return;
            }
            // game active - make all Players act
            this.steps++;
            Collections.shuffle(this.players);
            System.gc();
            for (Player p : this.players) {
                p.act();
                if (p.hasFlag()) {
                    // check for win (has flag on own side)
                    if (p.getTeam().onSide(p.getLocation())) {
                        p.getTeam().setHasWon();
                        return;
                    }
                }
            }
        }

        // update displayed scores
        this.displayScores();

    }

    private final void displayScores() {
        String scoreText = "step: " + steps + "   \t";
        if (this.teamA.getSide() == 0) {
            scoreText += this.teamA.getName() + ": " + this.teamA.getScore();
            scoreText += "   \t" + this.teamB.getName() + ": " + this.teamB.getScore();
        } else {
            scoreText += this.teamB.getName() + ": " + this.teamB.getScore();
            scoreText += "   \t" + this.teamA.getName() + ": " + this.teamA.getScore();
        }
        scoreText += CtfWorld.extra;
        this.setMessage(scoreText);
        CtfWorld.extra = "";
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

    /**
     * closes the GUI windows of a CTFWorld
     */
    public void close() {
        frame.stop();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        System.gc();
    }
}