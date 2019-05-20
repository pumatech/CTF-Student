package ctf2019;

import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.util.Collections;

public class CtfWorld extends ActorWorld {
    public static final int MAX_GAME_LENGTH = 1000;
    public static String extra = "";
    private ArrayList<AbstractPlayer> players;
    private Team teamA, teamB;
    private int steps;

    public CtfWorld() {
        super();
        players = new ArrayList<AbstractPlayer>();
        this.setMessage("Click Run to begin");
    }

    public CtfWorld(Team a, Team b) {
        super();// img won't work
        players = new ArrayList<AbstractPlayer>();
        teamA = a;
        teamB = b;
        this.setMessage("Click Run to begin");
    }

    public void step() {
        if (players.size() == 0) {
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
            for (AbstractPlayer p : players) {
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

    public void setTeams(Team a, Team b) {
        players.clear();
        steps = 0;
        teamA = a;
        teamB = b;
    }

    public int getGameLength() {
        return MAX_GAME_LENGTH;
    }

    public int getSteps() {
        return steps;
    }

    public boolean locationClicked(Location loc) { return true; }

}