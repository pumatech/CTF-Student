package ctf2019;

import ctf2019.teams.sample.SampleTeam;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class TournamentRunner {
    public static void main(String[] args) throws IOException {
        ArrayList<Team> teams = new ArrayList<Team>();

        teams.add(new SampleTeam("Team RED", Color.RED));
        teams.add(new SampleTeam("Team GREEN", Color.GREEN));
        teams.add(new SampleTeam("Team BLUE", Color.BLUE));
        teams.add(new SampleTeam("Team BLACK", Color.BLACK));
        teams.add(new SampleTeam("Team YELLOW", Color.YELLOW));
        teams.add(new SampleTeam("Team RED", Color.RED));
        teams.add(new SampleTeam("Team GREEN", Color.GREEN));
        teams.add(new SampleTeam("Team BLUE", Color.BLUE));
        teams.add(new SampleTeam("Team BLACK", Color.BLACK));
        teams.add(new SampleTeam("Team YELLOW", Color.YELLOW));
        teams.add(new SampleTeam("Team RED", Color.RED));
        teams.add(new SampleTeam("Team GREEN", Color.GREEN));
        teams.add(new SampleTeam("Team BLUE", Color.BLUE));
        teams.add(new SampleTeam("Team BLACK", Color.BLACK));
        teams.add(new SampleTeam("Team YELLOW", Color.YELLOW));

        CtfWorld world = new CtfWorld();

        Tournament tournament = new Tournament(teams, world);
        tournament.start();
        Team winner = tournament.getWinner();
        world.setMessage(winner.getName() + " won the tournament");
    }
}
