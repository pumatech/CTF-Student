package ctf2019;

import ctf2019.teams.GoGo.GoGoTeam;
import ctf2019.teams.sample.SampleTeam;
import info.gridworld.actor.Rock;

import java.awt.*;

public class SimpleRunner {

    public static void main(String[] args) {

        Team a = new SampleTeam("Team 1", Color.RED);
        Team b = new GoGoTeam();
        CtfWorld world = new CtfWorld(a, b);

        a.setOpposingTeam(b);
        b.setOpposingTeam(a);

        Match match = new Match(a, b, world, 10);
        match.start();
    }
}
