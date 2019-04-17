package ctf2019;

import ctf2019.teams.sample.SampleTeam;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class TournamentRunner {
	public static void main(String[] args) throws IOException {
		ArrayList<Team> teams = new ArrayList<Team>();
		
		teams.add(new SampleTeam("Team RED", Color.RED));
		teams.add(new SampleTeam("Team GREEN", Color.GREEN));
		teams.add(new SampleTeam("Team BLUE", Color.BLUE));
		teams.add(new SampleTeam("Team BLACK", Color.BLACK));
		
		CtfWorld world = new CtfWorld(teams.get(0), teams.get(1));
		
		Tournament tournament = new Tournament(teams,world);
		tournament.start();
		Team winner = tournament.getWinner();
		System.out.println(winner.getName()+" won the tournament 2");
	}
}
