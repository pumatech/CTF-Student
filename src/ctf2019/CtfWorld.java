package ctf2019;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import info.gridworld.actor.ActorWorld;

public class CtfWorld extends ActorWorld {
	public static final int MAX_GAME_LENGTH = 1000;
	
	private ArrayList<AbstractPlayer> players;
	private Team teamA, teamB;
	private int steps;
	
	public CtfWorld() {
		super();
		players = new ArrayList<AbstractPlayer>();
	}
	
	public CtfWorld(Team a, Team b) throws IOException {
		super();// img won't work
		players = new ArrayList<AbstractPlayer>();
		teamA = a;
		teamB = b;
	}
	
	public void step() {
		if(players.size()==0) {
			players.addAll(teamA.getPlayers());
			players.addAll(teamB.getPlayers());
		}
		steps++;
		if (steps >= MAX_GAME_LENGTH) {
			if (teamA.getScore() > teamB.getScore()) {
				teamA.setHasWon();
			} else {
				teamB.setHasWon();
			}
		} else {
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
		String scoreAnnouncement = "step: " + steps + "   \t"+teamA.getName()+": ";
		scoreAnnouncement += teamA.getScore();
		scoreAnnouncement += "   \t"+teamB.getName()+": ";
		scoreAnnouncement += teamB.getScore();
		System.out.println(scoreAnnouncement);
	}
	
	public void setTeams(Team a, Team b) {
		players.clear();
		steps=0;
		teamA = a;
		teamB = b;
	}
	
	public int getGameLength() {
		return MAX_GAME_LENGTH;
	}
	public int getSteps() {
		return steps;
	}
}
